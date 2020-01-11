package com.example.resumemaker;

import android.content.Context;
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


public class Projects extends Fragment {

    EditText project;
    Button save7;
    public Projects() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_projects, container, false);
        project=view.findViewById(R.id.project);
        save7=view.findViewById(R.id.save7);


        save7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Project=project.getText().toString();
                if(Project.isEmpty()){
                    project.setError("No field should be empty");
                }
                else{

                    UserDbHelper user=new UserDbHelper(getActivity());
                    SQLiteDatabase db=user.getWritableDatabase();
                    user.projectData(Project,db);
                    Toast.makeText(getActivity(), "Submission successfull", Toast.LENGTH_LONG).show();

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frag, new Projects());
                    transaction.commit();
                }
            }
        });
        return view;
    }


}
