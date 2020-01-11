package com.example.resumemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class resumeFragment extends AppCompatActivity implements View.OnClickListener {
    Button tv1,tv2,tv3,tv4,tv5,tv6,tv7,CV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_fragment);





        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        tv3=findViewById(R.id.tv3);
        tv4=findViewById(R.id.tv4);
        tv5=findViewById(R.id.tv5);
        tv6=findViewById(R.id.tv6);
        tv7=findViewById(R.id.tv7);
        CV=findViewById(R.id.CV);

        CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(resumeFragment.this,CV.class);
                startActivity(i);
            }
        });

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag, new Personal());
        transaction.commit();

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Fragment fr;
        switch (id) {
            case R.id.tv1:
                fr = new Personal();
                break;
            case R.id.tv2:
                fr = new Education();
                break;
            case R.id.tv3:
                fr = new Skills();
                break;
            case R.id.tv4:
                fr = new Experience();
                break;
            case R.id.tv5:
                fr = new Objective();
                break;
            case R.id.tv6:
                fr = new Projects();
                break;
            case R.id.tv7:
                fr = new Section();
                break;
            default:
                fr = new Personal();
                break;


        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frag, fr);
        transaction.commit();
    }
}
