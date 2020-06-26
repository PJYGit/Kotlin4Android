package com.example.storage.DB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(private val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val sql = "create table book ( " +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text )"

    private val createCategory = "create table category ( " +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer )"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sql)
        db.execSQL(createCategory)
        Toast.makeText(context, "Database Created", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists book")
        db.execSQL("drop table if exists categroy")
        onCreate(db)
    }
}