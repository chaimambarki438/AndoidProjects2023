package com.example.myapplicationmobile2023.service;

import com.example.myapplicationmobile2023.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
        @GET("users")
        Call<UserResponse> getUsers(@Query("delay") int delay);


}
