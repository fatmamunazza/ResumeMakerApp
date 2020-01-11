package com.example.resumemaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Registration extends AppCompatActivity {

    TextView textname,userName,textphone,Password,CPassword;
    Button login,register;

    FirebaseDatabase db;
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        textname=findViewById(R.id.name);
        userName=findViewById(R.id.userName);
        textphone=findViewById(R.id.phone);
        Password=findViewById(R.id.password);
        CPassword=findViewById(R.id.cPassword);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Registration.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password= Password.getText().toString().trim();
                String cpassword= CPassword.getText().toString().trim();
                if(password.equals(cpassword)){

                    db=FirebaseDatabase.getInstance();
                    myref=db.getReference().child("users");
                    DatabaseReference cref=myref.push();
                    cref.child("name").setValue(textname.getText().toString());
                    cref.child("username").setValue(userName.getText().toString());
                    cref.child("phone").setValue(textphone.getText().toString());
                    cref.child("password").setValue(Password.getText().toString());

                    Toast.makeText(Registration.this,"Registration Successfull",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(Registration.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(Registration.this,"Password does not match",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
