package com.example.finalfurnishings.api;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("users")
    Call<ResObj> register(@Query("username") String username, @Query("password") String password);
}
