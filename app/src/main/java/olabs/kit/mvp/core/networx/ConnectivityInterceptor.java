package olabs.kit.mvp.core.networx;

import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        if (!WifiNetworkService.getInstance().isOnline()) {
            throw new IOException("No internet connection");
        } else {
            return chain.proceed(chain.request());
        }
    }
}
