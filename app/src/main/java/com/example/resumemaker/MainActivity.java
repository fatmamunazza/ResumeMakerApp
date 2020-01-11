package com.example.resumemaker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText userName,password;
    Button login,register,skip;

    FirebaseDatabase db;
    DatabaseReference myref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName=findViewById(R.id.userName);
        password=findViewById(R.id.password);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        skip=findViewById(R.id.skip);

        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("Mypref",0);
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,MainPage.class);
                startActivity(i);
            }
        });




        if(sharedPreferences.getString("username",null)!=null && sharedPreferences.getString("password",null)!=null ){
            Intent i=new Intent(MainActivity.this,MainPage.class);

            startActivity(i);

        }
        else{

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(MainActivity.this,Registration.class);
                    startActivity(i);
                }
            });

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    db=FirebaseDatabase.getInstance();
                    myref=db.getReference().child("users");

                    final String un=userName.getText().toString().trim();
                    final String paswd=password.getText().toString().trim();

                   if(!un.isEmpty() && !paswd.isEmpty()){
                      myref.orderByChild("username").equalTo(un).addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              if(dataSnapshot.exists()){
                                  for(DataSnapshot data:dataSnapshot.getChildren()){
                                      String Passwrd=data.child("password").getValue().toString();
                                      String Name=data.child("name").getValue().toString();
                                      if(Passwrd.equals(paswd)){
                                          Intent i=new Intent(MainActivity.this,MainPage.class);
                                          startActivity(i);
                                          editor.putString("username",userName.getText().toString());
                                          editor.putString("password",password.getText().toString());
                                          editor.putString("name",Name);
                                          editor.commit();

                                      }
                                      else{
                                          Toast.makeText(MainActivity.this,"Incorrect password",Toast.LENGTH_LONG).show();
                                      }
                                  }
                              }
                              else{
                                  Toast.makeText(MainActivity.this,"Incorrect username",Toast.LENGTH_LONG).show();

                              }
                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {

                          }
                      });

                   }
                   else{
                        Toast.makeText(MainActivity.this,"No field should be empty",Toast.LENGTH_LONG).show();

                   }
                }
            });


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSharedPreferences("Mypref3",MODE_PRIVATE).edit().clear().commit();
    }
}
