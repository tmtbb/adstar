package com.yundian.star.greendao.update;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yundian.star.greendao.gen.DaoMaster;
import com.yundian.star.greendao.gen.StarInfoDao;
import com.yundian.star.utils.LogUtils;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Growth on 2016/3/3.
 */
public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        LogUtils.loge("更新了------------------");
        MigrationHelper.migrate(db,StarInfoDao.class);
    }
}
