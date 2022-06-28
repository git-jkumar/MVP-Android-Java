package olabs.kit.mvp.core;


import olabs.kit.mvp.core.networx.CallbackManager;
import olabs.kit.mvp.core.networx.RetroError;

/**
 * Created by Jitendra Kumar on 27/2/17.
 */
public class BasePresenter<A extends IBaseApi, V extends IView> extends CallbackManager {
    protected V iView;
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
    protected void onError(RetroError retroError) {
        iView.hideProgress();
        iView.onNetworkError(retroError);
    }
}
