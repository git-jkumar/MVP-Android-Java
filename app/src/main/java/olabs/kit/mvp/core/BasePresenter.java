package olabs.kit.mvp.core;


import olabs.kit.mvp.R;
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

    }

    @Override
    protected void onError(RetroError retroError) {
        iView.hideProgress();
        if (retroError.getHttpErrorCode() == 401 || retroError.getHttpErrorCode() == 302 || retroError.getHttpErrorCode() == 404) {
            return;
        }
        if (retroError.getKind() == RetroError.Kind.HTTP) {
            this.iView.showMessage(R.string.err_something_went_wrong);
        } else if (retroError.getKind() == RetroError.Kind.NETWORK) {
            this.iView.showMessage(R.string.err_network_error);
        }
        if (retroError.getKind() == RetroError.Kind.UNEXPECTED) {
            this.iView.showMessage(R.string.err_unexpected_error);
        }
    }
}
