package io.github.adamnain.gowarkop.api;

import io.github.adamnain.gowarkop.model.Login;
import io.github.adamnain.gowarkop.model.Pesan;
import io.github.adamnain.gowarkop.model.ResponseLogin;
import io.github.adamnain.gowarkop.model.ResponseMenu;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    //list semua bangunan berdasarkan provinsi
    @GET("allmenu/")
    Call<ResponseMenu> getAllMenu(@Query("key") String key);

    @POST("pesan/")
    Call<ResponseBody> postPesan(@Body Pesan pesan, @Query("key") String key);

    @FormUrlEncoded
    @POST("login/")
    Call<ResponseLogin> login(@Field("email") String email, @Field("password") String password, @Query("key") String key);


}
