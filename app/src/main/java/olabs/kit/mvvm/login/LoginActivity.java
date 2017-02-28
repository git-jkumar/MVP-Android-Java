package olabs.kit.mvvm.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import olabs.kit.R;
import olabs.kit.databinding.MvvmActivityLoginBinding;
import olabs.kit.mvvm.base.BaseActivity;


public class LoginActivity extends BaseActivity<LoginViewModel,MvvmActivityLoginBinding> implements ILoginView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.mvvm_activity_login);
        mViewModel = new LoginViewModel(this);
        mViewDataBinding.setViewmodel(mViewModel);
    }


    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
