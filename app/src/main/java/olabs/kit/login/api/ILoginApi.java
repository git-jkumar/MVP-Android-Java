package olabs.kit.login.api;


import olabs.kit.login.model.LoginResponse;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ILoginApi {
    @FormUrlEncoded
    @POST("v1/oauth/token")
    Observable<LoginResponse> doLogin(@Field("grant_type") String grantType, @Field("username") String userID, @Field("password") String password);

}
