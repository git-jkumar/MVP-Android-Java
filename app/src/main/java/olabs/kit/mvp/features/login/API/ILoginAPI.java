package olabs.kit.mvp.features.login.API;


import olabs.kit.mvp.core.base.IBaseApi;
import olabs.kit.mvp.features.login.model.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ILoginAPI extends IBaseApi {

    @FormUrlEncoded
    @POST("v1/oauth/token")
    Call<LoginResponse> doLogin(@Field("username") String arnCode, @Field("password") String password);
}
