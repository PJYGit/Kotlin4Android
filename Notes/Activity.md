# Activity

[TOC]

**Codes Directory: FirstLineOfCode/Activity**

## Layout

activity_*.xml

~~~xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    <!-- match_parent 跟父元素一样宽/高 -->
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".activity.MainActivity">

    
    <TextView
        android:id="@+id/message"
        <!-- wrap_content 当前元素高/宽刚好能包含内容即可 -->
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
    

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/forecastList"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
~~~

## 在AndroidManifest.xml注册Activity

~~~xml
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
~~~

## Activity中使用Toast

Toast是一种很好的向用户提示短小并且短时间的信息的方式（如点击按钮提示）。

~~~kotlin
// context + msg + showing time: LENGTH_SHORT/LONG
Toast.makeText(this, "Hey! Don't touch me!",Toast.LENGTH_SHORT).show()
~~~

## Simplify findViewById() in Activity

~~~groovy
// Project build.gradle -> 引入extension
classpath "org.jetbrains.kotlin:kotlin-android-extensions:$kotlin_version"
~~~

~~~kotlin
import kotlinx.android.synthetic.main.activity_main.*
~~~

After this, then you can delete all the find functions in your codes by using the id directly.

## Activity中使用Menu

~~~xml
<!-- create the menu directory and file in /res/menu/main.xml -->
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/add_item"
        android:title="Add" />
    <item
        android:id="@+id/remove_item"
        android:title="Remove" />
</menu>
~~~

override functions

~~~kotlin
// create the menu
override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main, menu)
    return true
}

// action listener
override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
        R.id.add_item -> Toast.makeText(
            this, "You added an item",
            Toast.LENGTH_SHORT
        ).show()
        R.id.remove_item -> Toast.makeText(
            this, "You removed an item",
            Toast.LENGTH_SHORT
        ).show()
    }

    return true
}
~~~

## 销毁一个Activity

1 BackButton -> 详细过程见Activity生命周期

2 调用finish()函数

## Activity中使用Intent

Intent是Android程序中各组之间进行交互的一种重要方式。一般分为显示Intent和隐示Intent。

###显示Intent 

(之页面切换)

~~~kotlin
button1.setOnClickListener {
    val intent = Intent(this, SecondActivity::class.java)
    startActivity(intent)
}
~~~

###隐示Intent 

(之页面切换)

~~~xml
<!-- 注册intent & category -->
<activity android:name=".SecondActivity">
    <intent-filter>
        <action android:name="android.intent.action.ACTION_START" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.MY_CATEGORY" />
    </intent-filter>
</activity>
~~~

~~~kotlin
button1.setOnClickListener {
    // 发送intent 已经注册该intent的activity会自行处理
    val intent = Intent("android.intent.action.ACTION_START")
    intent.addCategory("android.intent.category.MY_CATEGORY")
    startActivity(intent)
}
~~~

(之启动其他程序activity-Chrome打开网页)

~~~kotlin
button1.setOnClickListener {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://www.bytedance.com/zh/")
    startActivity(intent)
}
~~~

(之启动其他程序activity-Phone Call)

~~~kotlin
button1.setOnClickListener {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:10010")
    startActivity(intent)
}
~~~

### 向下一个Activity传递数据

putExtra()

~~~kotlin
button1.setOnClickListener {
    val data = "Hello second, this is first!"
    val intent = Intent(this, SecondActivity::class.java)
    intent.putExtra("greeting", data)
    startActivity(intent)
}
~~~

getStringExtra()

~~~kotlin
val greeting = intent.getStringExtra("greeting")
// operations
Log.d("SecondActivity", greeting)
~~~

### 返回数据给上一个Activity

startActivityForResult()

~~~kotlin
button1.setOnClickListener {
    val intent = Intent(this, SecondActivity::class.java)
    startActivityForResult(intent, 1)
}
~~~

back ways:

1. setResult()

```kotlin
button2.setOnClickListener {
    val intent = Intent()
    intent.putExtra("backmsg", "Go away first!")
    setResult(Activity.RESULT_OK, intent)
    finish()
}
```

2. override fun onBackPressed()

```kotlin
override fun onBackPressed() {
    val intent = Intent()
    intent.putExtra("backmsg", "Go away first!")
    setResult(Activity.RESULT_OK, intent)
    finish()
}
```

handle: override fun onActivityResult()

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
        1 -> if (resultCode == Activity.RESULT_OK) {
            val back = data?.getStringExtra("backmsg")
            Log.d("MainActivity", back)
        }
    }
}
```

## Activity生命周期

1 采用back stack管理activity

2 activity状态：

​	onCreate() -> onStart() -> onResume() -> onPause() -> onStop() -> onDestory() 

​	onRestart()

​	onPause(): 非栈顶，但可见。

​	onStop(): 非栈顶，不可见。

Activity销毁，返回状态丢失的解决方法：

~~~kotlin
override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    val state = "All the state information you need to save."
    outState.putString("state_str", state)
}
~~~

## Activity启动模式

standard: 可以不断重复创建

singleTop: 在栈顶时不重复创建

singleTask: 栈中有时不重复创建

singleInstance: 启用一个新的back stack来管理此类activity，其他程序调用此类activity是共享同一个back stack

在AndroidManifest.xml中指定，默认standard：

~~~xml
<activity android:name=".SecondActivity"
    android:launchMode="singleTask">
</activity>
~~~

## 开发技巧

1 获得当前activity名

​	BaseActivity 中 Log，使BaseActivity成为父类

2 随时退出程序

​	维护一个ActivityList，closeButton时候finishAll()

3 启动activity最佳方法-解决参数未知/混乱问题

​	伴生对象companion object

// TODO: Fill the concrete description and codes

