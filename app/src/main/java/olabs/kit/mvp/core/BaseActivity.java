package olabs.kit.mvp.core;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import olabs.kit.mvp.R;
import olabs.kit.mvp.core.networx.RetroError;


public abstract class BaseActivity<T extends BasePresenter, S extends ViewDataBinding> extends Activity {

    @Nullable
    protected T mPresenter;

    @NonNull
    protected S mViewDataBinding;

    protected View mProgressBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewBinding();
        initPresenter();
        setupViews();
    }

    protected abstract void initPresenter();
    protected abstract void initViewBinding();
    protected abstract void setupViews();

    public void hideProgress() {
        if(mProgressBarView == null)
            return;
        mProgressBarView.setVisibility(View.GONE);
    }

    public void showProgress() {
        if(mProgressBarView == null)
            return;
        mProgressBarView.setVisibility(View.VISIBLE);
    }

    public void showMessage(int messageStringId) {
        Toast.makeText(this, getString(messageStringId), Toast.LENGTH_SHORT).show();
    }

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return true;
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
                return true;
            return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
        }
        return false;
    }

    protected void logout(){
    }

    public void onNetworkError(RetroError retroError){
        if (retroError.getHttpErrorCode() == 302 || retroError.getHttpErrorCode() == 404) {
            return;
        }
        if (retroError.getHttpErrorCode() == 401) {
             logout();
        }else if (retroError.getKind() == RetroError.Kind.HTTP) {
             showMessage(R.string.err_something_went_wrong);
        } else if (retroError.getKind() == RetroError.Kind.NETWORK) {
             showMessage(R.string.err_network_error);
        }else if (retroError.getKind() == RetroError.Kind.UNEXPECTED) {
             showMessage(R.string.err_unexpected_error);
        }
    }

}
