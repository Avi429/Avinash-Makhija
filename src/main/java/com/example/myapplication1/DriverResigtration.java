package com.example.myapplication1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class DriverResigtration extends AppCompatActivity {
    private EditText username1;
    private EditText userpassword1;
    private EditText useremail1;
    private EditText userNumber1;
    private EditText R_number;
    private String RickshawNumber;
    private Button regbutton1;
    private TextView userLogin1;
    FirebaseAuth firebaseAuth;
    String name,password,email,Number;
    //private FirebaseAuth firebaseAuth1;
    private FirebaseAuth.AuthStateListener firebaseListener;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_resigtrartion);

        username1=findViewById(R.id.Driverusername);
        useremail1=findViewById(R.id.Driveruseremail);
        userpassword1=findViewById(R.id.Driveruserpassword);
        regbutton1=findViewById(R.id.btnRegister);
        userLogin1=findViewById(R.id.DriverbtnRegister);
        userNumber1=findViewById(R.id.DriveretNumber);
        R_number = findViewById(R.id.editText2);
        firebaseAuth= FirebaseAuth.getInstance();

        //regbutton.setOnClickListener(new View.OnClickListener() {
        // @Override
        //  public void onClick(View view) {
        //  Intent intent1=new Intent(RegistrationActivity.this,MainActivity.class);
        // startActivity(intent1);


        regbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Upload data to database
                    String user_email=useremail1.getText().toString().trim();
                    String user_password=userpassword1.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(RegistrationActivity.this, "Registaration Scucessful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(RegistrationActivity.this,SecondActivity.class));
                                sendEmailVerification();
                            }else
                            {
                                FirebaseAuthException e = (FirebaseAuthException)task.getException();
                                Toast.makeText(DriverResigtration.this,"Registaration Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                //message.hide();
                                return;
                            }

                        }
                    });
                }
            }
        });
        //}
        //});

        userLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(DriverResigtration.this,Driver_LoginPage.class);
                startActivity(intent1);
            }
        });
    }


    private boolean validate(){
        boolean result =false;

        name = username1.getText().toString();
        password = userpassword1.getText().toString();
        email = useremail1.getText().toString().trim();
        Number = userNumber1.getText().toString().trim();
        RickshawNumber = R_number.getText().toString().trim();


        if(name.isEmpty() || password.isEmpty() || email.isEmpty() ||  Number.isEmpty()){
            Toast.makeText(this, "Please Enter All The Details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }
        return result;
    }

    private void sendEmailVerification()
    {
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        sendUserData();
                        Toast.makeText(DriverResigtration.this,"Sucessfully Register , Verification mail sent",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(DriverResigtration.this,Driver_LoginPage.class));
                    }
                    else {
                        Toast.makeText(DriverResigtration.this," Verification mail  hasn't been sent!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUserData()
    {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myref = firebaseDatabase.getReference().child("Drivers").child(firebaseAuth.getUid());
        userprofile userprofile1 =new userprofile(name,email,Number);
        myref.setValue(userprofile1);
        myref.child("RicksahwNumber").setValue(RickshawNumber);
    }

}


