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


public class Experience extends Fragment {

    Button save2;
    EditText employer,jobtitle,city,startdate,job;
    public Experience() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      final View view= inflater.inflate(R.layout.fragment_experience, container, false);
      employer=view.findViewById(R.id.employer);
      jobtitle=view.findViewById(R.id.jobtitle);
      city=view.findViewById(R.id.city);
      startdate=view.findViewById(R.id.startdate);
      job=view.findViewById(R.id.job);
      save2=view.findViewById(R.id.save2);


        save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Submission successfull",Toast.LENGTH_LONG).show();
                String Employer=employer.getText().toString();
                String JobTitle=jobtitle.getText().toString();
                String City=city.getText().toString();
                String StartDate=startdate.getText().toString();
                String Job=job.getText().toString();
                if(Employer.isEmpty() || JobTitle.isEmpty() || City.isEmpty() || StartDate.isEmpty() || Job.isEmpty()){
                    Toast.makeText(getActivity(),"No field sholud be empty",Toast.LENGTH_LONG).show();
                }
                else{

                    UserDbHelper user=new UserDbHelper(getActivity());
                    SQLiteDatabase db=user.getWritableDatabase();
                    user.insertData2(JobTitle,Employer,StartDate,Job,db);
                    Toast.makeText(getActivity(),"Submission Successfull",Toast.LENGTH_LONG).show();
                }



                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.frag, new Experience());
                transaction.commit();


            }
        });


        return view;
    }


}
