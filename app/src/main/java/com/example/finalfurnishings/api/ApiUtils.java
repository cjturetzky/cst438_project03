package com.example.finalfurnishings.api;

public class ApiUtils {
    public static final String BASE_URL = "https://furnishdb.herokuapp.com/";

    public static ApiService getApiService() {
        return ApiClient.getClient(BASE_URL).create(ApiService.class);
    }
}
