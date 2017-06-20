package com.yundian.star.greendao.update;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.yundian.star.greendao.gen.DaoMaster;
import com.yundian.star.greendao.gen.StarInfoDao;
import com.yundian.star.utils.LogUtils;

import org.greenrobot.greendao.database.StandardDatabase;


/**
 * Created by wenld- on 2015/12/24.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DaoMaster.createAllTables(new StandardDatabase(db), false);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            DBMigrationHelper migratorHelper = new DBMigrationHelper();
            //判断版本， 设置需要修改得表  我这边设置一个 FileInfo
            if (true) {
                LogUtils.loge("需要更新数据库---------------------------------------------");
                migratorHelper.onUpgrade(db, StarInfoDao.class);
            }
        } catch (ClassCastException e) {
        }
    }
}
