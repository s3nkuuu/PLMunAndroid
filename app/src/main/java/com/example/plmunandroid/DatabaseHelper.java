package com.example.plmunandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "navplmun";
    private static final int DATABASE_VERSION = 2; // Increment version to trigger onUpgrade

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STUDENT_NUMBER = "student_number";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email"; // New column
    public static final String COLUMN_USERNAME = "username"; // Optional additional column

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_STUDENT_NUMBER + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_USERNAME + " TEXT);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Add new columns if upgrading from an older version
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_EMAIL + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_USERNAME + " TEXT");
        }
    }

    // Updated insert user method to include email and username
    public void insertUser(String studentNumber, String password, String email, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_NUMBER, studentNumber);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_USERNAME, username);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    // Get all users (optional)
    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
    }

    // Optional method to update user details
    public int updateUser(String studentNumber, String email, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_USERNAME, username);
        return db.update(TABLE_USERS,
                values,
                COLUMN_STUDENT_NUMBER + " = ?",
                new String[]{studentNumber});
    }
}
