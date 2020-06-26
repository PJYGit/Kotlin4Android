# Storge

[TOC]

## 持久化技术

将内存中的瞬时数据保存到存储设备中。

持久化技术提供一种机制 -> 让数据在瞬时状态和持久状态之间转换。

Android的3中持久化技术功能：

文件存储、SharedPreferences存储、数据库存储（SQLite）

## 文件存储

不对存储的内容进行格式化处理，原封不动保存，适合存储简单的文本数据/二进制数据。

对于存储复杂的结构化数据，需要定义自己的格式规范。

**写入文件：**

文件操作模式：

| 模式         | 描述 |
| ------------ | ---- |
| MODE_PRIVATE | 覆盖 |
| MODE_APPEND  | 追加 |

利用输出文件流：

```kotlin
try {
    // 存储的文件不可以包含路径，所有的文件默认存储到/data/data/<package name>/files/
    val output = openFileOutput("data", Context.MODE_PRIVATE)
    val writer = BufferedWriter(OutputStreamWriter(output))
    // 利用kotlin提供的内置扩展函数use可以不用手动关闭文件流
    writer.use {
        it.write(inputText)
    }
} catch (e: IOException) {
    e.printStackTrace()
}
```

**读取文件：**

```kotlin
val content = StringBuilder()
try {
    // 同样默认到/data/data/<package name>/files/找文件
    val input = openFileInput("data")
    val reader = BufferedReader(InputStreamReader(input))
    // 利用kotlin提供的内置扩展函数use可以不用手动关闭文件流
    reader.use {
        reader.forEachLine { content.append(it) }
    }
    Log.d("File", "done")
} catch (e: IOException) {
    e.printStackTrace()
}
```

## SharedPreferences存储

使用**键值对**的方式存储数据。

支持多种不同数据类型的存储，如，存储整型读取也是整型等。可以用来实现记住密码功能（不要明文存储，哈希等都行）。

**数据存储到SharedPreferences：**

1 获取SharedPreferences对象：

利用Context类

```kotlin
// 默认文件夹/data/data/<package name>/shared_prefs
getSharedPreferences("data", Context.MODE_PRIVATE) // 只有一个模式
```

利用Activity类

```kotlin
getSharedPreferences(Context.MODE_PRIVATE) // 只有一个模式
```

2 调用edit()

3 向editor添加数据

4 apply()提交

```kotlin
val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
editor.putString("name", "PJ")
editor.putInt("age", 20)
editor.putBoolean("handsome", true)
editor.apply()
```

**从SharedPreferences读取数据：**

```kotlin
val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
// (key, default value)
val name = prefs.getString("name", "")
val age = prefs.getInt("age", 0)
val handsome = prefs.getBoolean("handsome", true)
// Log
```

## SQLite数据库存储

###数据类型

| 关键字  | 描述       |
| ------- | ---------- |
| integer | 整型       |
| real    | 浮点型     |
| text    | 文本类型   |
| blob    | 二进制类型 |

###创建数据库

1 创建MyDatabaseHelper继承自SQLiteOpenHelper

参数：（context，db name, cursor, version）

```kotlin
class MyDatabaseHelper(private val context: Context, name: String, version: Int) :
    SQLiteOpenHelper(context, name, null, version) {

    private val sql = "create table book ( " +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text )"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(sql)
        Toast.makeText(context, "Database Created", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}
```

2 创建

```kotlin
// 数据库文件默认位置/data/data/<package name>/databases/
val dbHelper = MyDatabaseHelper(this, "BookStore.db", 1)
// getWritableDatabase()
dbHelper.writableDatabase
```

###升级数据库

数据库存在时不会重复创建也就是onCreate()不会被再次调用，那么新建一张表该怎么办呢？

```kotlin
override fun onCreate(db: SQLiteDatabase) {
    db.execSQL(sql)
    db.execSQL(createCategory) // another table
    Toast.makeText(context, "Database Created", Toast.LENGTH_SHORT).show()
}

override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    db.execSQL("drop table if exists book")
    db.execSQL("drop table if exists categroy")
    onCreate(db)
}
```

只需要传递较之前高的版本号version即可调用onUpgrade()

```kotlin
val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2) // 2 > 1
dbHelper.writableDatabase
```

### SQLite的CRUD

**C-create 添加**

利用ContentValues() & insert

```kotlin
val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)
val db = dbHelper.writableDatabase
val values_one = ContentValues().apply {
    put("name", "The First Line Of Code")
    put("author", "Guo Lin")
    put("pages", 692)
    put("price", 75.4)
}
db.insert("book", null, values_one)
```

**U-update 更新**

利用ContentValues() & update

```kotlin
val dbHelper = MyDatabaseHelper(this, "BookStore.db", 2)
val db = dbHelper.writableDatabase
val values = ContentValues()
values.put("price", 99)
// table name , new values, key + 占位符, name
db.update("book", values, "name = ?", arrayOf("Paris"))
```

**D-delete 删除**

```kotlin
val db = dbHelper.writableDatabase
db.delete("book", "pages > ?", arrayOf("600"))
```

**R-retrieve 查询**

query()参数表

| 参数          | 对应SQL                   | 描述                    |
| ------------- | ------------------------- | ----------------------- |
| table         | from table_name           | 表名                    |
| columns       | select column1, column2   | 列名                    |
| selection     | where column = value      | where约束条件           |
| selectionArgs | -                         | 为where占位符提供具体值 |
| groupBy       | group by column           | group by的列            |
| having        | having column = value     | group by后进一步约束    |
| orderBy       | order by column1, column2 | 排序方式                |

使用

```kotlin
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
```

---

**Remark：**所有的操作其实均可以使用SQL语句进行 -> db.execSQL("sql")，查询例外 -> db.rawQuery("sql", null)

---

## 最佳实践

### 使用事务

确保一系列操作的原子性，利用transaction相关函数。

```kotlin
val db = dbHelper.writableDatabase
```

若在try语段中出现异常，则所有的操作都会被取消。

### 升级数据库最佳写法

onUpgrade()方法中添加if语句判断数据库版本号，不同覆盖形式，不同操作。

