package com.example.resumemaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.Log;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;

public class CV extends AppCompatActivity {
    Button save10;
    private ScrollView llScroll;
    private LinearLayout education,work,PRO,SKILL,AWARD;
    private Bitmap bitmap;
    ImageView img;
    EditText et;
    TextView tv,pt,co,birth,gender,address,mobile,email,ed,detail,skill,certi,pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cv);

        tv = findViewById(R.id.tv);
        et = findViewById(R.id.et);
        pt = findViewById(R.id.pt);
        co = findViewById(R.id.co);
        skill = findViewById(R.id.skill);
        certi = findViewById(R.id.certi);
        pro = findViewById(R.id.pro);

        save10 = findViewById(R.id.save10);
        llScroll = findViewById(R.id.llscroll);
        work = findViewById(R.id.work);
        PRO = findViewById(R.id.PRO);
        AWARD = findViewById(R.id.AWARD);
        SKILL = findViewById(R.id.SKILL);
        education = findViewById(R.id.education);

        ed = findViewById(R.id.ed);
        detail = findViewById(R.id.detail);


        birth = findViewById(R.id.birth);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);
        mobile = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        img = findViewById(R.id.imgcv);

       final SharedPreferences sharedPreferences=getSharedPreferences("Mypref2", Context.MODE_PRIVATE);


        save10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedPreferences.getString("NAME",null)==null  && sharedPreferences.getString("CAREER",null)==null) {

                    Toast.makeText(CV.this, "Complete your CV information first", Toast.LENGTH_LONG).show();
                }
                else if(et.getText().toString().isEmpty()){
                    Toast.makeText(CV.this, "Enter your CV name to save as pdf", Toast.LENGTH_LONG).show();

                }
                else{

                    Log.d("size","MUNAZZA FATMA "+llScroll.getWidth() +"  "+llScroll.getWidth());
                    bitmap = loadBitmapFromView(llScroll, llScroll.getChildAt(0).getWidth(), llScroll.getChildAt(0).getHeight());
                   int permission= ContextCompat.checkSelfPermission(CV.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                   if(permission != PackageManager.PERMISSION_GRANTED){
                       ActivityCompat.requestPermissions(CV.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                   }
                   else {
                       Document document =  new Document(PageSize.A4);
                       File resume = new File("/sdcard/Resume Maker");
                       resume.mkdir();
                       if (resume.exists()) {

                           try {
                               PdfWriter.getInstance(document, new FileOutputStream(resume + "/" + et.getText().toString() + ".pdf"));
                               Log.d("perfect", "The Great munazza fatma pdf form");
                           } catch (DocumentException e) {
                               e.printStackTrace();
                               Log.d("size", "Document NOT FOUND EXCEPTIONNNNNNN");
                           } catch (FileNotFoundException e) {
                               Log.d("size", "FILE NOT FOUND EXCEPTIONNNNNNN");
                               e.printStackTrace();
                           }
                           document.open();

                           ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                           bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                           Image img = null;
                           try {
                               img = Image.getInstance(byteArrayOutputStream.toByteArray());
                               Log.d("size", "GREAT MUNAZZA FATMA  RIZVI");
                           } catch (BadElementException e) {
                               e.printStackTrace();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                           float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - 0) / img.getWidth()) * 100;
                           img.scalePercent(scaler);
                           img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
                           try {
                               document.add(img);
                               Log.d("size", " THE GREAT MUNAZZA FATMA  RIZVI");
                           } catch (DocumentException e) {
                               e.printStackTrace();
                           }
                           document.close();
                           Toast.makeText(CV.this, "Your CV save in Resume Maker folder as " + et.getText().toString() + ".pdf in file manager of your phone memory", Toast.LENGTH_LONG).show();


                           // sharedPreferences.edit().clear().commit();
                           Intent i = new Intent(CV.this, PdfPageOpen.class);
                           i.putExtra("name",et.getText().toString());
                           startActivity(i);
                       }
                       else
                       {
                           Toast.makeText(CV.this, "Doesn't found directory to save", Toast.LENGTH_LONG).show();
                       }
                   }
                }
            }
        });

       if(sharedPreferences.getString("IMAGE", "").isEmpty()){
           img.setVisibility(View.GONE);
           if(sharedPreferences.getAll()!=null && sharedPreferences.getString("NAME",null)!=null && sharedPreferences.getString("TITLE",null)!=null && sharedPreferences.getString("CAREER",null)!=null) {

               String Name = sharedPreferences.getString("NAME", "");
               String BIRTH = sharedPreferences.getString("BIRTH", "");
               String GENDER = sharedPreferences.getString("GENDER", "");
               String ADDRESS = sharedPreferences.getString("ADDRESS", "");
               String PHONE = sharedPreferences.getString("PHONE", "");
               String EMAIL = sharedPreferences.getString("EMAIL", "");

               String CAREER = sharedPreferences.getString("CAREER", "");
               String TITLE = sharedPreferences.getString("TITLE", "");

               tv.setText(Name);
               pt.setText(TITLE);
               co.setText(CAREER);


               birth.setText(BIRTH);
               gender.setText(GENDER);
               address.setText(ADDRESS);
               mobile.setText(PHONE);
               email.setText(EMAIL);

               UserDbHelper user = new UserDbHelper(getApplicationContext());
               SQLiteDatabase db = user.getReadableDatabase();
               Cursor c;
               c = user.viewData(db);
               if (c.getCount() > 0) {
                   StringBuffer sb = new StringBuffer();
                   c.moveToFirst();
                   do {
                       sb.append("***" + c.getString(0)+ "\n");
                       sb.append("       " + c.getString(1) + "\n");
                       sb.append("       " + c.getString(2) + "\n");
                       sb.append("       " + c.getString(3) + "\n");

                   } while (c.moveToNext());

                   ed.setText(sb);
               } else {
                   education.setVisibility(View.GONE);

               }
               Cursor c1;
               c1 = user.viewData3(db);
               if (c1.getCount() > 0) {
                   StringBuffer sb1 = new StringBuffer();
                   c1.moveToFirst();
                   do {

                       sb1.append("***" + c1.getString(0) + "\n");

                   } while (c1.moveToNext());

                   skill.setText(sb1);
               } else {
                   SKILL.setVisibility(View.GONE);

               }
               Cursor c2;
               c2 = user.viewData2(db);
               if (c2.getCount() > 0) {
                   StringBuffer sb2 = new StringBuffer();
                   c2.moveToFirst();
                   do {
                       sb2.append("***" + c2.getString(0)+ "\n");
                       sb2.append("       " + c2.getString(1) + "\n");
                       sb2.append("       " + c2.getString(2) + "\n");
                       sb2.append("       " + c2.getString(3) + "\n");

                   } while (c2.moveToNext());

                   detail.setText(sb2);
               }
               else {
                   work.setVisibility(View.GONE);

               }
               Cursor c3;
               c3 = user.viewData4(db);
               if (c3.getCount() > 0) {
                   StringBuffer sb3 = new StringBuffer();
                   c3.moveToFirst();
                   do {

                       sb3.append("***" + c3.getString(0) + "\n");

                   } while (c3.moveToNext());

                   certi.setText(sb3);
               } else {
                   AWARD.setVisibility(View.GONE);

               }
               Cursor c5;
               c5 = user.viewData5(db);
               if (c5.getCount() > 0) {
                   StringBuffer sb5 = new StringBuffer();
                   c5.moveToFirst();
                   do {

                       sb5.append("***" + c5.getString(0) + "\n");

                   } while (c5.moveToNext());

                   pro.setText(sb5);
               } else {
                   PRO.setVisibility(View.GONE);

               }

           }
           else if(sharedPreferences.getAll()!=null && sharedPreferences.getString("NAME",null)!=null && sharedPreferences.getString("TITLE",null)==null && sharedPreferences.getString("CAREER",null)!=null) {

               String Name = sharedPreferences.getString("NAME", "");
               String BIRTH = sharedPreferences.getString("BIRTH", "");
               String GENDER = sharedPreferences.getString("GENDER", "");
               String ADDRESS = sharedPreferences.getString("ADDRESS", "");
               String PHONE = sharedPreferences.getString("PHONE", "");
               String EMAIL = sharedPreferences.getString("EMAIL", "");

               pt.setText("Career Objective");

               String CAREER = sharedPreferences.getString("CAREER", "");
               tv.setText(Name);
               co.setText(CAREER);

               birth.setText(BIRTH);
               gender.setText(GENDER);
               address.setText(ADDRESS);
               mobile.setText(PHONE);
               email.setText(EMAIL);

               UserDbHelper user = new UserDbHelper(getApplicationContext());
               SQLiteDatabase db = user.getReadableDatabase();
               Cursor c;
               c = user.viewData(db);
               if (c.getCount() > 0) {
                   StringBuffer sb = new StringBuffer();
                   c.moveToFirst();
                   do {
                       sb.append("***" + c.getString(0)+ "\n");
                       sb.append("       " + c.getString(1) + "\n");
                       sb.append("       " + c.getString(2) + "\n");
                       sb.append("       " + c.getString(3) + "\n");

                   } while (c.moveToNext());

                   ed.setText(sb);
               } else {
                   education.setVisibility(View.GONE);

               }
               Cursor c1;
               c1 = user.viewData3(db);
               if (c1.getCount() > 0) {
                   StringBuffer sb1 = new StringBuffer();
                   c1.moveToFirst();
                   do {

                       sb1.append("***" + c1.getString(0) + "\n");

                   } while (c1.moveToNext());

                   skill.setText(sb1);
               } else {
                   SKILL.setVisibility(View.GONE);

               }
               Cursor c2;
               c2 = user.viewData2(db);
               if (c2.getCount() > 0) {
                   StringBuffer sb2 = new StringBuffer();
                   c2.moveToFirst();
                   do {
                       sb2.append("***" + c2.getString(0)+ "\n");
                       sb2.append("       " + c2.getString(1) + "\n");
                       sb2.append("       " + c2.getString(2) + "\n");
                       sb2.append("       " + c2.getString(3) + "\n");

                   } while (c2.moveToNext());

                   detail.setText(sb2);
               }
               else {
                   work.setVisibility(View.GONE);

               }
               Cursor c3;
               c3 = user.viewData4(db);
               if (c3.getCount() > 0) {
                   StringBuffer sb3 = new StringBuffer();
                   c3.moveToFirst();
                   do {

                       sb3.append("***" + c3.getString(0) + "\n");

                   } while (c3.moveToNext());

                   certi.setText(sb3);
               } else {
                   AWARD.setVisibility(View.GONE);

               }
               Cursor c5;
               c5 = user.viewData5(db);
               if (c5.getCount() > 0) {
                   StringBuffer sb5 = new StringBuffer();
                   c5.moveToFirst();
                   do {

                       sb5.append("***" + c5.getString(0) + "\n");

                   } while (c5.moveToNext());

                   pro.setText(sb5);
               } else {
                   PRO.setVisibility(View.GONE);

               }

           }
           else{
               Toast.makeText(CV.this, "Complete your CV information first", Toast.LENGTH_LONG).show();
           }
       }
       else{
           if(sharedPreferences.getAll()!=null && sharedPreferences.getString("NAME",null)!=null && sharedPreferences.getString("TITLE",null)!=null && sharedPreferences.getString("CAREER",null)!=null) {

               String Name = sharedPreferences.getString("NAME", "");
               String BIRTH = sharedPreferences.getString("BIRTH", "");
               String GENDER = sharedPreferences.getString("GENDER", "");
               String ADDRESS = sharedPreferences.getString("ADDRESS", "");
               String PHONE = sharedPreferences.getString("PHONE", "");
               String EMAIL = sharedPreferences.getString("EMAIL", "");

               String CAREER = sharedPreferences.getString("CAREER", "");
               String TITLE = sharedPreferences.getString("TITLE", "");

               String IMAGE = sharedPreferences.getString("IMAGE", "");

               byte[] byteArray = Base64.decode(IMAGE, Base64.DEFAULT);
               Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

               tv.setText(Name);
               pt.setText(TITLE);
               co.setText(CAREER);


               birth.setText(BIRTH);
               gender.setText(GENDER);
               address.setText(ADDRESS);
               mobile.setText(PHONE);
               email.setText(EMAIL);
               img.setImageBitmap(bitmap);

               UserDbHelper user = new UserDbHelper(getApplicationContext());
               SQLiteDatabase db = user.getReadableDatabase();
               Cursor c;
               c = user.viewData(db);
               if (c.getCount() > 0) {
                   StringBuffer sb = new StringBuffer();
                   c.moveToFirst();
                   do {
                       sb.append("***" + c.getString(0)+ "\n");
                       sb.append("       " + c.getString(1) + "\n");
                       sb.append("       " + c.getString(2) + "\n");
                       sb.append("       " + c.getString(3) + "\n");

                   } while (c.moveToNext());

                   ed.setText(sb);
               } else {
                   education.setVisibility(View.GONE);

               }
               Cursor c1;
               c1 = user.viewData3(db);
               if (c1.getCount() > 0) {
                   StringBuffer sb1 = new StringBuffer();
                   c1.moveToFirst();
                   do {

                       sb1.append("***" + c1.getString(0) + "\n");

                   } while (c1.moveToNext());

                   skill.setText(sb1);
               } else {
                   SKILL.setVisibility(View.GONE);

               }
               Cursor c2;
               c2 = user.viewData2(db);
               if (c2.getCount() > 0) {
                   StringBuffer sb2 = new StringBuffer();
                   c2.moveToFirst();
                   do {
                       sb2.append("***" + c2.getString(0)+ "\n");
                       sb2.append("       " + c2.getString(1) + "\n");
                       sb2.append("       " + c2.getString(2) + "\n");
                       sb2.append("       " + c2.getString(3) + "\n");

                   } while (c2.moveToNext());

                   detail.setText(sb2);
               }
               else {
                   work.setVisibility(View.GONE);

               }
               Cursor c3;
               c3 = user.viewData4(db);
               if (c3.getCount() > 0) {
                   StringBuffer sb3 = new StringBuffer();
                   c3.moveToFirst();
                   do {

                       sb3.append("***" + c3.getString(0) + "\n");

                   } while (c3.moveToNext());

                   certi.setText(sb3);
               } else {
                   AWARD.setVisibility(View.GONE);

               }
               Cursor c5;
               c5 = user.viewData5(db);
               if (c5.getCount() > 0) {
                   StringBuffer sb5 = new StringBuffer();
                   c5.moveToFirst();
                   do {

                       sb5.append("***" + c5.getString(0) + "\n");

                   } while (c5.moveToNext());

                   pro.setText(sb5);
               } else {
                   PRO.setVisibility(View.GONE);

               }

           }
           else if(sharedPreferences.getAll()!=null && sharedPreferences.getString("NAME",null)!=null && sharedPreferences.getString("TITLE",null)==null && sharedPreferences.getString("CAREER",null)!=null) {

               String Name = sharedPreferences.getString("NAME", "");
               String BIRTH = sharedPreferences.getString("BIRTH", "");
               String NATIONALITY = sharedPreferences.getString("NATIONALITY", "");
               String GENDER = sharedPreferences.getString("GENDER", "");
               String ADDRESS = sharedPreferences.getString("ADDRESS", "");
               String PHONE = sharedPreferences.getString("PHONE", "");
               String EMAIL = sharedPreferences.getString("EMAIL", "");

               String CAREER = sharedPreferences.getString("CAREER", "");

               pt.setText("Career Objective");

               String IMAGE = sharedPreferences.getString("IMAGE", "");

               byte[] byteArray = Base64.decode(IMAGE, Base64.DEFAULT);
               Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

               tv.setText(Name);
               co.setText(CAREER);


               birth.setText(BIRTH);
               gender.setText(GENDER);
               address.setText(ADDRESS);
               mobile.setText(PHONE);
               email.setText(EMAIL);
               img.setImageBitmap(bitmap);

               UserDbHelper user = new UserDbHelper(getApplicationContext());
               SQLiteDatabase db = user.getReadableDatabase();
               Cursor c;
               c = user.viewData(db);
               if (c.getCount() > 0) {
                   StringBuffer sb = new StringBuffer();
                   c.moveToFirst();
                   do {
                       sb.append("***" + c.getString(0)+ "\n");
                       sb.append("       " + c.getString(1) + "\n");
                       sb.append("       " + c.getString(2) + "\n");
                       sb.append("       " + c.getString(3) + "\n");

                   } while (c.moveToNext());

                   ed.setText(sb);
               } else {
                   education.setVisibility(View.GONE);

               }
               Cursor c1;
               c1 = user.viewData3(db);
               if (c1.getCount() > 0) {
                   StringBuffer sb1 = new StringBuffer();
                   c1.moveToFirst();
                   do {

                       sb1.append("***" + c1.getString(0) + "\n");

                   } while (c1.moveToNext());

                   skill.setText(sb1);
               } else {
                   SKILL.setVisibility(View.GONE);

               }
               Cursor c2;
               c2 = user.viewData2(db);
               if (c2.getCount() > 0) {
                   StringBuffer sb2 = new StringBuffer();
                   c2.moveToFirst();
                   do {
                       sb2.append("***" + c2.getString(0)+ "\n");
                       sb2.append("       " + c2.getString(1) + "\n");
                       sb2.append("       " + c2.getString(2) + "\n");
                       sb2.append("       " + c2.getString(3) + "\n");

                   } while (c2.moveToNext());

                   detail.setText(sb2);
               }
               else {
                   work.setVisibility(View.GONE);

               }
               Cursor c3;
               c3 = user.viewData4(db);
               if (c3.getCount() > 0) {
                   StringBuffer sb3 = new StringBuffer();
                   c3.moveToFirst();
                   do {

                       sb3.append("***" + c3.getString(0) + "\n");

                   } while (c3.moveToNext());

                   certi.setText(sb3);
               } else {
                   AWARD.setVisibility(View.GONE);

               }
               Cursor c5;
               c5 = user.viewData5(db);
               if (c5.getCount() > 0) {
                   StringBuffer sb5 = new StringBuffer();
                   c5.moveToFirst();
                   do {

                       sb5.append("***" + c5.getString(0) + "\n");

                   } while (c5.moveToNext());

                   pro.setText(sb5);
               } else {
                   PRO.setVisibility(View.GONE);

               }

           }
           else{
               Toast.makeText(CV.this, "Complete your CV information first", Toast.LENGTH_LONG).show();
           }
       }

    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Drawable bgDrawable=v.getBackground();
        if(bgDrawable!=null){
            bgDrawable.draw(c);
        }
        else{
         c.drawColor(Color.WHITE);
        }
        v.draw(c);
        return b;
    }
}
