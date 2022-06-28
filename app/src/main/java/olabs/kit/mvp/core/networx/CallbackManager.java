package olabs.kit.mvp.core.networx;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import olabs.kit.mvp.BuildConfig;
import olabs.kit.mvp.core.BaseResponse;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Jitendra Kumar on 25/11/16.
 */

public abstract class CallbackManager<T extends BaseResponse> implements Callback<T> {

    private final HashMap<String, String> mRequestHeaderMap = new HashMap<>();

    public CallbackManager() {
        //initHeaders();
    }

    public <S> S getServiceClient(Class<S> serviceClass) {
        return NetworkServiceFactory.getInstance(BuildConfig.BASE_URL, serviceClass, mRequestHeaderMap);
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
                onError(new RetroError(RetroError.Kind.HTTP, response.errorBody().string(), response.code()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(retrofit2.Call call, Throwable throwable) {
        if (throwable instanceof IOException) {
            onError(new RetroError(RetroError.Kind.NETWORK, "Check your internet connection, appears to be offline.", -999));
        } else if (throwable instanceof UnknownHostException || throwable instanceof IOException) {
            onError(new RetroError(RetroError.Kind.NETWORK, "Unable to connect to server.", -999));
        } else {
            onError(new RetroError(RetroError.Kind.UNEXPECTED, "Unexpected error...try after sometime.", -999));
        }
    }


    protected abstract void onSuccess(Object o);

    protected abstract void onError(RetroError retroError);


}
