package com.saras.trying.frags;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.saras.trying.R;
import com.saras.trying.activity.GoogleLogin;

public class Profile extends Fragment {

    private Button signOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        signOut = rootView.findViewById(R.id.signOut); // Use rootView to find the signOut button
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), GoogleLogin.class); // Use getActivity() to get the context
                startActivity(i);
                getActivity().finish(); // Close the current activity after starting MainActivity
            }
        });

        return rootView; // Return the inflated view
    }
}
