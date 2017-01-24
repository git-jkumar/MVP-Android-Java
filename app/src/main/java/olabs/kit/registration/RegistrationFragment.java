package olabs.kit.registration;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import olabs.kit.BaseFragment;
import olabs.kit.OnProgressListener;
import olabs.kit.R;
import olabs.kit.databinding.FragmentRegisterBinding;
import olabs.kit.registration.model.RegistrationRequest;


public class RegistrationFragment extends BaseFragment<RegisterViewModel, FragmentRegisterBinding> implements OnProgressListener {

    public static String mTitle = "Registration";

    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);
        mViewModel = new RegisterViewModel(mViewDataBinding.getRoot().getContext());
        mViewModel.setOnProgressListener(this);
        mViewDataBinding.setModel(new RegistrationRequest());
        mViewDataBinding.setHandler(mViewModel);
        return mViewDataBinding.getRoot();
    }


    @Override
    public void showProgress() {
        mViewDataBinding.progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mViewDataBinding.progress.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        showMessage(message);
    }

}
