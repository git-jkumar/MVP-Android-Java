package olabs.kit.mvp.core;

/**
 * Created by Jitendra Kumar on 27/2/17.
 */

public interface IView {
    void showMessage(int messageStringId);
    void showProgress();
    void hideProgress();
}
