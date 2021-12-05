package com.example.finalfurnishings.api;

import com.example.finalfurnishings.DataObjects.Listing;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("users")
    Call<String> register(@Query("username") String username, @Query("password") String password);

    @POST("login")
    Call<String> login(@Query("user") String username, @Query("pass") String password);

    @POST("logout")
    Call <String> logout();

    @POST("items")
    Call<String> postListing(@Query("item_name") String name, @Query("price") double price, @Query("description") String description);

    @GET("items")
    Call<List<Listing>> getListings();
}
