package olabs.kit.mvp.core.net;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import olabs.kit.mvp.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Response originalResponse = chain.proceed(chain.request());
        int maxStale = 2419200;
        return originalResponse.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build();
    };
    private static SSLContext mSSLContext;
    private static X509TrustManager mTrustManager;

    private NetworkClient() {
    }

    public static Retrofit getRestAdapter(final String baseUrl, final HashMap<String, String> requestHeaderMap) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(Level.HEADERS);
            interceptor.setLevel(Level.BODY);
        }
        Gson gson = (new GsonBuilder()).addSerializationExclusionStrategy(new ExclusionStrategy() {
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).addDeserializationExclusionStrategy(new ExclusionStrategy() {
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.deserialize();
            }

            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).setLenient().create();
        OkHttpClient client = (new Builder())
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .addInterceptor(interceptor)
                .addInterceptor(new ConnectivityInterceptor())
                .addNetworkInterceptor(chain -> {
                    Request.Builder builder = chain.request().newBuilder();
                    Set entrySet = requestHeaderMap.entrySet();
                    Iterator request = entrySet.iterator();

                    while (request.hasNext()) {
                        Entry entry = (Entry) request.next();
                        if (entry.getValue() != null) {
                            if (((String) entry.getValue()).isEmpty()) {
                                builder.removeHeader((String) entry.getKey());
                            } else {
                                builder.addHeader((String) entry.getKey(), (String) entry.getValue());
                            }
                        }
                    }

                    Request request1 = builder.build();
                    return chain.proceed(request1);
                })
                .addNetworkInterceptor(interceptor).writeTimeout(5, TimeUnit.MINUTES).readTimeout(5, TimeUnit.MINUTES).build();
        return (new Retrofit.Builder()).baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build();
    }

    private static void createKeyStore(InputStream certificateInputStream) {

        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate ca;
            try {
                ca = cf.generateCertificate(certificateInputStream);
                System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                certificateInputStream.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            mTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];

            // Create an SSLContext that uses our TrustManager
            mSSLContext = SSLContext.getInstance("TLS");
            mSSLContext.init(null, tmf.getTrustManagers(), null);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("SOP from no IS method " + ex.getLocalizedMessage());
        }
    }

    public static Retrofit getHttpsRestAdapter(final InputStream inputStream, final String baseUrl, final HashMap<String, String> requestHeaderMap) {

        if (mTrustManager == null || mSSLContext == null) {
            createKeyStore(inputStream);
        }
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(Level.HEADERS);
            httpLoggingInterceptor.setLevel(Level.BODY);
        }

        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.serialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).addDeserializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                final Expose expose = fieldAttributes.getAnnotation(Expose.class);
                return expose != null && !expose.deserialize();
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create();

        OkHttpClient client = new Builder().
                sslSocketFactory(mSSLContext.getSocketFactory(), mTrustManager)
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request().newBuilder();
                    Set<Entry<String, String>> entrySet = requestHeaderMap.entrySet();
                    for (Entry<String, String> entry : entrySet) {
                        if (entry.getValue() != null) {
                            if (entry.getValue().isEmpty())
                                builder.removeHeader(entry.getKey());
                            else
                                builder.addHeader(entry.getKey(), entry.getValue());
                        }
                    }

                    Request request = builder.build();
                    return chain.proceed(request);
                })
                .addInterceptor(httpLoggingInterceptor).writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                .build();


        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


}

