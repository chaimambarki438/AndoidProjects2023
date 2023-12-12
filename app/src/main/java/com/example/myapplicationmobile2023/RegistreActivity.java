package com.example.myapplicationmobile2023;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationmobile2023.model.User;
import com.example.myapplicationmobile2023.model.UserResponse;
import com.example.myapplicationmobile2023.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistreActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextEmail = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    // Use Retrofit to fetch users from the API
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://reqres.in/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiService apiService = retrofit.create(ApiService.class);
                    Call<UserResponse> call = apiService.getUsers(3);
                    call.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                            if (response.isSuccessful()) {
                                UserResponse userResponse = response.body();
                                if (userResponse != null) {
                                    List<User> userList = userResponse.data;

                                    // Validate login for each user
                                    boolean loginSuccessful = false;
                                    for (User user : userList) {
                                        if (user.getEmail().equals(email) && user.getFirstName() .equals(password)) {
                                            loginSuccessful = true;
                                            break;
                                        }
                                    }

                                    if (loginSuccessful) {
                                        Toast.makeText(RegistreActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        // Redirect to the main activity or perform other actions
                                        Intent intent = new Intent(RegistreActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish(); // Finish the current activity to prevent going back to registration
                                    } else {
                                        Toast.makeText(RegistreActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                // Handle API error
                                Toast.makeText(RegistreActivity.this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                            // Handle network failure
                            Toast.makeText(RegistreActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Empty email or password
                    Toast.makeText(RegistreActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}