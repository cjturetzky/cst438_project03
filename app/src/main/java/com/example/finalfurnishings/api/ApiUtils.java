package com.example.finalfurnishings.api;

public class ApiUtils {
    //public static final String BASE_URL = "https://furnishdb.herokuapp.com/";
    public static final String BASE_URL = "http://10.0.2.2:8080/";

    public static ApiService getApiService() {
        return ApiClient.getClient(BASE_URL).create(ApiService.class);
    }
}
