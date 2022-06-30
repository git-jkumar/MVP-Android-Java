package olabs.kit.mvp.features.login.model;

import com.google.gson.annotations.SerializedName;

import olabs.kit.mvp.R;
import olabs.kit.mvp.core.base.BaseRequest;


/**
 * Created by Jitendra Kumar on 2/11/16.
 */

public class LoginRequest extends BaseRequest {
    @SerializedName("username")
    String username ;

    @SerializedName("password")
    String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int isValidLogin(String username, String pwd){
        return username.isEmpty()? R.string.enter_username:
                (pwd.isEmpty()?R.string.enter_password:1);
    }
}
