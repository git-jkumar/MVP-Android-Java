package onsterlabs.android.kit;

import android.content.Context;

import onsterlabs.network.RetroError;


/**
 * Interface that every BaseViewModel must implement
 */
public abstract class BaseViewModel {

    protected Context context;
    protected BaseApplication application;
    protected OnProgressListener mOnProgressListener;

    private BaseViewModel() {
    }

    public BaseViewModel(Context context) {
        this.context = context;
        application = BaseApplication.get(context);
    }

    public abstract void onDestroy();

    public void setOnProgressListener(OnProgressListener onProgressListener) {
        mOnProgressListener = onProgressListener;
    }

    public void onError(RetroError retroError) {
        mOnProgressListener.hideProgress();
        if(retroError.getKind() == RetroError.Kind.HTTP){
            switch (retroError.getHttpErrorCode()){
                case 401:
                    application.onSessionTimeout("Your session has expired...please login again");
                    break;
                case 500:
                    mOnProgressListener.showMessage("Something went wrong...try after sometime.");
                    break;
                default:
                    mOnProgressListener.showMessage("Something went wrong...try after sometime.");
                    break;
            }
        }
        else if(retroError.getKind() == RetroError.Kind.NETWORK){
            mOnProgressListener.showMessage("Unable to connect to server.");
        }if(retroError.getKind() == RetroError.Kind.UNEXPECTED){
            mOnProgressListener.showMessage("Unexpected error...try after sometime.");
        }
    }

    public abstract void onResume();

    public abstract void onPause();

}
