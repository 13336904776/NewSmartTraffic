package com.example.mrzhang.newsmarttraffic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class Mydb extends SQLiteOpenHelper{

    public Mydb(Context context) {
        super(context, "smartdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists recharge(_id integer primary key autoincrement,operator varchar(20),carId integer,rechargeAmount integer,date integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
