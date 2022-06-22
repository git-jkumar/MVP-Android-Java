package olabs.kit.mvp.core.networx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;

public class WifiNetworkService {
    private static WifiNetworkService wifiNetworkService;
    WifiManager wifiManager;
    ConnectivityManager connectivityManager;

    private WifiNetworkService() {

    }

    public static WifiNetworkService getInstance() {
        if (wifiNetworkService == null) {
            wifiNetworkService = new WifiNetworkService();
        }
        return wifiNetworkService;

    }

    public void initializeWithApplicationContext(Context context) {
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
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
