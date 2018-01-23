package olabs.kit.base;


import android.app.Fragment;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

public class BaseFragment<T extends BaseViewModel, S extends ViewDataBinding> extends Fragment implements IView {

    @Nullable
    protected T mPresenter;

    @Nullable
    protected S mViewDataBinding;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    protected void setViewPresenter(@NonNull S mViewDataBinding, @Nullable T mPresenter) {
        setViewDataBinding(mViewDataBinding);
        setPresenter(mPresenter);
    }

    @Nullable
    protected T getPresenter() {
        return mPresenter;
    }

    protected void setPresenter(@Nullable T mPresenter) {
        this.mPresenter = mPresenter;
    }

    @NonNull
    protected S getViewDataBinding() {
        return mViewDataBinding;
    }

    protected void setViewDataBinding(@NonNull S mViewDataBinding) {
        this.mViewDataBinding = mViewDataBinding;
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }


    public void onDetach() {
        super.onDetach();
    }


    protected boolean isOnline() {
        if (getActivity() != null) {
            ConnectivityManager cm =
                    (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        } else {
            return false;
        }
    }


    public void popAllBackStack(String name, boolean isInclusive) {
        if (getActivity() != null && !getActivity().isFinishing())
            ((BaseActivity) getActivity()).popAllBackStack(name, isInclusive);
    }

    public void popAllBackStack() {
        if (getActivity() != null && !getActivity().isFinishing())
            ((BaseActivity) getActivity()).popAllBackStack();
    }

    public void popBackStack() {
        if (getActivity() != null && !getActivity().isFinishing())
            ((BaseActivity) getActivity()).popBackStack();
    }

//    public void loadFragment(Fragment baseFragment, boolean isAdd, View... view) {
//        if (getActivity() != null && !getActivity().isFinishing())
//            ((BaseActivity) getActivity()).loadFragment(baseFragment, isAdd, view);
//    }
//
//
//    public void loadFragment(Fragment baseFragment, boolean isAdd) {
//        if (getActivity() != null && !getActivity().isFinishing())
//            ((BaseActivity) getActivity()).loadFragment(baseFragment, isAdd);
//    }


    public void hideKeyboard(View view) {
        if (view != null) {
            if (getActivity() != null && !getActivity().isFinishing())
                ((BaseActivity) getActivity()).hideKeyboard(view);
            view.clearFocus();
        }
    }

    @Override
    public void showMessage(String msg) {

    }
}