package myquizapp.ica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDb) {
        myDb.execSQL("create Table users(username Text, email Text primary key, password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDb, int oldVersion, int newVersion) {
        myDb.execSQL("drop Table if exists users");
    }

    public Boolean insertData(String username, String email, String password){
        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = myDb.insert("users", null, contentValues);

        if(result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email){
        SQLiteDatabase myDb = this.getWritableDatabase();
        Cursor cursor = myDb.rawQuery("select * from users where email = ?", new String[] {email});

        if(cursor.getCount() > 0) {
            return  true;
        } else {
            return false;
        }
    }


    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase myDb = this.getWritableDatabase();
        Cursor cursor = myDb.rawQuery("select * from users where email = ? and password = ?", new String[] {email, password});

        if(cursor.getCount() > 0) {
            return  true;
        } else {
            return false;
        }
    }

}
