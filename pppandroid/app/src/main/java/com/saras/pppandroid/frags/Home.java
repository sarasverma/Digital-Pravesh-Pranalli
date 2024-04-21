package com.saras.pppandroid.frags;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.saras.pppandroid.activity.PlaceActivity;
import com.saras.pppandroid.model.Place;
import com.saras.pppandroid.util.PlaceAdapter;
import com.saras.pppandroid.R;

import java.util.ArrayList;

public class Home extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Place> placeArrayList;
    PlaceAdapter placeAdapter;
    FirebaseFirestore db;

    ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();


        recyclerView = rootView.findViewById(R.id.placeList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        db = FirebaseFirestore.getInstance();
        placeArrayList = new ArrayList<Place>();
        placeAdapter = new PlaceAdapter(getActivity(), placeArrayList, new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Place place) {
                Intent i = new Intent(getActivity(), PlaceActivity.class);
                i.putExtra("placeName", place.getPlaceName());
                startActivity(i);
            }
        });

        recyclerView.setAdapter(placeAdapter);

        // listener for changes
        EventChangeListener();

        return rootView;
    }

    private void EventChangeListener() {

        db.collection("places").orderBy("placeName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null){

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Log.e("Firestore error ", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType() == DocumentChange.Type.ADDED){

                                placeArrayList.add(dc.getDocument().toObject(Place.class));
                            }

                            placeAdapter.notifyDataSetChanged();

                            // remove loading
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }
                    }
                });

    }
}