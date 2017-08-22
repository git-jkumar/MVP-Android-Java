package olabs.kit.base;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("status")
    @Expose
    public boolean status;
    @SerializedName("message")
    @Expose
    public String message;

}
