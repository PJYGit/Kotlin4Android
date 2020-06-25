# Fragment

[TOC]

## What

一种可以嵌入在Activity中的UI片段，更充分合理利用大屏幕空间 -> 迷你Activity，有自己的生命周期，可动态添加。

## 简单使用

1 写fragment的布局代码（xml文件）

2 建立MyFragment类继承自Fragment()，重写onCreateView()方法，inflate布局文件

```kotlin
class LeftFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.left_fragment, container, false)
    }
}
```

3 activity_main.xml中注册fragment

```xml
<fragment
    android:id="@+id/leftFragment"
    android:name="com.example.fragment.fragment.LeftFragment"
    android:layout_width="0dp"
    android:layout_height="match_parent"
    android:layout_weight="1"/>
```

## 动态添加

1 将activity_main.xml中某区域设置为layout，Framelayout适用

```xml
<FrameLayout
    android:id="@+id/rightLayout"
    android:layout_width="0dp"
    android:layout_height="match_parent"
    android:layout_weight="1">
</FrameLayout>
```

2 利用supportFragmentManager动态添加fragment

```kotlin
private fun replaceFragment(fragment: Fragment) {
    val fragmentManager = supportFragmentManager
    val transaction = fragmentManager.beginTransaction()
    transaction.replace(R.id.rightLayout, fragment)
    transaction.commit()
}

// replaceFragment(MyFragment())
```

3 实现返回栈---点击返回键不退出，返回上一个fragment

```kotlin
transaction.addToBackStack(null)
```

## Fragment和Activity交互

A 调 F

```kotlin
val fragment = leftFragment as LeftFragment
```

F 调 A

```kotlin
if (activity != null){
    val mainActivity = activity as MainActivity // Context
}
```

## Fragment生命周期

包含了Activity的生命周期调用函数，见Activity.md

额外的函数：

onAttach(): Fragment和Acitivity建立关联时调用，onCreate()之前

onCreateView(): 创建Fragment视图，onCreate()之后

onActivityCreated(): 确保与Fragment相关联的Activity创建完毕时调用，onStart()前

onDestroyView(): 移除Fragment关联的视图，onStop()之后，可回调onCreateView()

onDetach(): 解除两者关联，onDestroy()之后

## 动态加载布局---限定符（qualifier）

解决单双页问题。

新建文件夹/res/layout-large，再写一个activity_main.xml，则大屏硬件会加载当前文件夹下的布局文件。

限定符介绍见下：

| 屏幕特征 | 限定符 | 描述       |
| -------- | ------ | ---------- |
| 大小     | small  | 小屏幕     |
|          | normal | 中等屏幕   |
|          | large  | 大屏幕     |
|          | xlarge | 超大屏幕   |
| 分辨率   | ldpi   | <120dpi    |
|          | mdpi   | 120~160dpi |
|          | hdpi   | 160~240dpi |
|          | xhdpi  | 240~320dpi |
|          | xxhdpi | 320~480dpi |
| 方向     | land   | 横屏       |
|          | port   | 竖屏       |

以上表格界限定义不明确，命名文件夹时可以指定最小宽度限定符，如：

layout-sw600dp -> 当屏幕宽度大于600dp时，加载该文件夹下布局。