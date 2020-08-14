# FitSystemWindow
拓展Android view 的 FitSystemWindows的功能

### 一、依赖

```
compile 'com.aliya:fitwindow:3.0.0'
```

### 二、使用

```
<com.aliya.view.fitsys.FitWindowsFrameLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:fitsSystemWindows="true"
    app:fitType="bottom">
</com.aliya.view.fitsys.FitWindowsFrameLayout>
```

### 三、属性

fitType：三种类型

```
app:fitType="top"     // 适配状态栏
app:fitType="bottom"  // 适配导航栏
app:fitType="both"    // 默认值，适配两者

```

### 四、代码混淆

```
# 保持View的子类不被混淆
-keep public class * extends android.app.View
```