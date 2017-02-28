package com.example.babis.databaseswipetodelete;

import android.provider.BaseColumns;

/**
 * Created by Babis on 2/27/2017.
 */

//THIS IS JUST A HELPER CLASS TO DEFINE A SQLDB SCHEME YOU CAN DO IT WITHOUT IT BUT ITS ERROR PROOF THAT WAY

public class MyDatabaseContract {


    public static final class DatabaseScheme implements BaseColumns{
        public static final String TABLE_NAME = "database_table_name";
        public static final String DATABASE_ID = "ID";
        public static final String NAME ="NAME";
        public static final String SURNAME = "SURNAME";
        public static final String RATING = "RATING";
    }


}
