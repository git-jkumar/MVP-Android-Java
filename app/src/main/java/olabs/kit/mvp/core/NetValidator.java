package olabs.kit.mvp.core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;

public class NetValidator {
    private static NetValidator netValidator;
    WifiManager wifiManager;
    ConnectivityManager connectivityManager;

    private NetValidator() {

    }

    public static NetValidator getInstance() {
        if (netValidator == null) {
            netValidator = new NetValidator();
        }
        return netValidator;

    }

    public void initializeWithApplicationContext(Context context) {
        wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    // Helper that detects if online
    public boolean isOnline() {
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
}
