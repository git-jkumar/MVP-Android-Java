package onsterlabs.android.kit.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import onsterlabs.android.kit.BaseActivity;
import onsterlabs.android.kit.BaseApplication;
import onsterlabs.android.kit.OnProgressListener;
import onsterlabs.android.kit.R;
import onsterlabs.android.kit.SessionTimeoutListener;
import onsterlabs.android.kit.databinding.ActivityLoginBinding;
import onsterlabs.android.kit.login.model.LoginRequest;


public class LoginActivity extends BaseActivity implements OnProgressListener, SessionTimeoutListener {

    ActivityLoginBinding loginActivityBinding;
    LoginViewModel loginViewModel;
    LoginRequest loginRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
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
