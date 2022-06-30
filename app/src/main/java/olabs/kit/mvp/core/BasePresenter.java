package olabs.kit.mvp.core;


import olabs.kit.mvp.core.networx.RemoteDataServiceCallback;
import olabs.kit.mvp.core.networx.NetworkError;

/**
 * Created by Jitendra Kumar on 27/2/17.
 */
public class BasePresenter<A extends IBaseApi, V extends IView> extends RemoteDataServiceCallback {
    protected final V iView;
    protected A iAPI;

    public BasePresenter(Class api, IView iView) {
        this.iView = (V) iView;
        this.iAPI = (A) getServiceClient(api);
    }

    public BasePresenter(IView iView) {
        this.iView = (V) iView;
    }

    @Override
    protected void onSuccess(Object o) {
        iView.hideProgress();
    }

    @Override
    protected void onError(NetworkError networkError) {
        iView.hideProgress();
        iView.onNetworkError(networkError);
    }
}
