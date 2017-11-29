package rain.com.dribbble_client.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import rain.com.dribbble_client.mvp.model.Shots;
import rain.com.dribbble_client.mvp.model.UserBean;
import rain.com.dribbble_client.mvp.model.UserToken;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by HwanJ.Choi on 2017-5-16.
 */

public interface DribbbleService {

    String BASE_URL = "https://api.dribbble.com/v1/";

    @FormUrlEncoded
    @POST("https://dribbble.com/oauth/token")
    Observable<UserToken> getUserToken(@Field("client_id") String clientID,
                                       @Field("client_secret") String clientSecret,
                                       @Field("code") String code);

    @GET("shots")
    Observable<List<Shots>> getShotsList(@QueryMap Map<String, String> params);

    @GET("shots/{id}")
    Observable<Shots> getShotById(@Path("id") int id);

    @GET("users")
    Observable<UserBean> getUserById(@Path("user") int userId);

    @GET("user")
    Observable<UserBean> getUserAuthenticated();
}
