package com.saras.pppandroid.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.saras.pppandroid.R;
import com.saras.pppandroid.model.VerifyApiInterface;
import com.saras.pppandroid.model.VerifyApiQuery;
import com.saras.pppandroid.util.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScanResult extends AppCompatActivity {

    private ProgressDialog progressDialog;
    ImageView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);

        result = findViewById(R.id.result);

        // Show progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Checking ...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        // Receive place from QR code
        String place = getIntent().getStringExtra("place");

        // Make the POST API call
        verifyTicket(place);
    }


    private void verifyTicket(String place) {

        VerifyApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(VerifyApiInterface.class);

        // make payload ready
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = (currentUser != null) ? currentUser.getEmail() : "";
        VerifyApiQuery apiQuery = new VerifyApiQuery(userEmail, place);


        Call<Boolean> call = apiInterface.checkQuery(apiQuery);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(@NonNull Call<Boolean> call, @NonNull Response<Boolean> response) {
                Picasso.get().load(Boolean.TRUE.equals(response.body()) ? R.drawable.correct : R.drawable.wrong).into(result);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<Boolean> call, @NonNull Throwable t) {
                Picasso.get().load(R.drawable.wrong).into(result);
                Toast.makeText(ScanResult.this, "Failed to get status.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

}