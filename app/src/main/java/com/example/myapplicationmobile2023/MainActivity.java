package com.example.myapplicationmobile2023;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationmobile2023.model.User;
import com.example.myapplicationmobile2023.model.UserResponse;
import com.example.myapplicationmobile2023.service.ApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://reqres.in/api/";
    private static final int delay = 3;

    private ListView listViewUsers;
    private EditText editTextSearch;
    private Button buttonSearch;
    private UserListAdapter arrayAdapter;
    private List<User> userList;
    private List<User> filteredList;
    private TextView textViewMessage; // Add this line

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewUsers = findViewById(R.id.listViewUsers);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        textViewMessage = findViewById(R.id.textViewMessage); // Add this line

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<UserResponse> call = apiService.getUsers(delay);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        userList = userResponse.data;

                        arrayAdapter = new UserListAdapter(MainActivity.this, userList);
                        listViewUsers.setAdapter(arrayAdapter);

                        editTextSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                arrayAdapter.getFilter().filter(charSequence);
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {
                            }
                        });

                        buttonSearch.setOnClickListener(view -> {
                            String query = editTextSearch.getText().toString();

                            // Log the query
                            Log.d("Search", "Performed search with query: " + query);

                            // Check if the query is empty
                            if (!query.isEmpty()) {
                                // Log the search result
                                Log.d("Search", "Displaying search result for query: " + query);

                                // Perform search and update the list
                                performNameSearch(query);
                            } else {
                                // If the query is empty, show all users
                                Log.d("Search", "Displaying all users");
                                arrayAdapter.getFilter().filter(null);
                            }
                        });
                    }
                } else {
                    Log.e("API Error", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("API Error", "Request failed", t);
            }
        });
    }

    // Method to perform search by name
    private void performNameSearch(String query) {
        filteredList = new ArrayList<>();

        for (User user : userList) {
            String userName = user.getLastName().toLowerCase();

            if (userName.contains(query.toLowerCase().trim())) {
                filteredList.add(user);
            }
        }

        Log.d("Search", "Filtered List Size: " + filteredList.size());

        for (User user : filteredList) {
            Log.d("Search", "Filtered User: " + user.getFirstName() + " " + user.getLastName());
        }

        arrayAdapter.clear();
        arrayAdapter.addAll(filteredList);
        arrayAdapter.notifyDataSetChanged();

        // Check if the list is empty
        if (filteredList.isEmpty()) {
            // Display a message indicating that no user was found
            showMessage("No user found");
        } else {
            // Hide the message
            hideMessage();
        }
    }

    // Method to show a message
    private void showMessage(String message) {
        textViewMessage.setVisibility(View.VISIBLE);
        textViewMessage.setText(message);
    }

    // Method to hide the message
    private void hideMessage() {
        textViewMessage.setVisibility(View.GONE);
    }
}
