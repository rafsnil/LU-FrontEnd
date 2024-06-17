package com.example.lu_frontend.apiIntegration;

import com.example.lu_frontend.apiIntegration.dto.Course;
import com.example.lu_frontend.apiIntegration.dto.LoginRequestDTO;
import com.example.lu_frontend.apiIntegration.dto.ResponseObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/v1/student/login")
    Call<ResponseObject> login(@Body LoginRequestDTO loginRequest);

    @GET("/api/v1/course/{id}")
    Call<ResponseObject> getCoursesForStudent(@Path("id") String id);
}
