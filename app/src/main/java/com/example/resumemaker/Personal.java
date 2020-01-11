package com.example.resumemaker;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class Personal extends Fragment {
    ImageView img;
    Button btn,save;
    EditText name,birth,gender,address,phone,email;

    final Calendar mycalender=Calendar.getInstance();
    public Personal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_personal, container, false);
        img = rootview.findViewById(R.id.img);
        btn = rootview.findViewById(R.id.btn);
        save = rootview.findViewById(R.id.save);
        name = (EditText) rootview.findViewById(R.id.name);

        birth = rootview.findViewById(R.id.birth);
        gender = rootview.findViewById(R.id.gender);
        address = rootview.findViewById(R.id.address);
        phone = rootview.findViewById(R.id.phone);
        email = rootview.findViewById(R.id.email);


        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("Mypref2", 0);
        final SharedPreferences.Editor editor=sharedPreferences.edit();

        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItem=getView().findViewById(R.id.gender);
                PopupMenu popupMenu=new PopupMenu(getActivity(),menuItem);
                popupMenu.getMenuInflater().inflate(R.menu.mymenu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        gender.setText(" " + item.getTitle() + " ");
                       return true;
                    }

                });
                popupMenu.show();

            }
        });





        final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mycalender.set(Calendar.YEAR,i);
                mycalender.set(Calendar.MONTH,i1);
                mycalender.set(Calendar.DAY_OF_MONTH,i2);
                birth.setText("" + i2 + "/" + i1 + "/" + i + "");
            }
        };

        birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(),date,mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Name = name.getText().toString();
                String Birth = birth.getText().toString();
                String Gender = gender.getText().toString();
                String Address = address.getText().toString();
                String Phone = phone.getText().toString();
                String Email = email.getText().toString();

            if(img.getDrawable().getConstantState() ==getResources().getDrawable(R.drawable.ic_launcher_background).getConstantState()){
                if (Name.isEmpty() ||  Birth.isEmpty() || Gender.isEmpty() || Address.isEmpty() || Phone.isEmpty()) {
                    Toast.makeText(getActivity(), "No field sholud be empty", Toast.LENGTH_LONG).show();

                } else if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    email.setError("Enter Valid Email Address");

                }
                else {

                    editor.putString("NAME", Name);
                    editor.putString("BIRTH", Birth);
                    editor.putString("GENDER", Gender);
                    editor.putString("ADDRESS", Address);
                    editor.putString("PHONE", Phone);
                    editor.putString("EMAIL", Email);
                    editor.commit();

                    Toast.makeText(getActivity(), "Submission successfull", Toast.LENGTH_LONG).show();

                    FragmentManager manager = getActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.frag, new Education());
                    transaction.commit();
                }
                Toast.makeText(getActivity(), "IMAGE SHARED NOT", Toast.LENGTH_LONG).show();
            }
            else{
                    Bitmap image = ((BitmapDrawable) img.getDrawable()).getBitmap();
                    if (Name.isEmpty() ||  Birth.isEmpty() ||  Gender.isEmpty() || Address.isEmpty() || Phone.isEmpty()) {
                        Toast.makeText(getActivity(), "No field sholud be empty", Toast.LENGTH_LONG).show();

                    } else if (Email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                        email.setError("Enter Valid Email Address");

                    } else {

                        editor.putString("NAME", Name);
                        editor.putString("BIRTH", Birth);
                        editor.putString("GENDER", Gender);
                        editor.putString("ADDRESS", Address);
                        editor.putString("PHONE", Phone);
                        editor.putString("EMAIL", Email);
                        editor.putString("IMAGE", encode(image));

                        editor.commit();

                        Toast.makeText(getActivity(), "Submission successfull", Toast.LENGTH_LONG).show();

                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.frag, new Education());
                        transaction.commit();


                    }
                }



            }

        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_DENIED){

                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1001);
                    }
                    else{
                        pickImageFromGallery();
                    }
                }
                else{
                    pickImageFromGallery();
                }
            }


        });

        // Inflate the layout for this fragment
        return rootview;
    }

    public  static  String encode(Bitmap Img){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Img.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] b=byteArrayOutputStream.toByteArray();
        String Encode= Base64.encodeToString(b,Base64.DEFAULT);
        return Encode;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1001:{
                if(grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(getActivity(),"Permission denied",Toast.LENGTH_LONG).show();
                }
            }
            break;
        }
    }


    private void pickImageFromGallery() {
        Intent I=new Intent(Intent.ACTION_PICK);
        I.setType("image/*");
        startActivityForResult(I,1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1000 && data!=null){
            final Uri uri=data.getData();
            try {
                final InputStream str=getActivity().getContentResolver().openInputStream(uri);
               final Bitmap selectimage= BitmapFactory.decodeStream(str);
                img.setImageBitmap(selectimage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),"Something went wrong",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(getActivity(),"You haven't picked image",Toast.LENGTH_LONG).show();
        }
    }
}
