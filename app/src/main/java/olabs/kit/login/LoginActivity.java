package olabs.kit.login;


import android.databinding.DataBindingUtil;
import android.os.Bundle;

import olabs.kit.R;
import olabs.kit.base.BaseActivity;
import olabs.kit.databinding.ActivityLoginBinding;


public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> implements ILoginView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel((ActivityLoginBinding) DataBindingUtil.setContentView(this, R.layout.activity_login), new LoginViewModel(this));
        getViewDataBinding().setViewModel(getViewModel());
    }

    @Override
    public void showMessage(String message) {

    }

}
