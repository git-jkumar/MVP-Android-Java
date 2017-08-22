package olabs.kit.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import olabs.kit.rxnetworx.RetroError;

public class BaseViewModel {
    protected IView mIView;

    public BaseViewModel(IView iView) {
        mIView = iView;
    }

    public HashMap<String, String> getHeaderConfiguration() {
        Properties localProps = new Properties();
        HashMap<String, String> requestHeaders = new HashMap<>();
        try {
            //API HEADERS ARE ACCESSED FROM PROPERTIES FILE IN ASSETS FOLDER.CHANGE ACCORDINGLY FOR APP USAGE
            localProps.load(BaseApplication.getContext().getAssets().open("app.properties"));
            requestHeaders.put("app-type", localProps.getProperty("app-type"));
            requestHeaders.put("Content-Type", localProps.getProperty("Content-Type"));
            requestHeaders.put("Authorization", localProps.getProperty("Authorization"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestHeaders;

    }

    public String getBaseUrl() {
        return Constants.BASE_URL;
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
                    mIView.showMessage("Something went wrong...try after sometime");
                    break;
                default:
                    mIView.showMessage("Something went wrong...please try again.");
                    break;
            }
        } else if (retroError.getKind() == RetroError.Kind.NETWORK) {
            mIView.showMessage("Unable to connect to server....try after sometime");
        }
        if (retroError.getKind() == RetroError.Kind.UNEXPECTED) {
            mIView.showMessage("Unexpected error....try after sometime");
        }
    }


}
