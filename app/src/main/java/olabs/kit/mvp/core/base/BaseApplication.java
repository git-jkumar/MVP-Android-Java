package olabs.kit.mvp.core.base;

import android.app.Application;

import olabs.kit.mvp.core.NetValidator;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupServices();

    }
    private void setupServices() {
        NetValidator.getInstance().initializeWithApplicationContext(this);
    }
}
