package onsterlabs.android.kit;

import android.content.Context;

import onsterlabs.network.rxnetwork.RetroError;


public abstract class BaseViewModel {

    protected Context context;
    protected BaseApplication application;
    protected OnProgressListener mOnProgressListener;

    public BaseViewModel(Context context) {
        this.context = context;
        application = BaseApplication.get(context);
    }

    public abstract void onDestroy();

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        mOnProgressListener = onProgressListener;
    }

    //@Override
    public void onError(RetroError errorMessage) {
        mOnProgressListener.hideProgress();
        mOnProgressListener.showMessage(errorMessage.getErrorMessage());
    }

    public abstract void onResume();

    public abstract void onPause();




}
