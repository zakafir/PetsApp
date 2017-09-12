package com.example.android.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zakaria_afir on 12/09/2017.
 */

public class PetDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = PetDbHelper.class.getSimpleName();

    //we need to explicit the .db extension
    public static final String DATABASE_NAME = "shelter.db";
    public static final int DATABASE_VERSION = 1;

    /**
     * CREATE TABLE pets(_id INTEGER PRIMARY KEY AUTOINCREMENT,
     * name TEXT NOT NULL,
     * breed TEXT,
     * gender INTEGER NOT NULL,
     * weight INTEGER NOT NULL DEFAULT 0);
     */


    public PetDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL statement
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + PetContract.PetEntry.TABLE_NAME + "(" +
                PetContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PetContract.PetEntry.COLUMN_PET_NAME + " TEXT NOT NULL," +
                PetContract.PetEntry.COLUMN_PET_BREED + " TEXT," +
                PetContract.PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL," +
                PetContract.PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_PETS_TABLE);
        Log.d(LOG_TAG, "query: " + SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
