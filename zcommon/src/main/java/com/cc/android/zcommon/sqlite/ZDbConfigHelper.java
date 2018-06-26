package com.cc.android.zcommon.sqlite;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Database configuration helper.
 *
 * @Author:LiuLiWei
 */
public class ZDbConfigHelper {

    private static final String TAG = "ZDbConfigHelper";

    public static final String ID = "_id";
    public static final String TIME = "time";

    public static final String INTEGER_TYPE = " Integer";
    public static final String TEXT_TYPE = " TEXT";
    public static final String LONG_TYPE = " Long";
    public static final String DOUBLE_TYPE = " Double";
    public static final String DESC = " DESC";

    private static volatile ZDbConfigHelper sInstance = null;
    private static Context sContext;
    private static String sDatabaseName;
    private static int sDataBaseVersion;
    public static HashMap<String, List<String>> sTableMap = new HashMap<>();


    public static Context getContext() {
        return sContext;
    }

    private ZDbConfigHelper(Context context) {
        this.sContext = context;
    }

    public static ZDbConfigHelper getInstance(Context context) {
        if (null == sInstance) {
            synchronized (ZDbConfigHelper.class) {
                sInstance = new ZDbConfigHelper(context);
                ZDbManager.setContext(context);
            }
        }
        return sInstance;
    }

    public static String getDatabaseName() {
        return sDatabaseName;
    }

    public static int getDatabaseVersion() {
        return sDataBaseVersion;
    }

    public void setDatabaseVersion(int versionCode) {
        sDataBaseVersion = versionCode;
    }

    public void setDatabaseName(String databaseName) {
        sDatabaseName = databaseName;
    }

    /**
     * @param tableName
     * @param columnAndTypes
     */
    public synchronized void addTable(String tableName, List<String> columnAndTypes) {
        if (TextUtils.isEmpty(sDatabaseName)) {
            Log.e(TAG, "When you invoke this method, you must invoke the method setDatabaseName() firstly to specify a database name");
            return;
        }
        if (sDataBaseVersion < 1) {
            Log.e(TAG, "When you invoke this method, you must invoke the method setDatabaseVersion() firstly to specify the database's version code");
            return;
        }
        if (TextUtils.isEmpty(tableName)) {
            throw new NullPointerException("The table name cannot be null.");
        }
        if (null == columnAndTypes || columnAndTypes.size() == 0) {
            throw new NullPointerException("The table's column amount cannot equals zero.");
        }
        List<String> list = new ArrayList<>();
        Iterator iterator = columnAndTypes.iterator();
        while (iterator.hasNext()) {
            list.add((String) iterator.next());
        }
        sTableMap.put(tableName, list);
    }

    /**
     * @return
     */
    public static HashMap<String, List<String>> getTables() {
        return sTableMap;
    }

    /**
     * @param tableName
     * @return
     */
    public static List<String> getSpecificTable(String tableName) {
        if (TextUtils.isEmpty(tableName)) {
            throw new NullPointerException("The table name cannot be null.");
        }
        return sTableMap.containsKey(tableName) ? sTableMap.get(tableName) : null;
    }
}
