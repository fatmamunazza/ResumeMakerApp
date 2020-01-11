package com.example.resumemaker;

import android.content.Context;
import android.content.SharedPreferences;
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


public class Objective extends Fragment {
    EditText et1,et2;
    Button save3;
    public Objective() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.fragment_objective, container, false);

        et1 = rootview.findViewById(R.id.et1);
        et2 = rootview.findViewById(R.id.et2);

        save3 = rootview.findViewById(R.id.save3);
        save3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et2.getText().toString().isEmpty()){
                    et2.setError("Career objective cannot be empty");
                }
                else if(et1.getText().toString().isEmpty()){
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Mypref2", 0);
                    final SharedPreferences.Editor editor=sharedPreferences.edit();

                    editor.putString("CAREER",et2.getText().toString());
                    editor.commit();

                    Toast.makeText(getActivity(),"Submission successfull",Toast.LENGTH_LONG).show();
                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frag, new Projects());
                    transaction.commit();

                }
                else{
                    SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Mypref2", 0);
                    final SharedPreferences.Editor editor=sharedPreferences.edit();

                    editor.putString("CAREER",et2.getText().toString());
                    editor.putString("TITLE",et1.getText().toString());
                    editor.commit();

                    Toast.makeText(getActivity(),"Submission successfull",Toast.LENGTH_LONG).show();

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frag, new Projects());
                    transaction.commit();

                }
            }
        });

        return rootview;
    }


}
