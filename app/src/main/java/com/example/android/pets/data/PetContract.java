package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * Created by zakaria_afir on 10/09/2017.
 */

public final class PetContract {

    public class PetEntry implements BaseColumns {

        private PetEntry(){}

        //table
        public static final String TABLE_NAME = "pets";

        //Columns
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_WEIGHT = "weight";

        //gender cases
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_FEMALE = 1;
        public static final int GENDER_MALE = 2;
    }
}
