package io.github.adamnain.gowarkop.api;

import io.github.adamnain.gowarkop.model.Pesan;
import io.github.adamnain.gowarkop.model.ResponseMenu;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    //list semua bangunan berdasarkan provinsi
    @GET("allmenu/")
    Call<ResponseMenu> getAllMenu(@Query("key") String key);

    @POST("pesan/")
    Call<Pesan> pesan(@Body Pesan pesan, @Query("key") String key);


}
