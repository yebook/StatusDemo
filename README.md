## Android实现沉浸式状态栏及标题栏滑动变色

![效果图](http://pd8746ife.bkt.clouddn.com/demo.gif)



### 1. 沉浸式状态栏的实现

> 沉浸式状态栏实现的方式有很多，可自行百度谷歌
>
> 此处沉浸式状态栏实现采用第三方开源库 [**ImmersionBar**](https://github.com/gyf-dev/ImmersionBar),集成方式可直接查看官方文档，这里只做演示



##### 1.1 导入包

```
implementation 'com.gyf.immersionbar:immersionbar:2.3.2-beta01'
```



##### 1.2 初始化

```kotlin
//这里需要注意初始化必须要在setContentView(view)之后执行
//解决状态栏与布局顶部重叠，官方文档提供六种方案
//ImmersionBar.with(this).titleBar(mToolBar).init()	
ImmersionBar.with(this).init()
```



##### 1.3 关闭销毁

```kotlin
override fun onDestroy() {
    super.onDestroy()
    ImmersionBar.with(this).destroy()
}
```



到这，沉浸式状态栏的简单集成就实现了，当然根据布局复杂程度会有各种各样的问题，比如侧滑菜单，Fragment等中沉浸式状态栏的实现，详细可先查看[文档](https://github.com/gyf-dev/ImmersionBar)



### 2. 标题栏随着滑动透明度(颜色)变化，目标效果如网易云音乐详情页

思路：标题栏后面添加一个ImageView作为标题栏的背景图片，标题栏为透明，考虑到背景图片为头部图片的底部截取，可将大小设置与头部图片一样，然后将其上移至标题栏部分；随着滑动的变化，将背景图片的透明度进行设置

![布局示意图](http://pd8746ife.bkt.clouddn.com/%E5%B8%83%E5%B1%80%E7%A4%BA%E6%84%8F%E5%9B%BE.png)



![toolbar背景图](http://pd8746ife.bkt.clouddn.com/toolbar%E8%83%8C%E6%99%AF%E5%9B%BE.png)



##### 2.1 布局文件如下

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >

    <ScrollView
        android:id="@+id/mScrollView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">

            <ImageView
                android:id="@+id/mIvHeadView"
                android:layout_width="match_parent"
                android:layout_height="200dp"
                android:scaleType="centerCrop"
                android:src="@mipmap/test" />

            <TextView
                android:id="@+id/mTv"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:text="jkjlkjkljslkdjfklsjdfkljsdlkjfkljsdlkfj"
                />
        </LinearLayout>
    </ScrollView>


    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <ImageView
            android:id="@+id/mIvBarView"
            android:layout_width="match_parent"
            android:layout_height="200dp"
            android:alpha="0"
            android:scaleType="centerCrop"
            android:src="@mipmap/test"
            android:visibility="visible" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="vertical">

            <View
                android:id="@+id/mTopView"
                android:layout_width="match_parent"
                android:layout_height="0dp" />

            <android.support.v7.widget.Toolbar
                android:id="@+id/mToolBar"
                android:layout_width="match_parent"
                android:layout_height="?actionBarSize"
                app:title="图片标题栏滑动变化"
                app:titleTextColor="@android:color/white" />

        </LinearLayout>
    </FrameLayout>
</FrameLayout>
```





##### 2.2 设置状态栏透明

```kotlin
ImmersionBar.with(this).titleBar(mToolBar).init()


override fun onDestroy() {
    super.onDestroy()
    ImmersionBar.with(this).destroy()
}
```



##### 2.2 将标题图片上移至状态栏+标题栏高度的底部部位

```kotlin
 //将标题图片上移至状态栏+标题栏高度的底部部位
val params = mIvBarView.getLayoutParams()
//状态栏+标题栏的高度
var topHeight = mToolBar.layoutParams.height + getStatusHeight()

val ivTitleHeadBgParams = mIvBarView.getLayoutParams() as ViewGroup.MarginLayoutParams
val marginTop = params.height - topHeight
ivTitleHeadBgParams.setMargins(0, -marginTop, 0, 0)
```



##### 2.3 设置滑动监听，控制背景图片的透明度

```kotlin
//监听滑动状态设置透明度
mScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
    var headHight = mIvHeadView.layoutParams.height
    var flagHight = headHight - topHeight
    var alpha = 0f
    var scrolledY = if (scrollY < 0) 0 else scrollY

    if (flagHight > 0) {
       if (scrolledY < flagHight) {
           alpha = scrolledY.toFloat() / flagHight
        } else {
           alpha = 1f
        }
    }
    mIvBarView.alpha = alpha
}
```



### 参考资料

- [Android - 仿网易云音乐歌单详情页](https://www.jianshu.com/p/1995b7135073)



### 源码地址

- [Android实现沉浸式状态栏及标题栏滑动变色(Kotlin)](https://github.com/yebook/StatusDemo)