package com.example.finalfurnishings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalfurnishings.Adapters.ListingAdapter;
import com.example.finalfurnishings.DataObjects.Listing;
import com.example.finalfurnishings.api.ApiService;
import com.example.finalfurnishings.api.ApiUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrowseActivity extends AppCompatActivity {

    ApiService apiService;
    RecyclerView recyclerView;
    ListingAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Listing> listings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        apiService = ApiUtils.getApiService();
        recyclerView = findViewById(R.id.browseRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);


        getListings();
    }

    private void getListings() {
        Call<List<Listing>> call = apiService.getListings();

        call.enqueue(new Callback<List<Listing>>() {
            @Override
            public void onResponse(Call<List<Listing>> call, Response<List<Listing>> response) {
                listings = response.body();
                setupRecycler();
            }

            @Override
            public void onFailure(Call<List<Listing>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void setupRecycler() {
        adapter = new ListingAdapter(listings);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}