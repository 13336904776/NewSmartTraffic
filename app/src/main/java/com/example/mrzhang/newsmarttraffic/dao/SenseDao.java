package com.example.mrzhang.newsmarttraffic.dao;

import android.content.Context;

import com.example.mrzhang.newsmarttraffic.db.OrmDBHelper;

import java.sql.SQLException;

/**
 *
 */
public class SenseDao {
    private Context context;

    public SenseDao(Context context) {
        this.context = context;
    }

    public void add(Sense sense){
        OrmDBHelper help = OrmDBHelper.gethelp(context);
        try {
            help.getSenseDao().create(sense);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
