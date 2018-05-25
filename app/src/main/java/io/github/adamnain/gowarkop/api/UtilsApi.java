package io.github.adamnain.gowarkop.api;

public class UtilsApi {

    public static final String BASE_URL_API = "http://adamnain.000webhostapp.com/apigowarkop/public/";

    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
