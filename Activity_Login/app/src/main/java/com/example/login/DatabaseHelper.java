
package com.example.login;

/**
 * Created by Ibrahim on 31/03/2019.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, "Student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE students(Firstname TEXT, Username TEXT primary key,Email TEXT,Password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS students");
        onCreate(sqLiteDatabase);
    }

    public boolean insert(String Firstname, String Username ,String Email,String Password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();

        contentValue.put("Firstname", Firstname);
        contentValue.put("Username", Username);
        contentValue.put("Email", Email);
        contentValue.put("Password", Password);


        long rest = db.insert("students", null, contentValue);

        db.close();
      if(rest==-1)return  false;
      else return  true;
    }

    public void updatePassword(String Email,String Password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("Password",Password);
        db.update("Students",values,"Email"+"=?",new String[] {Email});
        db.close();
    }

    public  boolean chkusn(String Username){

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.rawQuery("select * from students where Username=?",new String[] {Username});
      if(cursor.getCount()>0)return  false;
      else return  true;
    }

    public boolean checkUser(String Username, String Password) {

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from students where Username=? and Password=? ",new String [] {Username,Password});
        if(cursor.getCount()>0) return  true;
        else return false;

    }
}



