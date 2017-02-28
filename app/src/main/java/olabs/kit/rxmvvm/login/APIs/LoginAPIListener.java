package olabs.kit.rxmvvm.login.APIs;


import olabs.kit.network.rx.APIManager;
import olabs.kit.rxmvvm.login.LoginViewModel;
import olabs.kit.model.LoginResponse;
import olabs.network.rxnetwork.RXEventBus;
import olabs.network.rxnetwork.RetroError;
import olabs.network.rxnetwork.APISubscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by ttnd on 25/11/16.
 */

public class LoginAPIListener extends APIManager {

    protected LoginViewModel mLoginViewModel;
    protected ILoginAPI mAlbumAPI;

    public LoginAPIListener(LoginViewModel loginViewModel) {
        super();
        mAlbumAPI = (ILoginAPI) getServiceClient(ILoginAPI.class);
        this.mLoginViewModel = loginViewModel;

    }

    public void doLogin(String arn,String grantType,String password) {
        mAlbumAPI.doLogin(arn, grantType, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(defaultSubscribeScheduler())
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
        RXEventBus.getInstance().register(RetroError.class, this);
        RXEventBus.getInstance().register(LoginResponse.class, this);
    }

}
