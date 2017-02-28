package olabs.kit.API;


import olabs.kit.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ILoginAPI {

    @FormUrlEncoded
    @POST("v1/oauth/token")
    Call<LoginResponse> doLogin(@Field("username") String arnCode, @Field("grant_type") String grant_type, @Field("password") String password);
}
