package olabs.kit.mvp.features.login;


import olabs.kit.mvp.core.base.BasePresenter;
import olabs.kit.mvp.features.login.API.ILoginAPI;
import olabs.kit.mvp.features.login.model.LoginRequest;
import olabs.kit.mvp.features.login.model.LoginResponse;

/**
 * Created by Jitendra on 27/2/17.
 */
public class LoginPresenter extends BasePresenter<ILoginAPI,ILoginView> {

    public LoginPresenter(ILoginView loginView){
        super(ILoginAPI.class,loginView);
    }

    public void onLogin(String username, String password){
        LoginRequest loginRequest = new LoginRequest(username,password);
        if(loginRequest.isValidLogin(username,password) != 1) {
            iView.showMessage(loginRequest.isValidLogin(username, password));
            return;
        }
        iView.showProgress();
        iAPI.doLogin(username,password).enqueue(this);
    }

    @Override
    protected void onSuccess(Object o) {
        super.onSuccess(o);
        if(o instanceof LoginResponse){
            iView.onLoginSuccess((LoginResponse) o);
        }
    }
}
