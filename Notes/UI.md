# UI

[TOC]

**Code Directory: /FirstLineOfCode/UI**

目前只包含较为常用的且简单的控件，其他的后面会补充。

## 界面编写方式

1 ConstraintLayout 拖拽

2 XML自己编写 -> 良好的屏幕适配性

## 常用控件

### TextView

```xml
<TextView
    android:id="@+id/textView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center"
    android:textColor="#00ff00"
    android:textSize="24sp"
    android:text="Hello World!" />
```

android:+属性，包含字体大小、颜色和对齐方式等等，详细可查阅文档。

### Button

```xml
<Button
    android:id="@+id/button"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="Button"
    android:textAllCaps="false"/>
```

新的添加事件监听器的方式，通过让activity继承View.OnClickListener实现，可以支持activity中不同控件监听器的统一实现。

```kotlin
// 继承
class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 设置监听器
        button.setOnClickListener(this)
    }

    // 重写点击事件方法
    override fun onClick(v: View?) {
        when (v?.id) {
            // 获得id以便统一处理
            R.id.button -> {
                Log.d("Button", "button clicked")
            }
            // else
        }
    }
}
```

### EditText

```xml
<EditText
    android:id="@+id/editText"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:maxLines="2"
    android:hint="Please type something here"/>
```

获取所输入的文本的方式：

```kotlin
val inputText = editText.text.toString()
Toast.makeText(this, inputText, Toast.LENGTH_SHORT).show()
```

### ImageView

将需要使用的图片放置在/res/drawable文件夹下

```xml
<ImageView
    android:id="@+id/imageView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    // 目标文件位置
    android:src="@drawable/stars" />
```

动态更换图片的方法：

```kotlin
// R.drawable.newpic
imageView.setImageResource(R.drawable.black_stars)
```

### ProgressBar

```xml
<ProgressBar
    android:id="@+id/progressBar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

加载完毕如何让其消失，或者重新出现：

Android的控件都具有visibility属性，有三种类别：

visible：可见		invisible：不可见，但仍然占据原来位置大小		gone：不可见且无大小

可以在xml中使用android:visibility设定也可以在代码中设置，以下为用代码动态设置样例：

```kotlin
if (progressBar.visibility == View.VISIBLE){
    progressBar.visibility = View.GONE
} else {
    progressBar.visibility = View.VISIBLE
}
```

在xml中设置进度条样式（默认圈圈），此处设为横向条形 -> 可以设置最大值等属性：

```xml
style="?android:attr/progressBarStyleHorizontal"
android:max="100"
```

设置进度：

```kotlin
progressBar.progress += 10
```

### AlertDialog

一个置于所有元素之上的对话框，可以屏蔽其他控件的交互能力。一般用于提示警告等重要信息。

```kotlin
AlertDialog.Builder(this).apply {
    setTitle("Warning!")
    setMessage("You really want to do this?")
    setCancelable(false)
    setPositiveButton("OK") { dialog, which -> } // operation after ok
    setNegativeButton("CANCEL") { dialog, which -> } // operation after cancel
    show()
}
```

## 基本布局

### LinearLayout

线性布局

```xml
// 设置方向属性
android:orientation="vertical"

// 设置控件重力属性 android:layout_gravity
<Button
        android:id="@+id/button"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Button"
        // top center_vertical bottom
        android:layout_gravity="top" />

// 按比例设置 android:layout_weight
// 可以配合wrap_content使用，提高屏幕适配能力
android:layout_width="0dp"
android:layout_weight="1" // 2 3 ...
```

### RelativeLayout

相对布局

（之相对父布局）

```xml
android:layout_alignParentRight="true" // Left Top Bottom
```

（之相对其他控件）

```xml
android:layout_above="@id/*_id" // below
android:layout_toLeftOf="@id/*_id" // Right
```

### FrameLayout

帧布局，默认放在左上角

不常用，可以设置layout_gravity属性，Fragment中会用到。

## 自定义控件

Android中所有的控件都直接或间接继承自View，自定义控件利用继承结构来创建。

### 引入布局

1 自己写一个新的/layout/custom.xml

2 在activity_main.xml中引入

```xml
<include layout="@layout/custom" />
```

在MainActivity中消除掉原有自带布局（如果有的话，如标题栏等）

### 创建自定义控件

引入布局创建的自定义控件，当需要监听器事件时，需要每个都写一遍，造成不必要重复，创建类可以解决此问题。

以创建标题栏为例

```kotlin
class TitleLayout(contex: Context)
```

即可在其他布局文件中引入

```xml
<packagename.TitleLayout ... />
```

## ListView

手机屏幕有限但内容较多时，使用该控件进行滑动浏览。

每次滑动都需要加载一遍布局，优化方法：重写getView()方法时，判断convertView是否为null

每次滑动都需要find一遍控件，优化方法：创建ViewHolder类，采用上一步，非null时候设定viewHolder = view.tag as ViewHolder

点击事件：

```kotlin
listView.setOnItemClickListener { parent, view, position, id ->
    val fruit = items[position]
    Toast.makeText(this, fruit, Toast.LENGTH_SHORT).show()
}
```

## RecyclerView

ListView的增强版，性能更优，且可以实现横向滚动。

引入依赖：

```groovy
implementation 'androidx.recyclerview:recyclerview:1.1.0'
```

MyAdapter继承自RecylerView.Adapter\<MyAdapter.ViewHolder\>

需要在MainActivity中设置：

```kotlin
val layoutManager = LinearLayoutManager(this)
recycler.layoutManager = layoutManager

// 设置水平滑动
layoutManager.orientation = LinearLayoutManager.HORIZONTAL
```

其他的布局Manager还有 GridLayoutManager（网格布局）, StaggeredGridLayoutManager（瀑布流布局）。

重写onCreateViewHolder()时添加监听器，可以添加不同层级的。后期可以使用Lambda简化。