//package com.aliya.view.fitsys;
//
//import android.content.Context;
//import android.graphics.Rect;
//import android.os.Build;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowInsets;
//import android.widget.RelativeLayout;
//
///**
// * 自定义拓展FitSystemWindow属性 - LinearLayout.
// *
// * @author a_liYa
// * @date 2017/7/19 12:40.
// * @see FitHelper
// */
//public class FitWindowsRelativeLayout extends RelativeLayout implements FitHelper.FitWindowsProxy {
//
//    private FitHelper helper;
//
//    public FitWindowsRelativeLayout(Context context) {
//        this(context, null);
//    }
//
//    public FitWindowsRelativeLayout(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public FitWindowsRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            helper = new FitHelper(context, attrs, this);
//        }
//    }
//
//    @Override
//    public void addView(View child, int index, ViewGroup.LayoutParams params) {
//        super.addView(child, index, params);
//        if (helper != null) {
//            helper.fitInsetsChildView(child);
//        }
//    }
//
//    @Override
//    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
//        if (helper != null) {
//            helper.setWindowInsets(insets);
//        }
//        return super.dispatchApplyWindowInsets(insets);
//    }
//
//    @Override
//    protected boolean fitSystemWindows(Rect insets) {
//        if (helper != null) {
//            return helper.fitSystemWindows(insets);
//        }
//        return super.fitSystemWindows(insets);
//    }
//
//    @Override
//    public boolean fitSystemWindowsProxy(Rect insets, boolean callSuper) {
//        return callSuper ? super.fitSystemWindows(insets) : fitSystemWindows(insets);
//    }
//
//}
