package onsterlabs.android.kit.registration.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ttnd on 16/11/16.
 */

public class RegisterModel {

    @SerializedName("registrationRequestId")
    String registrationRequestId;

    @SerializedName("registrationStatus")
    String registrationStatus;


    public String getRegistrationRequestId() {
        return registrationRequestId;
    }

    public void setRegistrationRequestId(String registrationRequestId) {
        this.registrationRequestId = registrationRequestId;
    }

    public String getRegistrationStatus() {
        return registrationStatus;
    }

    public void setRegistrationStatus(String registrationStatus) {
        this.registrationStatus = registrationStatus;
    }
}
