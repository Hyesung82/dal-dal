package com.example.dalyeodalyeok;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper {
    private static final String DATABASE_NAME = "InnerDatabase(SQLite).db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DataBases.CreateDB._CREATE0);
            db.execSQL(DataBases.CreateDB._CREATE1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DataBases.CreateDB._TABLENAME0);
            onCreate(db);
        }
    }

    public DbOpenHelper(Context context) {
        this.mCtx = context;
    }

    public DbOpenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

    // Insert DB
    public long insertColumn(String subject, String report, int checked) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.SUBJECT, subject);
        values.put(DataBases.CreateDB.REPORT, report);
        values.put(DataBases.CreateDB.CHECKED, checked);
        return mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
    }

    // Update DB
    public boolean updateColumn(long id, String subject, String report, int checked) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.SUBJECT, subject);
        values.put(DataBases.CreateDB.REPORT, report);
        values.put(DataBases.CreateDB.CHECKED, checked);
        return mDB.update(DataBases.CreateDB._TABLENAME0, values, "_id=" + id, null) > 0;
    }

    // Delete All
    public void deleteAllColumns() {
        mDB.delete(DataBases.CreateDB._TABLENAME0, null, null);
    }

    // Delete DB
    public boolean deleteColumn(long id) {
        return mDB.delete(DataBases.CreateDB._TABLENAME0, "_id=" + id, null) > 0;
    }

    // Select DB
    public Cursor selectColumns() {
        return mDB.query(DataBases.CreateDB._TABLENAME0, null, null, null, null, null, null);
    }

    // sort by column
    public Cursor sortColumn(String sort) {
        Cursor c = mDB.rawQuery("SELECT * FROM usertable ORDER BY " + sort + ";", null);
        return c;
    }

    public boolean search(String subject, String report) {
        String[] dataStr = {subject, report};
        Cursor c = mDB.rawQuery("SELECT * FROM usertable WHERE subject=? AND report=?;", dataStr);

        System.out.println("c.getCount() 출력 : " + c.getCount());
        if (c.getCount() == 0) return false;
        else return true;
    }

    public Cursor findUnchecked() {
        String[] condition = {"0"};
        Cursor c = mDB.rawQuery("SELECT * FROM usertable WHERE checked=?;", condition);
        return c;
    }

    public Cursor setChecked(String report, int checked) {
        String[] condition = {Integer.toString(checked), report};
        Cursor c = mDB.rawQuery("UPDATE usertable SET checked=? WHERE report=?;", condition);
        return c;
    }

    public long insertSchedule(String date, String time, String schedule) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.SCHEDULE, schedule);
        values.put(DataBases.CreateDB.DATE, date);
        values.put(DataBases.CreateDB.TIME, time);
        return mDB.insert(DataBases.CreateDB._TABLENAME1, null, values);
    }

    public Cursor sortSchedule(String sort) {
        Cursor c = mDB.rawQuery("SELECT * FROM scheduletable ORDER BY " + sort + ";", null);
        return c;
    }

    public Cursor findSchedule(String strDate) {
        String[] condition = {strDate};
        Cursor c = mDB.rawQuery("SELECT * FROM scheduletable WHERE date=?;", condition);
        return c;
    }
}
