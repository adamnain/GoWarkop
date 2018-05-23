package io.github.adamnain.gowarkop.api;

public class UtilsApi {

    public static final String BASE_URL_API = "http://10.0.3.2:8080/apigowarkop/public/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
