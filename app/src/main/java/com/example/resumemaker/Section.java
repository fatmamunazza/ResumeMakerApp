package com.example.resumemaker;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Section extends Fragment {
    EditText section;
    Button save6;
     public Section(){

     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview=inflater.inflate(R.layout.fragment_section, container, false);

        section=rootview.findViewById(R.id.section);
        save6=rootview.findViewById(R.id.save6);


        save6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Section=section.getText().toString();
                if(Section.isEmpty()){
                    section.setError("No field should be empty");
                }
                else{

                    UserDbHelper user=new UserDbHelper(getActivity());
                    SQLiteDatabase db=user.getWritableDatabase();
                    user.sectionData(Section,db);
                    Toast.makeText(getActivity(), "Submission successfull", Toast.LENGTH_LONG).show();
                    Intent i=new Intent(getActivity(),CV.class);
                    startActivity(i);

                }
            }
        });

        return rootview;
    }

}
