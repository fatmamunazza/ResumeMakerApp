package com.example.resumemaker;

import android.content.Context;
import android.content.SharedPreferences;
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


public class Skills extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    EditText skill;
    Button save5;
    public Skills() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_skills, container, false);

        skill=rootview.findViewById(R.id.skill);
        save5=rootview.findViewById(R.id.save8);


        save5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Skill=skill.getText().toString();
                if(Skill.isEmpty()){
                    skill.setError("No field should be empty");
                }
                else{

                    UserDbHelper user=new UserDbHelper(getActivity());
                    SQLiteDatabase db=user.getWritableDatabase();
                    user.skillData(Skill,db);
                    Toast.makeText(getActivity(), "Submission successfull", Toast.LENGTH_LONG).show();

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frag, new Skills());
                    transaction.commit();
                }
            }
        });

        return rootview;
    }

}
