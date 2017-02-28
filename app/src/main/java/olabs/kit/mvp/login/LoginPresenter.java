package olabs.kit.mvp.login;

import olabs.kit.model.LoginResponse;
import olabs.kit.mvp.base.BasePresenter;
import olabs.kit.mvp.listener.LoginAPIListener;


/**
 * Created by ttnd on 27/2/17.
 */
public class LoginPresenter extends BasePresenter {
    private ILoginView mLoginView;
    private LoginAPIListener mLoginAPIListener;

    public LoginPresenter(ILoginView loginView){
        mLoginView = loginView;
        mLoginAPIListener = new LoginAPIListener(this);
    }

    public void onLogin(String arn,String password){
        String grant_type = "password";
        mLoginAPIListener.doLogin(arn,grant_type,password);

    }

    public void onRegister(){

    }

    public void onLoginSuccess(LoginResponse loginResponse) {
        mLoginView.showMessage(loginResponse.getMessage());
    }

    public void onError(String errorMessage) {
        mLoginView.showMessage(errorMessage);
    }
}
