package olabs.kit.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import olabs.kit.common.AppConstant;
import olabs.network.rxnetwork.RetroError;

public class BaseViewModel {
    protected IView mIView;

    public BaseViewModel(IView iView) {
        mIView = iView;
    }

    public HashMap<String, String> getHeaderConfiguration() {
        Properties localProps = new Properties();
        HashMap<String, String> requestHeades = new HashMap<>();
        try {

            localProps.load(BaseApplication.getContext().getAssets().open("app.properties"));
            requestHeades.put("app-type", localProps.getProperty("app-type"));
            requestHeades.put("Content-Type", localProps.getProperty("Content-Type"));
            requestHeades.put(AppConstant.AUTHORIZATION, localProps.getProperty(AppConstant.AUTHORIZATION));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestHeades;

    }

    public String getBaseUrl() {
        return AppConstant.BASE_URL;
    }

    public void onSuccess(String message) {
        mIView.showMessage(message);
    }

    public void onError(RetroError retroError) {
        if (retroError.getKind() == RetroError.Kind.HTTP) {
            switch (retroError.getHttpErrorCode()) {
                case 401:
                    mIView.showMessage("Your session has expired...please login again");
                    break;
                case 500:
                    mIView.showMessage("ds");
                    break;
                default:
                    mIView.showMessage("ds");
                    break;
            }
        } else if (retroError.getKind() == RetroError.Kind.NETWORK) {
            mIView.showMessage("sdf");
        }
        if (retroError.getKind() == RetroError.Kind.UNEXPECTED) {
            mIView.showMessage("sdf");
        }
    }


}
