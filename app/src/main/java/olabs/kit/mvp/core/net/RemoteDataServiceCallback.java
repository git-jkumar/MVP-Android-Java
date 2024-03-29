package olabs.kit.mvp.core.net;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import olabs.kit.mvp.BuildConfig;
import olabs.kit.mvp.core.base.BaseResponse;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Jitendra Kumar on 25/11/16.
 */

public abstract class RemoteDataServiceCallback<T extends BaseResponse> implements Callback<T> {

    private final HashMap<String, String> mRequestHeaderMap = new HashMap<>();

    public RemoteDataServiceCallback() {
        //initHeaders();
    }

    public <S> S getServiceClient(Class<S> serviceClass) {
        return RemoteDataService.getInstance(BuildConfig.BASE_URL, serviceClass, mRequestHeaderMap);
    }

    private void initHeaders() {
        mRequestHeaderMap.put("Content-Type", "application/json");
        mRequestHeaderMap.put("Authorization", BuildConfig.BASIC_AUTHORIZATION);
    }

    private void updateHeaders() {
        mRequestHeaderMap.put("Content-Type", "application/json");
        mRequestHeaderMap.put("Authorization", BuildConfig.APP_AUTHORIZATION);
    }

    @Override
    public void onResponse(retrofit2.Call call, Response response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            try {
                onError(new NetworkError(NetworkError.Kind.HTTP, response.errorBody().string(), response.code()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(retrofit2.Call call, Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            onError(new NetworkError(NetworkError.Kind.NETWORK, "Unable to connect to server.", -999));
        }else if (throwable instanceof IOException) {
            onError(new NetworkError(NetworkError.Kind.NETWORK, "Check your internet connection, appears to be offline.", -999));
        } else {
            onError(new NetworkError(NetworkError.Kind.UNEXPECTED, "Unexpected error...try after sometime.", -999));
        }
    }


    protected abstract void onSuccess(Object o);

    protected abstract void onError(NetworkError networkError);


}
