# BroadCast

[TOC]

## Introduction

Android中每个应用程序都可以对自己感兴趣的广播进行注册，如此，该程序就只会接收到自己所关心的广播内容。

广播可能来自系统，也可能来自其他应用程序。

**广播类型：**

标准广播（normal broadcasts）：完全异步执行，会被所有的BroadcastReceiver几乎同一时刻收到。效率高但无法被截断。

有序广播（ordered broadcasts）：同步执行，同一时刻只有一个BroadcastReceiver收到广播，该BroadcastReceiver的逻辑执行完毕后才会向下传播。不同的BroadcastReceiver有优先级区分，可截断。

## 接收系统广播

Android系统内置了很多系统级别的广播，比如开机、电量变化、时间变化等。

更多系统广播类型见 <https://developer.android.google.cn/guide/components/broadcast-exceptions.html> 

### 动态注册

以时间变化广播 TIME_TICK 为例

1 创建自己的MyBroadcastReceiver继承自BroadcastReceiver

2 重写onReceive()方法

3 intentFilter过滤广播事件

4 注册MyBroadcastReceiver

5 重写onDestroy()方法取消注册

```kotlin
class MainActivity : AppCompatActivity() {

    lateinit var timeChangeReceiver: TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // filter the specific intent action
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")
        // regist the receiver
        timeChangeReceiver = TimeChangeReceiver()
        registerReceiver(timeChangeReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        // unregist
        unregisterReceiver(timeChangeReceiver)
    }

    /**
     * Our specific broadcast receiver.
     */
    inner class TimeChangeReceiver : BroadcastReceiver() {
        // override onReceive
        override fun onReceive(context: Context?, intent: Intent?) {
            // handling logic
            Log.d("TimeChanged", "Time changed")
            Toast.makeText(context, "Time changed.", Toast.LENGTH_SHORT).show()
        }
    }
}
```

### 静态注册

动态注册灵活性较高，但缺点是必须在应用程序启动之后才能接收广播，静态注册可以解决问题。

以实现开机启动 BOOT_COMPLETED 为例

1 使用Android Studio创建BroadcastReceiver并重写onReceive()方法

```kotlin
class BootCompleteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.d("BroadcastReceiver", "Boot completed.")
        Toast.makeText(context, "Boot completed.", Toast.LENGTH_SHORT).show()
    }
}
```

2 在AndroidManifest.xml中注册，并添加事件过滤器

```xml
<receiver
    android:name=".BootCompleteReceiver"
    android:enabled="true"
    android:exported="true">

    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>

</receiver>
```

3 某些系统广播的监听需要开启权限

```xml
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

## 发送自定义广播

### 发送标准广播

自定义接收器和标准广播的注册

```xml
<receiver
    android:name=".MyBroadcastReceiver"
    android:enabled="true"
    android:exported="true">

    <intent-filter>
        <action android:name="com.example.broadcast.MY_BROADCAST" />
    </intent-filter>

</receiver>
```

广播的发送

```kotlin
val intent = Intent("com.example.broadcast.MY_BROADCAST")
// 要设置包名
intent.setPackage(packageName)
sendBroadcast(intent)
```

### 发送有序广播

自定义接收器和标准广播的注册，这里多了**优先权**设置

优先权越高，越早收到广播

```xml
<intent-filter
    android:priority="100">
    <action android:name="com.example.broadcast.MY_BROADCAST" />
</intent-filter>
```

先收到广播的接收器执行完逻辑后可以截断传播

```kotlin
override fun onReceive(context: Context, intent: Intent) {
    // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
    Log.d("MyBroadcastReceiver", "Message received.")
    Toast.makeText(context, "Message received.", Toast.LENGTH_SHORT).show()
    // 截断广播的传播
    abortBroadcast()
}
```

有序广播的发送

```kotlin
val intent = Intent("com.example.broadcast.MY_BROADCAST")
intent.setPackage(packageName)
// 发送有序广播
sendOrderedBroadcast(intent, null)
```

---

**注意：**onReceive()方法中不要添加耗时操作，因为BroadcastReceiver中**不允许开启新线程**。

