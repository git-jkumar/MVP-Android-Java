package olabs.kit.login.listener;


import olabs.kit.common.AppConstant;
import olabs.kit.login.LoginViewModel;
import olabs.kit.login.api.ILoginApi;
import olabs.kit.login.model.LoginResponse;
import olabs.kit.rxnetworx.RetroError;
import olabs.kit.rxnetworx.RxCallbackManager;
import rx.android.schedulers.AndroidSchedulers;


public class LoginApiListener extends RxCallbackManager {

    LoginViewModel loginViewModel;



    public LoginApiListener(LoginViewModel loginViewModel) {

        super(AndroidSchedulers.mainThread(),loginViewModel.getBaseUrl(), loginViewModel.getHeaderConfiguration());
        this.loginViewModel = loginViewModel;
    }

    public void doLogin(String userName,String password){
       ILoginApi mLoginAPI = (ILoginApi) getServiceClient(AppConstant.IS_HEADER_UPDATE, ILoginApi.class);
        initiateApiCall(LoginResponse.class,mLoginAPI.doLogin("password",userName,password));

    }

    @Override
    public void onError(RetroError retroError) {
        loginViewModel.onLoginError();

    }

    @Override
    public void onSuccess(Object o) {
        if(o instanceof LoginResponse)
            loginViewModel.onLoginSuccess();

    }
}
