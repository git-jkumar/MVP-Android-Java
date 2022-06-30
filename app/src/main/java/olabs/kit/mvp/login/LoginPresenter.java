package olabs.kit.mvp.login;


import olabs.kit.mvp.core.base.BasePresenter;
import olabs.kit.mvp.login.API.ILoginAPI;
import olabs.kit.mvp.login.model.LoginRequest;
import olabs.kit.mvp.login.model.LoginResponse;

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

    public void doRegister(){
//        iView.showMessage("To be developed by the user");
    }

    @Override
    protected void onSuccess(Object o) {
        super.onSuccess(o);
        if(o instanceof LoginResponse){
            iView.onLoginSuccess((LoginResponse) o);
        }
    }
}
