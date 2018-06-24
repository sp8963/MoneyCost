package com.example.pingliu.moneycost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sp on 2017/5/21.
 */

public class MyDBHelper extends SQLiteOpenHelper {
    // 資料庫名稱
    public static final String DATABASE_NAME = "mydata.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;
    // 資料庫物件，固定的欄位變數
    private static MyDBHelper Database;
    public static final String TABLE_NAME = "item";

    // 編號表格欄位名稱，固定不變



    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }




    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static MyDBHelper getInstances(Context context) {
        if (Database == null) {
            return new MyDBHelper(context);
        } else {
            return Database;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table" + " " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,name text,datetime text,money text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            //删除老表
            db.execSQL("drop" + TABLE_NAME);
            //重新创建表
            onCreate(db);
        }
    }
    public void insert(String datetime, String name, String money) {
        //让数据库可写
        SQLiteDatabase database = getWritableDatabase();
        /*
        类似于HashMap 都有键值对
        key 对应的列表中的某一列的名称,字段
        value 对应字段要插入的值
         */
        ContentValues values = new ContentValues();
        values.put("datetime", datetime);
        values.put("name", name);
        values.put("money", money);
        //插入
        database.insert(TABLE_NAME, null, values);
        //插入完成后关闭,以免造成内存泄漏
        database.close();
    }
    public Cursor query() {
        //数据库可读
        SQLiteDatabase database = getReadableDatabase();
        //查找
        Cursor query = database.query(TABLE_NAME, null, null, null, null, null, null);
        return query;
    }
    public void delete(int id, String datetime, String name, String money) {
        SQLiteDatabase database = getWritableDatabase();
        /*
        删除的条件,当id = 传入的参数id时,sex = 传入的参数sex时,age = 传入的age,hobby = 传入的hobby时
        当条件都满足时才删除这行数据,一个条件不满足就删除失败
         */
        String where = "id=? and datetime = ? and name = ? and money = ?";
        //删除条件的参数
        String[] whereArgs = {id + "", datetime, name, money};
        database.delete(TABLE_NAME, where, whereArgs);
        database.close();
    }
    public void delete(int id) {
        SQLiteDatabase database = getWritableDatabase();
        //当条件满足id = 传入的参数的时候,就删除那整行数据,有可能有好几行都满足这个条件,满足的全部都删除
        String where = "id = ?";
        String[] whereArgs = {id + ""};
        database.delete(TABLE_NAME, where, whereArgs);
        database.close();
    }
    public void updata(int id, String datetime, String name, String money) {
        SQLiteDatabase database = getWritableDatabase();
//        update(String table,ContentValues values,String  whereClause, String[]  whereArgs)
        String where = "id = ?";
        String[] whereArgs = {id + ""};
        ContentValues values = new ContentValues();
        values.put("datetime", datetime);
        values.put("name", name);
        values.put("money", money);
        //参数1  表名称	参数2  跟行列ContentValues类型的键值对Key-Value
        // 参数3  更新条件（where字句）	参数4  更新条件数组
        database.update(TABLE_NAME, values, where, whereArgs);
        database.close();
    }

}
