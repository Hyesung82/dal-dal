package com.example.dalyeodalyeok;

import android.provider.BaseColumns;

import java.net.PortUnreachableException;

public class DataBases {
    public static final class CreateDB implements BaseColumns {
        public static final String SUBJECT = "subject";
        public static final String REPORT = "report";
        public static final String CHECKED = "checked";
        public static final String _TABLENAME0 = "usertable";
        public static final String _CREATE0 = "create table if not exists " + _TABLENAME0 + "("
                + _ID + " integer primary key autoincrement, "
                + SUBJECT + " text not null, "
                + REPORT + " text not null, "
                + CHECKED + " text not null );";

        public static final String SCHEDULE = "schedule";
        public static final String DATE = "date";
        public static final String TIME = "time";
        public static final String _TABLENAME1 = "scheduletable";
        public static final String _CREATE1 = "create table if not exists " + _TABLENAME1 + "("
                + _ID + " integer primary key autoincrement, "
                + SCHEDULE + " text not null, "
                + DATE + " text not null, "
                + TIME + " text not null );";
    }
}
