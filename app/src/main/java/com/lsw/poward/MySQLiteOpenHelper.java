package com.lsw.poward;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //스탬프 관리하는 테이블
        db.execSQL("CREATE TABLE stamp ( " +
                "idx integer primary key autoincrement, " +
                "id text," +
                "podo1 text, podo2 text, podo3 text, podo4 text, podo5 text, " +
                "podo6 text, podo7 text, podo8 text, podo9 text, podo10 text, " +
                "podo11 text, podo12 text, podo13 text, podo14 text, podo15 text, " +
                "podo16 text, podo17 text, podo18 text, podo19 text, podo20 text, " +
                "podo21 text, podo22 text, podo23 text, podo24 text, podo25 text, " +
                "podo26 text, podo27 text, podo28 text, podo29 text, podo30 text);");


        //회원정보 테이블
        db.execSQL("CREATE TABLE member ( " +
                "idx integer primary key autoincrement, " +
                "id text, " +
                "pw text, " +
                "name text, " +
                "content text, " +
                "reward text, " +
                "until text);");

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists savestamp");
        db.execSQL("drop table if exists member");
        onCreate(db);
    }

}
