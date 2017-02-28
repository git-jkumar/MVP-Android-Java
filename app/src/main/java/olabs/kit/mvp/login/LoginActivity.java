package olabs.kit.mvp.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import olabs.kit.R;
import olabs.kit.databinding.MvpActivityLoginBinding;
import olabs.kit.mvp.base.BaseActivity;


public class LoginActivity extends BaseActivity<LoginPresenter,MvpActivityLoginBinding > implements ILoginView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.mvp_activity_login);
        mPresenter = new LoginPresenter(this);
        mViewDataBinding.setPresenter(mPresenter);
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
