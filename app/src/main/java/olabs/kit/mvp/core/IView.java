package olabs.kit.mvp.core;

import olabs.kit.mvp.core.networx.NetworkError;

/**
 * Created by Jitendra Kumar on 27/2/17.
 */

public interface IView {
    void showMessage(int messageStringId);
    void showProgress();
    void hideProgress();
    void onNetworkError(NetworkError networkError);
}
