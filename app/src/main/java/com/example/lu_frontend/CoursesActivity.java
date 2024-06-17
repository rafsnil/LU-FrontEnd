package com.example.lu_frontend;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lu_frontend.apiIntegration.ApiClient;
import com.example.lu_frontend.apiIntegration.ApiService;
import com.example.lu_frontend.apiIntegration.dto.Course;
import com.example.lu_frontend.apiIntegration.dto.ResponseObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoursesActivity extends AppCompatActivity {

    private RecyclerView coursesRecyclerView;
    private CoursesAdapter adapter;
    private ApiService apiService;
    private EditText searchEditText;
    private List<Course> allCourses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        apiService = ApiClient.getClient().create(ApiService.class);

        coursesRecyclerView = findViewById(R.id.coursesRecyclerView);
        coursesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CoursesAdapter(new ArrayList<>());
        coursesRecyclerView.setAdapter(adapter);

        searchEditText = findViewById(R.id.searchEditText); // Assuming your EditText has this ID in XML
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                filterCourses(s.toString());
            }
        });

        String studentId = getIntent().getStringExtra("student_id");
        fetchCourses(studentId);
    }

    private void fetchCourses(String studentId) {
        apiService.getCoursesForStudent(studentId).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseObject responseObject = response.body();
                    ObjectMapper objectMapper = new ObjectMapper();

                    String jsonData = null;
                    try {
                        jsonData = objectMapper.writeValueAsString(responseObject.getData());
                        allCourses = objectMapper.readValue(jsonData, new TypeReference<List<Course>>(){});
                        adapter.updateCourses(new ArrayList<>(allCourses)); // Initial load
                    } catch (JsonProcessingException e) {
                        Log.e("CoursesActivity", "JSON parsing error", e);
                        Toast.makeText(CoursesActivity.this, "Error parsing course data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("CoursesActivity", "Failed to fetch courses with status: " + response.code());
                    Toast.makeText(CoursesActivity.this, "Failed to fetch courses: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                Log.e("CoursesActivity", "Network error", t);
                Toast.makeText(CoursesActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void filterCourses(String text) {
        Log.d("CoursesActivity", "Filtering for: " + text);
        List<Course> filteredCourses = new ArrayList<>();
        if (text.isEmpty()) {
            filteredCourses.addAll(allCourses);
        } else {
            for (Course course : allCourses) {
                if (course.getCourseName().toLowerCase().contains(text.toLowerCase())) {
                    filteredCourses.add(course);
                }
            }
        }
        Log.d("CoursesActivity", "Filtered Count: " + filteredCourses.size());
        adapter.updateCourses(filteredCourses);
    }
}
