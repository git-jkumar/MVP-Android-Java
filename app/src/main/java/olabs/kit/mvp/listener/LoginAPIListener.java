package olabs.kit.mvp.listener;

import olabs.kit.API.ILoginAPI;
import olabs.kit.model.LoginResponse;
import olabs.kit.mvp.login.LoginPresenter;
import olabs.kit.network.CallbackManager;
import olabs.network.rxnetwork.RetroError;
import retrofit2.Call;

/**
 * Created by ttnd on 27/2/17.
 */

public class LoginAPIListener extends CallbackManager {
    LoginPresenter mLoginPresenter;
    protected ILoginAPI mLoginAPI;
    public LoginAPIListener(LoginPresenter loginPresenter) {
        mLoginAPI = (ILoginAPI) getServiceClient(ILoginAPI.class);
        this.mLoginPresenter = loginPresenter;

    }

    public void doLogin(String arn,String grantType,String password) {
        Call<LoginResponse> call = mLoginAPI.doLogin(arn, grantType, password);
        call.enqueue(this);
    }

    @Override
    protected void onSuccess(Object o) {
        if(o instanceof LoginResponse){
            mLoginPresenter.onLoginSuccess((LoginResponse) o);
        }

    }

    @Override
    protected void onError(RetroError retroError) {
        mLoginPresenter.onError(retroError.getErrorMessage());
    }
}
