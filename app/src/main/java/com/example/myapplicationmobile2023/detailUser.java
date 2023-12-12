package com.example.myapplicationmobile2023;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationmobile2023.model.User;
import com.squareup.picasso.Picasso;

public class detailUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_user);

        ImageView imageViewAvatarDetail = findViewById(R.id.imageViewAvatarDetail);
        TextView textViewNameDetail = findViewById(R.id.textViewNameDetail);
         TextView textViewEmail=findViewById(R.id.textViewEmail);

        // Retrieve user details from intent
        User currentUser = getIntent().getParcelableExtra("user");

        // Display user details
        if (currentUser != null) {
            // Load the image from the URL using Picasso into imageViewAvatarDetail
            Picasso.get().load(currentUser.getAvatar()).into(imageViewAvatarDetail);

            // Set the user's name in textViewNameDetail
            textViewNameDetail.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            textViewEmail.setText(currentUser.getEmail());
        }
    }
}
