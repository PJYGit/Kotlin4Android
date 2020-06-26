# ContentProvider

[TOC]

跨程序共享数据，如读取通讯录、短信和媒体库等。

使用ContentProvider是Android实现跨程序共享数据的安全标准方式。

## 运行时权限

顾名思义，程序运行时申请的权限，避免安装时一次性申请所有权限导致的“店大欺客”现象。

分为普通权限和危险权限，普通权限系统自动许可，危险权限，如读取联系人信息、位置信息等需要用户手动授权。以下为Android10的危险权限（仅列出权限组名，组内有多种权限名）。

---

CALENDAR	CALL_LOG	CAMERA		CONTACTS	LOCATION	MICROPHONE	PHONE	SENSORS

ACTIVITY_RECOGNITION	SMS	STORAGE

---

## 程序运行时申请权限

以直接拨号权限CALL_PHONE为例

1 AndroidManifest.xml中申请

```xml
<uses-permission android:name="android.permission.CALL_PHONE" />
```

2 检测是否已允许

```kotlin
if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
    != PackageManager.PERMISSION_GRANTED
) { // 未允许，运行中申请权限  1->requestCode 唯一即可
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), 1)
} else { // 允许，执行操作
    call()
}
```

3 重写申请权限完毕后的回调函数

```kotlin
override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
        1 -> {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 允许
                call()
            } else { // 不允许
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
```

## 访问其他程序的数据

ContentProvider的两种用法：使用现有的ContentProvider读取和操作象形程序中的数据；创建自己的ContentProvider，给程序的数据提供外部访问接口。

### ContentResolver基本用法

1 通过Context类获取实例，调用query(), insert(), update(), delete()方法

Uri参数：authority + path

authority -> 一般为package name.provider

path -> 表名

头部需要加上协议声明，例：content://com.example.app.provider/table1

```kotlin
val uri = Uri.parse("content://com.example.app.provider/table1")
val cursor = contentResolver.query(uir, projection, selection, selectionArgs,sortOrder)
// query参数介绍与SQLite类似，见/Notes/Storage.md
// insert() delete() update()略
```



