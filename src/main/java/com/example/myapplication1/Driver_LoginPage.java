package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.FallbackServiceBroker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class Driver_LoginPage extends AppCompatActivity {


    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseListener;
    //  private progressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver__login_page);

        Name = (EditText)findViewById(R.id.D_name);
        Password = (EditText)findViewById(R.id.D_password);
        Login = (Button)findViewById(R.id.D_login);
        //Info = (TextView)findViewById(R.id.textView);
        userRegistration = (TextView)findViewById(R.id.D_signup);
        //Info.setText("No of Attempts=0");

        firebaseAuth = FirebaseAuth.getInstance();
        //progressDialog = new progressDialog(this);
        firebaseListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Toast.makeText(Driver_LoginPage.this,"You are looged in",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Driver_LoginPage.this,DriverMap.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(Driver_LoginPage.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name1 = Name.getText().toString();
                String Password1 = Password.getText().toString();
                if(Name1.isEmpty() || Password1.isEmpty() ) {
                    Toast.makeText(Driver_LoginPage.this,"Please Enter All Details",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this,"Please Enter all Deatils:",Toast.LENGTH_SHORT).show();
                }
                else {
                    validate(Name.getText().toString(),Password.getText().toString());
                }
            }
        });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Driver_LoginPage.this,DriverResigtration.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String UserEmail, String USerPassword){
        firebaseAuth.signInWithEmailAndPassword(UserEmail, USerPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // Toast.makeText(MainActivity.this,"Login Suceesfull" , Toast.LENGTH_SHORT).show();
                    checkEmailVerification();
                }
                else {
                    FirebaseAuthException e = (FirebaseAuthException)task.getException();
                    Toast.makeText(Driver_LoginPage.this,"Login Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    //message.hide();

                    //Toast.makeText(MainActivity.this,"Please Enter All Required details" , Toast.LENGTH_SHORT).show();

                }
            }
        });
    }


    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseListener);
    }
    private void checkEmailVerification() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag) {
            finish();
            startActivity(new Intent(Driver_LoginPage.this, DriverMap.class));
        }
        else {
            Toast.makeText(this,"Verify Your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
