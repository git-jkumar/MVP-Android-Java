package olabs.kit.mvp.core.net;

import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;
import olabs.kit.mvp.core.NetValidator;

public class ConnectivityInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!NetValidator.getInstance().isOnline()) {
            throw new IOException("No internet connection");
        } else {
            return chain.proceed(chain.request());
        }
    }
}
