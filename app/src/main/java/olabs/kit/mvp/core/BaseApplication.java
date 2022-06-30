package olabs.kit.mvp.core;

import android.app.Application;

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
