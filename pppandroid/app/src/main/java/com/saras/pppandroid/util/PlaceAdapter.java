package com.saras.pppandroid.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.saras.pppandroid.R;
import com.saras.pppandroid.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceAdapter  extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Place place);
    }


   Context context;
   ArrayList<Place> placeArrayList;
   private OnItemClickListener listener;


    public PlaceAdapter(Context context, ArrayList<Place> placeArrayList, OnItemClickListener listener) {
        this.context = context;
        this.placeArrayList = placeArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlaceAdapter.PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.place_item, parent, false);

        return new PlaceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapter.PlaceViewHolder holder, int position) {

        Place place = placeArrayList.get(position);

        // set data of place
        holder.placeName.setText(place.getPlaceName());

        List<SlideModel> slideImgs = new ArrayList<>();
        List<String> imgUrls = place.getImages();

        for (String url : imgUrls){
            slideImgs.add(new SlideModel(url, ScaleTypes.FIT));
        }

        holder.imageSlider.setImageList(slideImgs, ScaleTypes.FIT);

        // add click listener (bcz images are having more zindex, click is not working on them )
        holder.bind(placeArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return placeArrayList.size();
    }

    public static class PlaceViewHolder extends RecyclerView.ViewHolder{

        TextView placeName;
        ImageSlider imageSlider;
        // add items

        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);

            placeName = itemView.findViewById(R.id.placeName);
            imageSlider = itemView.findViewById(R.id.imageSlider);
        }

        public void bind(final Place place, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(place);
                }
            });
        }

    }
}
