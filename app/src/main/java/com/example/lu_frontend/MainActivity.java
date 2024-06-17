package com.example.lu_frontend;

import android.content.Intent;
import android.os.Bundle;

import com.example.lu_frontend.apiIntegration.ApiClient;
import com.example.lu_frontend.apiIntegration.ApiService;
import com.example.lu_frontend.apiIntegration.dto.LoginRequestDTO;
import com.example.lu_frontend.apiIntegration.dto.ResponseObject;
import com.example.lu_frontend.apiIntegration.dto.Student;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.lu_frontend.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiService = ApiClient.getClient().create(ApiService.class);

        findViewById(R.id.login_button).setOnClickListener(view -> {
            String password = ((EditText) findViewById(R.id.email)).getText().toString();
            String username = ((EditText) findViewById(R.id.password)).getText().toString();
            loginUser(username, password);
        });

    }

    private void loginUser(String username, String password) {
        LoginRequestDTO loginRequest = new LoginRequestDTO(username, password);
        apiService.login(loginRequest).enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseObject responseObject = response.body();
                    // Assuming the data object contains a Student object with an ID
                    Map<String, Object> data = (Map<String, Object>) responseObject.getData();
                    String studentId = Objects.toString(data.get("id"));
                    showMessageDialog(responseObject.getMessage(), responseObject.getStatus(), studentId);
                } else {
                    showMessageDialog("An error occurred", 500, null);
                }
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                showMessageDialog("Network error: " + t.getMessage(), 500, null);
            }
        });
    }

    private void showMessageDialog(String message, int status, String studentId) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (status == 200 && studentId != null) {
                        Intent intent = new Intent(MainActivity.this, CoursesActivity.class);
                        intent.putExtra("student_id", studentId);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }
}