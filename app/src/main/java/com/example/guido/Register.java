package com.example.guido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;

public class Register extends AppCompatActivity {
    public static final String TAG="TAG";
    EditText mfullname,mEmail,mPassword,mCPassword;
    Button mRegisterbtn;
    TextView signup;
    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        mfullname=findViewById(R.id.fullname);
        mEmail=findViewById(R.id.email);
        mPassword=findViewById(R.id.password);
        mCPassword=findViewById(R.id.cpassword);
        mRegisterbtn=findViewById(R.id.signin);
        signup=findViewById(R.id.logintext1);
        progressBar=findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
            }
        });
        mRegisterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String cpassword = mCPassword.getText().toString().trim();
                final String fullname = mfullname.getText().toString();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if(TextUtils.isEmpty(cpassword)){
                    mCPassword.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be more than 6 letters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                          sendUserToNextActivity();
                            Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(Register.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void sendUserToNextActivity() {
        Intent intent= new Intent(Register.this,HomePage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}