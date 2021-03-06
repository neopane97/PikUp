package com.pikup.pash.pikup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetActivity extends AppCompatActivity {
    private EditText ForgetPassword;
    private Button buttonForgetSubmit;
    private Button buttonForgetBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);


        ForgetPassword = (EditText) findViewById(R.id.InputEmail);
        buttonForgetSubmit = (Button) findViewById(R.id.buttonForgetSubmit);
        buttonForgetBack = (Button) findViewById(R.id.buttonForgetBack);
        progressBar = (ProgressBar) findViewById(R.id.ForgetProgressBar);

        //Getting Firebase authentication instance
        auth = FirebaseAuth.getInstance();

        //Intent back to the login page
        buttonForgetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Case to handle when user forget the password
        buttonForgetSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if the user input is empty, it toast a message for user
                String email = ForgetPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // if the task is succes then it generate email to user set the new password with an instructions
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgetActivity.this, LoginActivity.class));
                                    finish();
                                }
                                //else it generate a failure message
                                else {
                                    Toast.makeText(ForgetActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
