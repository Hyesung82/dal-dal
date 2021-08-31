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
    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {
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

    public void open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

    // Insert DB
    public void insertColumn(String subject, String report, int checked) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.SUBJECT, subject);
        values.put(DataBases.CreateDB.REPORT, report);
        values.put(DataBases.CreateDB.CHECKED, checked);
        mDB.insert(DataBases.CreateDB._TABLENAME0, null, values);
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
        return mDB.rawQuery("SELECT * FROM usertable ORDER BY " + sort + ";", null);
    }

    public boolean search(String subject, String report) {
        String[] dataStr = {subject, report};
        Cursor c = mDB.rawQuery("SELECT * FROM usertable WHERE subject=? AND report=?;", dataStr);

        System.out.println("c.getCount() 출력 : " + c.getCount());
        return c.getCount() != 0;
    }

    public Cursor findUnchecked() {
        String[] condition = {"0"};
        return mDB.rawQuery("SELECT * FROM usertable WHERE checked=?;", condition);
    }

    public Cursor setChecked(String report, int checked) {
        String[] condition = {Integer.toString(checked), report};
        return mDB.rawQuery("UPDATE usertable SET checked=? WHERE report=?;", condition);
    }

    public void insertSchedule(String date, String time, String schedule) {
        ContentValues values = new ContentValues();
        values.put(DataBases.CreateDB.SCHEDULE, schedule);
        values.put(DataBases.CreateDB.DATE, date);
        values.put(DataBases.CreateDB.TIME, time);
        mDB.insert(DataBases.CreateDB._TABLENAME1, null, values);
    }

    public Cursor sortSchedule(String sort) {
        return mDB.rawQuery("SELECT * FROM scheduletable ORDER BY " + sort + ";", null);
    }

    public Cursor findSchedule(String strDate) {
        String[] condition = {strDate};
        return mDB.rawQuery("SELECT * FROM scheduletable WHERE date=?;", condition);
    }

    public Cursor updateTodo(String todo, String checked) {
        String[] condition = {checked, todo};
        return mDB.rawQuery("UPDATE usertable SET checked=? WHERE report=?;", condition);
    }

    public Cursor getCheckedStat(String todo) {
        String[] condition = {todo};
        return mDB.rawQuery("SELECT checked FROM usertable WHERE report=?;", condition);
    }
}
