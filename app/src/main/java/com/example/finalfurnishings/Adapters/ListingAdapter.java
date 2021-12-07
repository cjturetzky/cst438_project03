package com.example.finalfurnishings.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalfurnishings.DataObjects.Listing;
import com.example.finalfurnishings.R;

import java.util.List;

public class ListingAdapter extends RecyclerView.Adapter<ListingAdapter.ListingViewHolder> {
    private List<Listing> mListings;

    public static class ListingViewHolder extends RecyclerView.ViewHolder {
        public TextView listingView;
        //public Button purchaseButton;

        public ListingViewHolder(@NonNull View itemView) {
            super(itemView);
            listingView = itemView.findViewById(R.id.listingView);
            //purchaseButton = itemView.findViewById(R.id.purchaseButton);
        }
    }
    public ListingAdapter(List<Listing> listings) {
        mListings = listings;
    }

    @NonNull
    @Override
    public ListingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listing_card, parent, false);
        ListingViewHolder lvh = new ListingViewHolder(v);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListingViewHolder holder, int position) {
        Listing currentListing = mListings.get(position);

        holder.listingView.setText(currentListing.toString());
    }

    @Override
    public int getItemCount() {
        return mListings.size();
    }
}
