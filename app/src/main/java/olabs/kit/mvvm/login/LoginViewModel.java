package olabs.kit.mvvm.login;

import olabs.kit.model.LoginResponse;
import olabs.kit.mvvm.base.BaseViewModel;
import olabs.kit.mvvm.listener.LoginAPIListener;

/**
 * Created by ttnd on 28/2/17.
 */

public class LoginViewModel extends BaseViewModel {

    private ILoginView mLoginView;
    private LoginAPIListener mLoginAPIListener;

    public LoginViewModel(ILoginView loginView){
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
