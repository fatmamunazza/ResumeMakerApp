package com.example.resumemaker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Education extends Fragment {
    Button save;
    EditText year,degree,institution,cgpa;

    public Education() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootview= inflater.inflate(R.layout.fragment_education, container, false);

        save = rootview.findViewById(R.id.save2);
        year = rootview.findViewById(R.id.year);
        degree = rootview.findViewById(R.id.degree);
        cgpa = rootview.findViewById(R.id.cgpa);
        institution =rootview.findViewById(R.id.institution);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Year=year.getText().toString();
                String Degree=degree.getText().toString();
                String Cgpa=cgpa.getText().toString();
                String Institution=institution.getText().toString();
               if(Year.isEmpty() || Degree.isEmpty() || Cgpa.isEmpty() || Institution.isEmpty()){
                   Toast.makeText(getActivity(),"No field sholud be empty",Toast.LENGTH_LONG).show();
               }
               else{

                   UserDbHelper user=new UserDbHelper(getActivity());
                   SQLiteDatabase db=user.getWritableDatabase();
                   user.insertData(Degree,Institution,Year,Cgpa,db);
                   Toast.makeText(getActivity(),"Submission Successfull",Toast.LENGTH_LONG).show();
               }
            }
        });
        return rootview;
    }
}
