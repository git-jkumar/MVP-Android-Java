package olabs.kit.mvp.features.login;


import olabs.kit.mvp.core.base.IView;
import olabs.kit.mvp.features.login.model.LoginResponse;

/**
 * Created by Jitendra Kumar on 27/2/17.
 */

public interface ILoginView extends IView {
    void onLoginSuccess(LoginResponse loginResponse);

}
