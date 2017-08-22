package olabs.kit.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import olabs.kit.R;


public abstract class BaseActivity<T extends BaseViewModel, S extends ViewDataBinding> extends AppCompatActivity {

    @Nullable
    protected T mViewModel;

    @NonNull
    protected S mViewDataBinding;
    private Toolbar mToolbar;
    private TextView textViewTitle;
    private ImageView imageViewLogo;
    private ImageView imageViewHdfcLogo;
    private boolean homeIcon;
    private boolean toolbarIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            if (fragmentManager.getBackStackEntryCount() == 1) {
                finish();
            } else {
                fragmentManager.popBackStack();
            }
        } else {
            super.onBackPressed();
        }
    }


    protected void setViewModel(@NonNull S mViewDataBinding, @Nullable T mViewModel) {
        setViewDataBinding(mViewDataBinding);
        setViewModel(mViewModel);
    }

    @Nullable
    protected T getViewModel() {
        return mViewModel;
    }


    @NonNull
    protected S getViewDataBinding() {
        return mViewDataBinding;
    }

    protected void setViewDataBinding(@NonNull S mViewDataBinding) {
        this.mViewDataBinding = mViewDataBinding;
    }


    public boolean getToolbarIcon() {
        return toolbarIcon;
    }

    public void setToolbarIcon(boolean toolbarIcon) {
        this.toolbarIcon = toolbarIcon;
    }


//    public void showLongSnackBar(String message) {
//        View rootLayout;
//        rootLayout = findViewById(android.R.id.content);
//        Snackbar snackbar = Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG);
//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundResource(R.color.colorAccent);
//        snackbar.show();
//    }
//
//    public void showShortSnackBar(String message) {
//        View rootLayout;
//        rootLayout = findViewById(android.R.id.content);
//        Snackbar snackbar = Snackbar.make(rootLayout, message, Snackbar.LENGTH_LONG);
//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundResource(R.color.colorAccent);
//        snackbar.show();
//    }


    public void loadFragment(Fragment baseFragment, boolean isAdd) {
        loadFragment(baseFragment, isAdd, null);
    }

    public void loadFragment(Fragment baseFragment, boolean isAdd, View... view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String backStateName = baseFragment.getClass().getSimpleName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {
            for (View sharedElement : view) {
                ft.addSharedElement(sharedElement, ViewCompat.getTransitionName(sharedElement));
            }
        }
        if (isAdd)
            ft.add(R.id.container, baseFragment, backStateName).addToBackStack(backStateName).commit();
        else {
            popBackStack();
            ft.replace(R.id.container, baseFragment, backStateName).addToBackStack(backStateName).commit();
        }
    }


    public void popAllBackStack(String name, boolean isInclusive) {
        if (isInclusive)
            getFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        else
            getFragmentManager().popBackStack(name, 0);
    }

    public void popAllBackStack() {
        FragmentManager fm = getFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    public void popBackStack() {
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
    }


    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setViewModel(T viewModel) {
        this.mViewModel = viewModel;

    }
}
