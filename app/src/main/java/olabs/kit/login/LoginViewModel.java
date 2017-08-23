package olabs.kit.login;


import android.databinding.ObservableField;

import olabs.kit.base.BaseViewModel;
import olabs.kit.login.listener.LoginApiListener;


public class LoginViewModel extends BaseViewModel {
    ILoginView mILoginView;
    public ObservableField<String> userId;
    public ObservableField<String> password;
    LoginApiListener mLoginApiListener;

    public LoginViewModel(ILoginView mILoginView) {
        super(mILoginView);
        this.mILoginView = mILoginView;
        userId = new ObservableField<>();
        password = new ObservableField<>();
        mLoginApiListener = new LoginApiListener(this);
    }

    public void onLoginClick() {
        mLoginApiListener.doLogin(userId.get(), password.get());
    }

    public void onLoginSuccess() {
        userId.set("Login successful");
    }

    public void onLoginError() {
        userId.set("Login failed");
    }


}
