package com.saras.pppandroid.frags;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.saras.pppandroid.R;
import com.saras.pppandroid.model.Ticket;
import com.saras.pppandroid.util.TicketAdapter;

import java.util.ArrayList;

public class Tickets extends Fragment {

    ListView ticketView;
    ArrayList<Ticket> ticketsArrayList;
    TicketAdapter ticketAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        // Initialize the ArrayList and adapter
        ticketsArrayList = new ArrayList<>();
        ticketView = view.findViewById(R.id.ticketList);

        ticketAdapter = new TicketAdapter(getActivity(), ticketsArrayList);
        ticketView.setAdapter(ticketAdapter);


        db = FirebaseFirestore.getInstance();

        // Show progress dialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching your tickets...");
        progressDialog.show();

        // Fetch tickets from Firestore
        getTickets();

        return view;
    }

    private void getTickets() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = (currentUser != null) ? currentUser.getEmail() : "";

        // Query Firestore for tickets
        db.collection("tickets")
                .whereEqualTo("person", userEmail)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            // Dismiss progress dialog and log error
                            progressDialog.dismiss();
                            Log.e("Firestore error ", error.getMessage());
                            return;
                        }

                        // Process document changes
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                // Add ticket to list and notify adapter
                                ticketsArrayList.add(new Ticket(dc.getDocument().getString("date"), dc.getDocument().getString("person"),
                                        dc.getDocument().getString("place")));
                                ticketAdapter.notifyDataSetChanged();
                            }
                        }

                        // Dismiss progress dialog
                        progressDialog.dismiss();
                    }
                });
    }
}
