package olabs.kit.mvp.core.networx;

import java.io.InputStream;
import java.util.HashMap;

import retrofit2.Retrofit;


public class RemoteDataService {

    private static RemoteDataService sDataService;
    private Retrofit mRestClient;

    private RemoteDataService() {
    }

    private RemoteDataService(Retrofit restClient) {
        mRestClient = restClient;
    }

    public static <S> S getInstance(final String baseUrl, final Class<S> serviceClass,final HashMap requestHeaderMap) {
        if (sDataService == null) {
            sDataService = new RemoteDataService(NetworkClient.getRestAdapter(baseUrl, requestHeaderMap));
        }
        return sDataService.getClient(serviceClass);
    }

    public static <S> S getNewInstance(final String baseUrl, final Class<S> serviceClass,final HashMap requestHeaderMap) {
            sDataService = null;
            sDataService = new RemoteDataService(NetworkClient.getRestAdapter(baseUrl, requestHeaderMap));

        return sDataService.getClient(serviceClass);
    }
    public static <S> S getHttpsInstance(final InputStream certificateInputStream, final String baseUrl, final Class<S> serviceClass, final HashMap requestHeaderMap) {
        if (sDataService == null) {
            sDataService = new RemoteDataService(NetworkClient.getHttpsRestAdapter(certificateInputStream,baseUrl, requestHeaderMap));
        }
        return sDataService.getClient(serviceClass);
    }

    public static <S> S getNewHttpsInstance(final InputStream certificateInputStream, final String baseUrl, final Class<S> serviceClass, final HashMap requestHeaderMap) {
        sDataService = null;
        sDataService = new RemoteDataService(NetworkClient.getHttpsRestAdapter(certificateInputStream,baseUrl, requestHeaderMap));

        return sDataService.getClient(serviceClass);
    }

    private <S> S getClient(Class<S> serviceClass) {
        return mRestClient.create(serviceClass);
    }

}

