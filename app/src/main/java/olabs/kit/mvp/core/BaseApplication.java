package olabs.kit.mvp.core;

import android.app.Application;
import olabs.kit.mvp.core.networx.WifiNetworkService;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupServices();

    }
    private void setupServices() {
        WifiNetworkService.getInstance().initializeWithApplicationContext(this);
    }
}
