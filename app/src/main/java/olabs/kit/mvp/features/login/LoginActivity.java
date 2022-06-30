package olabs.kit.mvp.features.login;


import androidx.databinding.DataBindingUtil;
import olabs.kit.mvp.R;
import olabs.kit.mvp.core.base.BaseActivity;
import olabs.kit.mvp.databinding.ActivityLoginBinding;
import olabs.kit.mvp.features.login.model.LoginResponse;


public class LoginActivity extends BaseActivity<LoginPresenter,ActivityLoginBinding> implements ILoginView {

    @Override
    protected void initPresenter() {
        mPresenter   = new LoginPresenter(this);
        mViewDataBinding.setPresenter(mPresenter);
    }

    @Override
    protected void initViewBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mProgressBarView = mViewDataBinding.included.progressBar;
    }

    @Override
    protected void setupViews() {

    }

    @Override
    public void onLoginSuccess(LoginResponse loginResponse) {
    }
}
