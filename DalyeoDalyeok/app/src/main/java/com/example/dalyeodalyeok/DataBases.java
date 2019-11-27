package com.example.dalyeodalyeok;

import android.provider.BaseColumns;

public class DataBases {
    public static final class CreateDB implements BaseColumns {
//        public static final String USERID = "userid";
//        public static final String PASSWORD = "password";
        public static final String SUBJECT = "subject";
        public static final String REPORT = "report";
        public static final String _TABLENAME0 = "usertable";
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + _ID + " integer primary key autoincrement, "
                + SUBJECT + " text not null , "
                + REPORT + " text not null );";
    }
}
