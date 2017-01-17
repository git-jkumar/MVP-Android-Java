package onsterlabs.android.kit;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;

import onsterlabs.network.rxnetwork.NetworkServiceFactory;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static onsterlabs.android.kit.Constants.BASE_URL;

public class BaseApplication extends Application {
    HashMap<String, String> mRequestHeaderMap = new HashMap<>();
    private boolean isHeaderUpdate = false;
    private Scheduler defaultSubscribeScheduler;
    private SessionTimeoutListener sessionTimeoutListener;

    @Override
    public void onCreate() {
        super.onCreate();
        initHeaders();
    }

    public static BaseApplication get(Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    public void initHeaders(){
        mRequestHeaderMap.put("app-type", "M");
        mRequestHeaderMap.put("Content-Type","application/json");
        mRequestHeaderMap.put("Authorization","Basic ZGlzdHJpYnV0b3ItY2xpZW50OnNlY3JldA==");
    }

    public void updateAuthHeader(String authHeader){
//        isHeaderUpdate = true;
//        SharedPreferences.Editor editor = getSharedPreferences(AppConstant.APP_PREFERENCE, Context.MODE_PRIVATE).edit();
//        editor.putString(AppConstant.AUTHORIZATION, authHeader);
//        editor.commit();
//        mRequestHeaderMap.put(AppConstant.AUTHORIZATION,authHeader);
    }

    public <S> S getServiceClient(Class<S> serviceClass) {
        if (isHeaderUpdate) {
            isHeaderUpdate = false;
            return NetworkServiceFactory.getNewInstance(BASE_URL, serviceClass, mRequestHeaderMap);
        }
        return NetworkServiceFactory.getInstance(BASE_URL, serviceClass, mRequestHeaderMap);
    }

    public Scheduler defaultSubscribeScheduler() {
        if (defaultSubscribeScheduler == null) {
            defaultSubscribeScheduler = Schedulers.io();
        }
        return defaultSubscribeScheduler;
    }

    //User to change scheduler from tests
    public void setDefaultSubscribeScheduler(Scheduler scheduler) {
        this.defaultSubscribeScheduler = scheduler;
    }

    public void onSessionTimeout(String errorMessage){
        sessionTimeoutListener.onSessionTimeout(errorMessage);
    }

    public void setSessionTimeoutListener(SessionTimeoutListener sessionTimeoutListener) {
        this.sessionTimeoutListener = sessionTimeoutListener;
    }

    public SessionTimeoutListener getSessionTimeoutListener() {
        return  this.sessionTimeoutListener;
    }
}
