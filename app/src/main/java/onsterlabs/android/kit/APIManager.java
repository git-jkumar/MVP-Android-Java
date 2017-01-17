package onsterlabs.android.kit;

import java.util.ArrayList;
import java.util.HashMap;

import onsterlabs.network.rxnetwork.RetroError;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ttnd on 25/11/16.
 */

public abstract class APIManager<T> implements Action1<T> {
    protected ArrayList<Subscription> subscriptions = new ArrayList<>();
    public BaseApplication application;
    HashMap<String, String> mRequestHeaderMap = new HashMap<>();
    private boolean isHeaderUpdate = false;

    public APIManager(BaseApplication application) {
        subscriptions.clear();
        this.application = application;
    }

    @Override
    public void call(T t) {
        if (t instanceof RetroError) {
            RetroError retroError = (RetroError) t;
            if(retroError.getKind() == RetroError.Kind.HTTP){
                switch (retroError.getHttpErrorCode()){
                    case 401:
                        unsubscribe();
                        application.onSessionTimeout("Your session has expired...please login again");
                        break;
                    case 500:
                        retroError.setErrorMessage("Something went wrong...try after sometime.");
                        break;
                    default:
                        retroError.setErrorMessage("Something went wrong...try after sometime.");
                        break;
                }
            }
            else if(retroError.getKind() == RetroError.Kind.NETWORK){
                retroError.setErrorMessage("No internet connection.");
            }if(retroError.getKind() == RetroError.Kind.UNEXPECTED){
                retroError.setErrorMessage("Unexpected error...try after sometime.");
            }

            onError(retroError);
        }
        else
            onSuccess(t);
    }

    public abstract void onError(RetroError errorMessage);

    public abstract void onSuccess(T o);

    public void unsubscribe(){
        for(int i = 0;i<subscriptions.size();i++){
            subscriptions.get(i).unsubscribe();
        }
        subscriptions.clear();
    };

    public abstract void subscribe();





}
