DragFloatingView
-

[![](https://jitpack.io/v/enChenging/DragFloatingView.svg)](https://jitpack.io/#enChenging/DragFloatingView)

	
## 用法

>Android Studio

将其添加到存储库build.gradle中
```xml
allprojects {
    repositories {
      	...
        maven{url 'https://jitpack.io'}
    }
}
```
 在build.gradle文件中的dependencies下添加引用：
	
```kotlin
implementation 'com.github.enChenging:DragFloatingView:1.0.0'
```
详细使用见工程里的[simple](https://github.com/enChenging/DragFloatingView/tree/master/simple)

代码使用：
```xml
<com.release.draglibrary.DragFloatingView
    android:id="@+id/vDragFloatingView"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="50dp"
    android:layout_marginEnd="50dp"
    android:background="@drawable/rect_8273ef_100_8"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintTop_toTopOf="parent">

    <!--放入子控件-->

</com.release.draglibrary.DragFloatingView>

```

```kotlin
 /**
 * 是否开启吸附功能
 */
fun openAdsorption()
```


## 混淆

```java
#drawlibrary
-dontwarn com.release.draglibrary.**
-keep class com.release.draglibrary.**{*;}

```

声明
-
本控件用作分享与学习。





