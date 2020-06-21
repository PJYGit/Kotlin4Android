# Basics

[TOC]

## Android 系统架构

1 Linux内核层 -> 各种底层驱动（显示驱动、音频等）

2 Libraries <- runtime 提供主要特性支持（SQLite 数据库支持等）

3 Framework 各种API

4 Applications 各类手机应用程序

## Android 开发特色

1 四大组件

~~~
Activity + Service + BroadcastReceiver + ContentProvider
~~~

2 丰富系统空间

3 SQLite 轻量数据库

4 丰富的多媒体服务

## 环境搭建

Android Studio + Kotlin + JDK12

虚拟机

过程略

## 项目结构

1 build.gradle (Project & Module)

~~~groovy
// how to change to alibaba source which is faster
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    ext.kotlin_version = '1.3.71'
    repositories {
        google()
        maven { url 'http://maven.aliyun.com/nexus/content/groups/public/'}
		maven { url'https://maven.aliyun.com/repository/public/' }
		maven { url'https://maven.aliyun.com/repository/google/' }
		maven { url'https://maven.aliyun.com/repository/jcenter/' }
		maven { url'https://maven.aliyun.com/repository/central/' }
        jcenter()
        
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.6.3'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        // classpath "org.jetbrains.kotlin:kotlin-android-extensions:$kotlin_version"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        maven { url 'http://maven.aliyun.com/nexus/content/groups/public/'}
		maven { url'https://maven.aliyun.com/repository/public/' }
		maven { url'https://maven.aliyun.com/repository/google/' }
		maven { url'https://maven.aliyun.com/repository/jcenter/' }
		maven { url'https://maven.aliyun.com/repository/central/' }
        jcenter()
        
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

~~~

~~~groovy
Moudule:
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.3"

    defaultConfig {
        applicationId "com.example.weatherapp"
        minSdkVersion 15
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // 是否对项目代码进行混淆
            minifyEnabled false
            // 混淆时使用的规则文件
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

}

dependencies {
    // add the deps. here
    
    // 本地依赖
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    // 远程依赖关键词 implementation
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    // 测试用例库
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
    implementation 'androidx.recyclerview:recyclerview:1.1.0'
    implementation "com.google.code.gson:gson:2.4"
    implementation "com.squareup.picasso:picasso:2.5.2"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.1"
}

~~~



2 app

Source code and resources.

libs -> jar packages of the 3rd party

java -> source codes

res -> drawable-pics & layout-xml & values-strs

~~~xml
activity_main.xml
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".activity.MainActivity">

    <TextView
        android:id="@+id/message"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
~~~

~~~xml
strings.xml
<resources>
    <string name="app_name">HelloWorld</string>
</resources>

remark: R.string.app_name | @string/app_name
~~~



AndroidManifest.xml -> Configuration and permission file

~~~xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.example.weatherapp">

    <!-- Network Permission -->
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        android:usesCleartextTraffic="true"
        tools:targetApi="m">

        <activity android:name=".activity.MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
~~~

## 日志工具

Log (android.util.log)

Log.v() + Log.d() + Log.i() + Log.w() + Log.e() 

~~~kotlin
// tag (classname) + msg
Log.d("MainActivity", "onCreate execute")
~~~









