package com.example.mrzhang.newsmarttraffic.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mrzhang.newsmarttraffic.bean.Sense;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 *
 */
public class OrmDBHelper extends OrmLiteSqliteOpenHelper{
    private static OrmDBHelper Instance;
    private static Dao<Sense,Integer> senseDao;

    public OrmDBHelper(Context context) {
        super(context, "ormdb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.clearTable(connectionSource,Sense.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {

    }

    public static synchronized OrmDBHelper gethelp(Context context){
        if(Instance == null){
            synchronized (OrmDBHelper.class){
                if(Instance == null){
                    Instance = new OrmDBHelper(context);
                }
            }
        }
        return Instance;
    }

    public Dao<Sense,Integer> getSenseDao() throws SQLException {
        if(senseDao == null){
            senseDao = getDao(Sense.class);
        }
        return senseDao;
    }

    @Override
    public void close() {
        super.close();
        senseDao = null;
    }
}
