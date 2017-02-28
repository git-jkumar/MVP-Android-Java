package olabs.kit.rxmvvm.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import olabs.kit.BaseApplication;
import olabs.kit.OnProgressListener;
import olabs.kit.R;
import olabs.kit.SessionTimeoutListener;
import olabs.kit.databinding.RxActivityLoginBinding;
import olabs.kit.model.LoginRequest;
import olabs.kit.mvvm.base.BaseActivity;


public class LoginActivity extends BaseActivity implements OnProgressListener, SessionTimeoutListener {

    RxActivityLoginBinding loginActivityBinding;
    LoginViewModel loginViewModel;
    LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivityBinding = DataBindingUtil.setContentView(this, R.layout.rx_activity_login);
        loginRequest = new LoginRequest();
        loginViewModel = new LoginViewModel(this);
        loginViewModel.setOnProgressListener(this);
        loginActivityBinding.setHandler(loginViewModel);
        loginActivityBinding.setModel(loginRequest);

        BaseApplication baseApplication = ((BaseApplication) getApplication());
        baseApplication.setSessionTimeoutListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginViewModel.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loginViewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loginViewModel.onPause();
    }

    @Override
    public void showProgress() {
        loginActivityBinding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        loginActivityBinding.progress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSessionTimeout(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
