package com.saras.pppandroid.frags;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.saras.pppandroid.R;
import com.saras.pppandroid.activity.GoogleLogin;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends Fragment {

    private TextView displayName, displayEmail;
    private CircleImageView displayImage;
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

        displayName = rootView.findViewById(R.id.displayName);
        displayEmail = rootView.findViewById(R.id.displayEmail);
        displayImage = rootView.findViewById(R.id.displayImage);

        return rootView; // Return the inflated view
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the current user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            // User is signed in, display user information
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();

            displayName.setText(name);
            displayEmail.setText(email);

            // Set profile picture using URL
            if (photoUrl != null) {
//                displayImage.setImageURI(photoUrl);
                Picasso.get().load(photoUrl)
                        .placeholder(R.drawable.nav_profile).error(R.drawable.nav_profile)
                        .into(displayImage);
            }
        } else {
            // User is not signed in, handle this case if necessary
            Toast.makeText(getContext(), "Error: User not signed in", Toast.LENGTH_SHORT).show();
        }
    }
}
