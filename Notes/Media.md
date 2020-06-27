# Media

[TOC]

Android设备上丰富的多媒体组件为丰富我们的程序提供了很好的支持。

听音乐？看电影？记录美好的一刻？... 现代智能手机的功能一应俱全。

## 使用通知

应用程序不在前台运行，却要向用户提示信息时，可以借助通知实现。

状态栏会显示通知，下拉查看详细信息。

### 通知渠道

为了防止应用程序滥用通知功能，Android 8.0开始引入了**通知渠道**这个概念。

通知渠道，顾名思义，就是每条通知都要有自己的渠道，用户对该渠道拥有控制权，如打开推荐通知，打开铃声，关闭震动等。可以理解为对各类通知的再细化。

1 获取NotificationManager实例

2 判断版本并创建通知渠道，设置重要等级，不同等级提示方式不同。

```
IMPORTANCE_DEFAULT IMPORTANCE_HIGH ...
```

3 创建Notification对象，并设置属性

```kotlin
标题、内容、图标
点击Intent意图（PendingIntent）
点击后自动消失
等属性
进阶属性style等
.setStyle(
    NotificationCompat.BigPictureStyle()
        .bigPicture(BitmapFactory.decodeResource(resources, R.drawable.big_image))
)
```

4 notify()

```kotlin
val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val channel =
        NotificationChannel("normal", "Normal", NotificationManager.IMPORTANCE_DEFAULT)
    manager.createNotificationChannel(channel)
}

sendNotice.setOnClickListener {
    val intent = Intent(this, NotificationActivity::class.java)
    // 另外有getService()  getBroadcast()
    val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

    val notification = NotificationCompat.Builder(this, "normal")
        .setContentTitle("This is notice title")
        .setContentText("This sis notice content text")
        .setSmallIcon(R.drawable.small_icon)
        .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.large_icon))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()
    manager.notify(1, notification)
}
```

## Camera和相册

// TODO：Fill the codes

## 播放多媒体文件

### 播放音频

MediaPlayer

### 播放视频

VideoView