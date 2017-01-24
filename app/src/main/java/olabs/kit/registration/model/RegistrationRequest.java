package olabs.kit.registration.model;

import android.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;


/**
 * Created by ttnd on 16/11/16.
 */

public class RegistrationRequest extends BaseObservable {


    @SerializedName("arnCode")
    String arnCode;


    @SerializedName("termsAndConditions")
    boolean termsAndConditions = true;

    @SerializedName("bankAccountNumber")
    String bankAccountNumber;

    @SerializedName("emailAddress")
    String emailAddress;

    @SerializedName("gRecaptchaResponse")
    String gRecaptchaResponse = "default";

    @SerializedName("mobileNumber")
    String mobileNumber;

    public String getArnCode() {
        return arnCode;
    }

    public void setArnCode(String arnCode) {
        this.arnCode = "ARN-" + arnCode;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getRecaptchaResponse() {
        return gRecaptchaResponse;
    }

    public void setRecaptchaResponse(String gRecaptchaResponse) {
        this.gRecaptchaResponse = gRecaptchaResponse;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
