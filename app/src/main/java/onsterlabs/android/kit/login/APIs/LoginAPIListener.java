package onsterlabs.android.kit.login.APIs;


import onsterlabs.android.kit.APIManager;
import onsterlabs.android.kit.BaseApplication;
import onsterlabs.android.kit.login.LoginViewModel;
import onsterlabs.android.kit.login.model.LoginResponse;
import onsterlabs.network.rxnetwork.APISubscriber;
import onsterlabs.network.rxnetwork.RXEventBus;
import onsterlabs.network.rxnetwork.RetroError;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ttnd on 25/11/16.
 */

public class LoginAPIListener extends APIManager {

    protected LoginViewModel mLoginViewModel;
    protected ILoginAPI mAlbumAPI;

    public LoginAPIListener(BaseApplication application, LoginViewModel loginViewModel) {
        super(application);
        mAlbumAPI = application.getServiceClient(ILoginAPI.class);
        this.mLoginViewModel = loginViewModel;

    }

    public void doLogin(String arn,String grantType,String password) {
        mAlbumAPI.doLogin(arn, grantType, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(application.defaultSubscribeScheduler())
                .subscribe(new APISubscriber<LoginResponse>());

    }

    @Override
    public void onError(RetroError retroError) {
        mLoginViewModel.onError(retroError);
    }

    @Override
    public void onSuccess(Object o) {
        if (o instanceof LoginResponse) {
            mLoginViewModel.onLoginSuccess((LoginResponse) o);
        }
    }

    @Override
    public void subscribe() {
        subscriptions.add(RXEventBus.getInstance().register(RetroError.class, this));
        subscriptions.add(RXEventBus.getInstance().register(LoginResponse.class, this));
    }

}
