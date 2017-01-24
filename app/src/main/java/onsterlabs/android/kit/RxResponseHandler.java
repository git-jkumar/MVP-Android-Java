package onsterlabs.android.kit;

import java.util.ArrayList;

import onsterlabs.network.RetroError;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by ttnd on 23/1/17.
 */

public abstract class RxResponseHandler<T> implements Action1<T> {
    protected ArrayList<Subscription> subscriptions = new ArrayList<>();

    @Override
    public void call(T t) {
        if (t instanceof RetroError) {
            onError((RetroError)t);
        }
        else
            onSuccess(t);
    }

    public abstract void onError(RetroError errorMessage);

    public abstract void onSuccess(T o);

    public abstract void subscribe();

    public void unsubscribe(){
        for(int i = 0;i<subscriptions.size();i++){
            subscriptions.get(i).unsubscribe();
        }
        subscriptions.clear();
    }
}
