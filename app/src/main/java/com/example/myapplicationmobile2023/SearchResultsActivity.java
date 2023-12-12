package com.example.myapplicationmobile2023;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationmobile2023.model.User;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.search);

            // Get the ListView from the layout
            ListView listViewSearchResults = findViewById(R.id.listViewSearchResults);

            // Retrieve the search results from the intent
            List<User> searchResults = getIntent().getParcelableArrayListExtra("searchResults");

            // Create an adapter and set it to the ListView
            ArrayAdapter<User> arrayAdapter = new ArrayAdapter<>(this, R.layout.list_item_user, searchResults);
            listViewSearchResults.setAdapter(arrayAdapter);
        }
    }


