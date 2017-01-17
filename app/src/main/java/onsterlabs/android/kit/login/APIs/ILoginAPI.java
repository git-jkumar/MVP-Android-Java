package onsterlabs.android.kit.login.APIs;


import onsterlabs.android.kit.login.model.LoginResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ILoginAPI {

    @FormUrlEncoded
    @POST("v1/oauth/token")
    Observable<LoginResponse> doLogin(@Field("username") String arnCode, @Field("grant_type") String grant_type, @Field("password") String password);
}
