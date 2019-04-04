
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

    //public static final String DATABASE_NAME = "student.db";
   // public static final String TABLE_NAME = "students";
    //public static final String COL_1 = "Firstname";
    //public static final String COL_2 = "Username";
    //public static final String COL_3 = "Email";
    //public static final String COL_4 = "Password";

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




/*public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context){
        super(context, Database.DATABASE_NAME, null, Database.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE student;");
        db.execSQL(Database.Student.CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Method to insert values to the tables
    public long insert(String table, ContentValues values){
        return getWritableDatabase().insert(table, null, values);
    }

    //Method to update values in the tables
    public int update(String table, ContentValues values, String whereClause, String[] whereArgs){
        return getWritableDatabase().update(table, values, whereClause, whereArgs);
    }

    //Method to delete records from the tables
    public int delete(String table, String whereClause, String[] whereArgs){
        return getWritableDatabase().delete(table, whereClause, whereArgs);
    }


    //Method to read records from the tables
    public Cursor read(String table, String columns[], String whereClause, String[] whereArgs,
                       String groupByClause, String havingClause, String orderByClause, String limit){
        return getReadableDatabase().query(table, columns, whereClause, whereArgs, groupByClause,
                havingClause, orderByClause, limit);
    }*/


