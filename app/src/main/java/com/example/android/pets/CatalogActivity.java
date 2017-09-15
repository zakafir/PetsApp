/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.pets;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.pets.data.PetContract;
import com.example.android.pets.data.PetDbHelper;

import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_BREED;
import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_GENDER;
import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_NAME;
import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_WEIGHT;
import static com.example.android.pets.data.PetContract.PetEntry.GENDER_FEMALE;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    public static final String LOG_TAG = CatalogActivity.class.getSimpleName();
    TextView displayView;
    private PetDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        displayView = (TextView) findViewById(R.id.text_view_pet);
        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new PetDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertDummyPetData();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertDummyPetData() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_NAME, "TOTO");
        values.put(COLUMN_PET_BREED, "terrier");
        values.put(COLUMN_PET_GENDER, GENDER_FEMALE);
        values.put(COLUMN_PET_WEIGHT, 20);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId = db.insert(PetContract.PetEntry.TABLE_NAME, null, values);
        Log.d(LOG_TAG, "New row ID " + newRowId);
    }

    private void displayDatabaseInfo() {
        // Define a projection that specifies which columns from the database
        String[] projection = {
                PetContract.PetEntry._ID,
                PetContract.PetEntry.COLUMN_PET_NAME,
                PetContract.PetEntry.COLUMN_PET_BREED,
                PetContract.PetEntry.COLUMN_PET_GENDER,
                PetContract.PetEntry.COLUMN_PET_WEIGHT
        };

        String sortOrderBy =
                PetContract.PetEntry._ID + " ASC";

        //we don't need to interact with the database directly from our Activity
        //So, we use a ContentResolver instead
        /*Cursor cursor = db.query(
                PetContract.PetEntry.TABLE_NAME,
                projection,                         //make it "null" to select all columns
                null,
                null,
                null,
                null,
                sortOrderBy
        );*/

        Cursor cursor = getContentResolver().query(
                PetContract.PetEntry.CONTENT_URI,
                projection,                         //make it "null" to select all columns
                null,
                null,
                sortOrderBy);
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).
            displayView.setText("The pets table contains : " + cursor.getCount() + " pets. \n");
            while (cursor.moveToNext()) {
                displayView.append("\n _ID: " + cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry._ID)) +
                        "\n -Name: " + cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_NAME)) +
                        "\n -Breed: " + cursor.getString(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_BREED)) +
                        "\n -Gender: " + cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_GENDER)) +
                        "\n -Weight: " + cursor.getInt(cursor.getColumnIndex(PetContract.PetEntry.COLUMN_PET_WEIGHT)) +
                        "\n ---------------------------------");
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }
}
