package com.example.resumemaker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserDbHelper  extends SQLiteOpenHelper {
    private static final String DB_NAME="resume";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="education";
    private static final String FIRST_COL="degree";
    private static final String SECOND_COL="board";
    private static final String THIRD_COL="year";
    private static final String FOURTH_COL="cgpa";
    private static final String FIFTH_COL="skill";
    private static final String SIXTH_COL="section";
    private static final String SEVENTH_COL="project";

    private static final String CREATE_QUERY="CREATE TABLE " + TABLE_NAME + " (" + FIRST_COL + " text, " + SECOND_COL + " text, " + THIRD_COL + " text, " + FOURTH_COL  + " text)";

    private static final String TABLE_NAME2="experience";
    private static final String TABLE_NAME3="skills";
    private static final String TABLE_NAME4="objective";
    private static final String TABLE_NAME5="projects";
    private static final String FIRST_COL1="Title";
    private static final String SECOND_COL1="Employer";
    private static final String THIRD_COL1="Time";
    private static final String FOURTH_COL1="Description";

    private static final String CREATE_QUERY2="CREATE TABLE " + TABLE_NAME2 + " (" + FIRST_COL1 + " text, " + SECOND_COL1 + " text, " + THIRD_COL1 + " text, " + FOURTH_COL1 + " text)";
    private static final String CREATE_QUERY3="CREATE TABLE " + TABLE_NAME3 + " (" + FIFTH_COL +  " text)";
    private static final String CREATE_QUERY4="CREATE TABLE " + TABLE_NAME4 + " (" + SIXTH_COL +  " text)";
    private static final String CREATE_QUERY5="CREATE TABLE " + TABLE_NAME5 + " (" + SEVENTH_COL +  " text)";




    public UserDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        Log.i("DB Message","Database Created");
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_QUERY2);
        sqLiteDatabase.execSQL(CREATE_QUERY);
        sqLiteDatabase.execSQL(CREATE_QUERY3);
        sqLiteDatabase.execSQL(CREATE_QUERY4);
        sqLiteDatabase.execSQL(CREATE_QUERY5);
        Log.i("DB Messgae","Table Created");


    }
    public void insertData(String degree,String board,String year,String cgpa,SQLiteDatabase db){
        ContentValues content=new ContentValues();
        content.put(FIRST_COL,degree);
        content.put(SECOND_COL,board);
        content.put(THIRD_COL,year);
        content.put(FOURTH_COL,cgpa);

        db.insert(TABLE_NAME,null,content);
        Log.i("DB Message","1 data inserted");
    }
    public void skillData(String skill,SQLiteDatabase db){
        ContentValues content=new ContentValues();
        content.put(FIFTH_COL,skill);

        db.insert(TABLE_NAME3,null,content);
        Log.i("DB Message","1 data inserted");
    }
    public void projectData(String project,SQLiteDatabase db){
        ContentValues content=new ContentValues();
        content.put(SEVENTH_COL,project);

        db.insert(TABLE_NAME5,null,content);
        Log.i("DB Message","1 data inserted");
    }
    public void sectionData(String section,SQLiteDatabase db){
        ContentValues content=new ContentValues();
        content.put(SIXTH_COL,section);

        db.insert(TABLE_NAME4,null,content);
        Log.i("DB Message","1 data inserted");
    }
    public void insertData2(String title,String employer,String time,String description,SQLiteDatabase db){
        ContentValues content=new ContentValues();
        content.put(FIRST_COL1,title);
        content.put(SECOND_COL1,employer);
        content.put(THIRD_COL1,time);
        content.put(FOURTH_COL1,description);

        db.insert(TABLE_NAME2,null,content);
        Log.i("DB Message","1 data inserted");
    }
    public Cursor viewData(SQLiteDatabase db){
        Cursor cursor;

        cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return cursor;
    }
    public Cursor viewData3(SQLiteDatabase db){
        Cursor cursor;

        cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME3,null);
        return cursor;
    }
    public Cursor viewData4(SQLiteDatabase db){
        Cursor cursor;

        cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME4,null);
        return cursor;
    }
    public Cursor viewData5(SQLiteDatabase db){
        Cursor cursor;

        cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME5,null);
        return cursor;
    }
    public Cursor viewData2(SQLiteDatabase db){
        Cursor cursor;

        cursor=db.rawQuery("SELECT * FROM " + TABLE_NAME2,null);
        return cursor;
    }
    public void deleteData(SQLiteDatabase db){
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
    public void deleteData2(SQLiteDatabase db){
        db.delete(TABLE_NAME2,null,null);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

