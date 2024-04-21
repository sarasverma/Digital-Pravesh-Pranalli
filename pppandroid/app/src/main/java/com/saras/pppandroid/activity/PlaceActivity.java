package com.saras.pppandroid.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.saras.pppandroid.R;
import com.saras.pppandroid.model.Place;
import com.saras.pppandroid.model.Ticket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PlaceActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    ProgressDialog progressDialog;

    ImageSlider images;
    TextView name, price, loc, days, hours, no, email, website,
        nearByAttraction, vistiorTips, restriction;

    CheckBox checkBoxCafeteria, checkBoxGuidedTours, checkBoxParking,
            checkBoxRestrooms, checkBoxSouvenirShop, checkBoxWheelchairAccessible;

    Button buyTicket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching place...");
        progressDialog.show();

        Log.d("saras", "place Activity mei aya");

        db = FirebaseFirestore.getInstance();

        String place = getIntent().getStringExtra("placeName");

        images = findViewById(R.id.pimage);
        name = findViewById(R.id.pName);
        price = findViewById(R.id.pprice);
        loc = findViewById(R.id.loc);
        days = findViewById(R.id.ptimeDays);
        hours = findViewById(R.id.ptimeHours);
        no = findViewById(R.id.pno);
        email = findViewById(R.id.pemail);
        website = findViewById(R.id.pwebsite);
        nearByAttraction = findViewById(R.id.nearByAttraction);
        vistiorTips = findViewById(R.id.visitorTips);
        restriction = findViewById(R.id.restriction);

        checkBoxCafeteria = findViewById(R.id.checkBoxCafeteria);
        checkBoxGuidedTours = findViewById(R.id.checkBoxGuidedTours);
        checkBoxParking = findViewById(R.id.checkBoxParking);
        checkBoxRestrooms = findViewById(R.id.checkBoxRestrooms);
        checkBoxSouvenirShop = findViewById(R.id.checkBoxSouvenirShop);
        checkBoxWheelchairAccessible = findViewById(R.id.checkBoxWheelchairAccessible);

        getData(place);

        progressDialog.dismiss();

        // set on Click listener for buying ticket
        buyTicket = findViewById(R.id.buyTicket);
        buyTicket.setOnClickListener((View v) -> {buy(place);});

    }

    private void getData(String placeName) {
        db.collection("places")
                .whereEqualTo("placeName", placeName)
                .limit(1) // Limit to 1 document
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            // Handle errors
                            Log.e("getData", "Error fetching document", error);
                            return;
                        }

                        if (value != null && !value.isEmpty()) {
                            DocumentSnapshot documentSnapshot = value.getDocuments().get(0);
                            Place place = documentSnapshot.toObject(Place.class);

                            // set data
                            List<SlideModel> slideImgs = new ArrayList<>();
                            List<String> imgUrls = place.getImages();

                            for (String url : imgUrls){
                                slideImgs.add(new SlideModel(url, ScaleTypes.FIT));
                            }

                            images.setImageList(slideImgs, ScaleTypes.FIT);
                            name.setText(place.getPlaceName());
                            price.setText(place.getEntryFees());
                            loc.setText(place.getLocation());
                            days.setText(place.getOpeningDays());
                            hours.setText(place.getOpeningHours());
                            no.setText(place.getContactInfo().getPhoneNumber());
                            email.setText(place.getContactInfo().getEmail());
                            website.setText(place.getContactInfo().getWebsite());
                            nearByAttraction.setText(place.getAdditionalInfo().getNearbyAttractions());
                            vistiorTips.setText(place.getAdditionalInfo().getVisitorTips());
                            restriction.setText(place.getAdditionalInfo().getRestrictions());

                            // all checkbox
                            checkBoxCafeteria.setChecked(place.getFacilities().isCafeteria());
                            checkBoxGuidedTours.setChecked(place.getFacilities().isGuidedTours());
                            checkBoxParking.setChecked(place.getFacilities().isParking());
                            checkBoxRestrooms.setChecked(place.getFacilities().isRestrooms());
                            checkBoxSouvenirShop.setChecked(place.getFacilities().isSouvenirShop());
                            checkBoxWheelchairAccessible.setChecked(place.getFacilities().isWheelchairAccessible());
                        } else {
                            // Document does not exist or query result is empty
                            Log.d("getData", "No such document");
                        }
                    }
                });
    }

    private void buy(String placeName) {
        progressDialog.setMessage("Buying ticket...");
        progressDialog.show();

        // Make payload ready
        @SuppressLint("SimpleDateFormat") // Suppress warning
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(new Date());
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = (currentUser != null) ? currentUser.getEmail() : "";

        // Create ticket object
        Ticket ticket = new Ticket(formattedDate, userEmail, placeName);

        // Check if ticket already exists
        db.collection("tickets")
                .whereEqualTo("date", formattedDate)
                .whereEqualTo("person", userEmail)
                .whereEqualTo("place", placeName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                // Ticket already exists, handle the case
                                // For example, you can update the existing ticket or show a message
                                Toast.makeText(PlaceActivity.this, "You already have a ticket for " + placeName, Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            } else {
                                // Ticket doesn't exist, add new ticket
                                addNewTicket(ticket);
                            }
                        } else {
                            Log.w("error", "Error getting documents.", task.getException());
                            Toast.makeText(PlaceActivity.this, "Error while purchasing ticket", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }

    private void addNewTicket(Ticket ticket) {
        db.collection("tickets")
                .add(ticket)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("error", "DocumentSnapshot added with ID: " + documentReference.getId());

                        Intent i = new Intent(PlaceActivity.this, MainActivity.class);

                        Toast.makeText(PlaceActivity.this, "Successfully purchased ticket for " + ticket.getPlace(), Toast.LENGTH_LONG).show();

                        progressDialog.dismiss();
                        startActivity(i);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("error", "Error adding document", e);
                        Toast.makeText(PlaceActivity.this, "Error while purchasing ticket", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

}