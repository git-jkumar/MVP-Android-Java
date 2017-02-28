package olabs.kit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import olabs.kit.BaseResponse;


/**
 * Created by Salil Kaul on 2/11/16.
 */

public class RegisterResponse extends BaseResponse {
    @SerializedName("data")
    @Expose
    private RegisterModel data;

    /**
     * @return The data
     */
    public RegisterModel getData() {
        return data;
    }

    /**
     * @param data The data
     */
    public void setData(RegisterModel data) {
        this.data = data;
    }


}
