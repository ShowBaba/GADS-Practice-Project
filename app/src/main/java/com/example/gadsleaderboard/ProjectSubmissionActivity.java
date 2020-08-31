package com.example.gadsleaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gadsleaderboard.util.ApiUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectSubmissionActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button submitFormBtn;
    EditText fname_et, lname_et, email_et, projectLink_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_submission);
        backBtn = findViewById(R.id.back_btn);
        submitFormBtn = findViewById(R.id.submit_form_btn);
        fname_et = findViewById(R.id.first_name);
        lname_et = findViewById(R.id.last_name);
        email_et = findViewById(R.id.email);
        projectLink_et = findViewById(R.id.project_link_tv);

        //back btn
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });

        // submit form btn
        submitFormBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProjectSubmissionActivity.this.executeSendForm(
                        fname_et.getText().toString(),
                        lname_et.getText().toString(),
                        email_et.getText().toString(),
                        projectLink_et.getText().toString()
                );
                submitFormBtn.setBackgroundResource(R.drawable.rounded_corner_btn_white);
            }
        });

    }

    private void executeSendForm(String fname, String lname, String email, String project_link) {
        UserClient userClient = ApiUtil.retrofit.create(UserClient.class);

        Call<ResponseBody> responseBodyCall = userClient.sendUserData(fname, lname, email, project_link);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ProjectSubmissionActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                clearText();
                submitFormBtn.setBackgroundResource(R.drawable.rounded_corner_btn);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ProjectSubmissionActivity.this, "Error!", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void clearText(){
        fname_et.getText().clear();
        lname_et.getText().clear();
        email_et.getText().clear();
        projectLink_et.getText().clear();
    }
}