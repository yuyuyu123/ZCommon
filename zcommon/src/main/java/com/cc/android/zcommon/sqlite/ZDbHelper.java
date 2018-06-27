package com.cc.android.zcommon.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ZDbHelper.
 *
 * @Author:LiuLiWei
 */
public final class ZDbHelper extends SQLiteOpenHelper {

    private static Context sContext;
    private static ZDbHelper sInstance = null;

    public static void setContext(Context context) {
        sContext = context;
    }

    private ZDbHelper() {
        super(sContext, ZDbConfigHelper.getDatabaseName(), null, ZDbConfigHelper.getDatabaseVersion());
    }

    public ZDbHelper(Context context) {
        super(context, ZDbConfigHelper.getDatabaseName(), null, ZDbConfigHelper.getDatabaseVersion());
    }

    public static synchronized ZDbHelper getsInstance() {
        if (sInstance == null) {
            sInstance = new ZDbHelper();
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            createAllTables(db);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //根据老的数据库版本名来升级数据库,增量升级
        switch (oldVersion) {
            case 1:
                //此处不应该有break; 保证版本跳跃时也可以全部更新，例如:由oldVersion为1直接到version 5
            case 2:
                //老版本为2时,说明一定执行过了老版本为1时的更新,再执行以后所有版本的更新即可,不需要使用网上的那种循环的方式。
            case 3:

            default:
                break;
        }
    }

    private void createAllTables(SQLiteDatabase db) {

       final HashMap<String, List<String>> tableMap = ZDbConfigHelper.getTables();
        if(null == tableMap || tableMap.keySet() == null) {
            return;
        }
        for (String key : tableMap.keySet()) {
            createTable(db, key, tableMap.get(key));
        }
    }

    /**
     * @param sqliteDatabase
     * @param table          要创建的数据表名
     * @param columns        列名
     */
    private void createTable(SQLiteDatabase sqliteDatabase, String table, String[] columns) {
        String createTable = "create table if not exists ";
        String primaryKey = " Integer primary key autoincrement";
        String text = " text";
        char leftBracket = '(';
        char rightBracket = ')';
        char comma = ',';
        int stringBufferSize = 170;
        StringBuffer sql = new StringBuffer(stringBufferSize); // StringBuffer的效率会更高一些
        sql.append(createTable).append(table).append(leftBracket).append(ZDbConfigHelper.ID).append(primaryKey).append(comma);
        for (String column : columns) {
            sql.append(column);
            sql.append(comma);
        }
        sql.append(ZDbConfigHelper.TIME).append(text).append(rightBracket);
        try {
            sqliteDatabase.execSQL(sql.toString());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void createTable(SQLiteDatabase sqliteDatabase, String table, List<String> columns) {
        String createTable = "create table if not exists ";
        String primaryKey = " Integer primary key autoincrement";
        String text = " text";
        char leftBracket = '(';
        char rightBracket = ')';
        char comma = ',';
        int stringBufferSize = 170;
        StringBuffer sql = new StringBuffer(stringBufferSize); // StringBuffer的效率会更高一些
        sql.append(createTable).append(table).append(leftBracket).append(ZDbConfigHelper.ID).append(primaryKey).append(comma);
        for (String column : columns) {
            sql.append(column);
            sql.append(comma);
        }
        sql.append(ZDbConfigHelper.TIME).append(text).append(rightBracket);
        try {
            sqliteDatabase.execSQL(sql.toString());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /**
     * 关闭数据库
     */
    public void closeDatabase(SQLiteDatabase db) {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /**
     * 插入数据
     *
     * @param table
     * @param nullColumnHack
     * @param values
     * @return
     */
    public synchronized long insert(final String table, final String nullColumnHack, final ContentValues values) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            return database.insert(table, nullColumnHack, values);
        } catch (Exception e) {
            return -1;
        } finally {
            closeDatabase(database);
        }
    }

    /**
     * 批量插入数据，显示使用事物,事物的效率在批量插入时非常的明显
     */
    public synchronized boolean insert(final String table, final String nullColumnHack,
                                       final ArrayList<ContentValues> values) {
        boolean result = true;
        SQLiteDatabase database = null;
            try {
            database = getWritableDatabase();
            database.beginTransaction();
            for (ContentValues value : values) {
                if (database.insert(table, nullColumnHack, value) < 0) {
                    result = false;
                    break;
                }
            }
            if (result) {
                database.setTransactionSuccessful();
            }
        } catch (Exception e) {
            return false;
        } finally {
            database.endTransaction();
            closeDatabase(database);
        }
        return result;
    }

    /**
     * 删除数据
     *
     * @param table
     * @param whereClause //删除条件
     * @param whereArgs   //删除条件值
     * @return
     */
    public int delete(final String table, final String whereClause, final String[] whereArgs) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            return database.delete(table, whereClause, whereArgs);

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeDatabase(database);
        }
    }

    /**
     * 更新数据
     *
     * @param table
     * @param values
     * @param whereClause
     * @param whereArgs
     * @return
     */
    public int update(final String table, final ContentValues values, final String whereClause,
                      final String[] whereArgs) {
        SQLiteDatabase database = null;
        try {
            database = getWritableDatabase();
            return database.update(table, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            closeDatabase(database);
        }
    }
}
