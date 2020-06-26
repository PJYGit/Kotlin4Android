package com.example.storage

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.storage.DB.MyDatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)
        create.setOnClickListener {
            dbHelper.writableDatabase
        }

        addData.setOnClickListener {
            val dp = dbHelper.writableDatabase
            val values_one = ContentValues().apply {
                put("name", "The First Line Of Code")
                put("author", "Guo Lin")
                put("pages", 692)
                put("price", 75.4)
            }
            dp.insert("book", null, values_one)
            val values_two = ContentValues().apply {
                put("name", "Paris")
                put("author", "PJ")
                put("pages", 555)
                put("price", 66)
            }
            dp.insert("book", null, values_two)
        }

        updateData.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price", 99)
            db.update("book", values, "name = ?", arrayOf("Paris"))
        }

        deleteData.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("book", "pages > ?", arrayOf("600"))
        }

        queryData.setOnClickListener {
            val db = dbHelper.writableDatabase
            // 查询获得cursor对象
            val cursor = db.query(
                "book", null, null,
                null, null, null, null
            )
            // 循环
            if (cursor.moveToFirst()) {
                do {
                    // 获取键值对应值
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    Log.d("Database", name)
                } while (cursor.moveToNext())
            }
            // 关闭cursor
            cursor.close()
        }
    }

}
