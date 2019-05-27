package com.aliya.view.fitsys;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 自定义拓展FitSystemWindow属性 - FrameLayout.
 *
 * @author a_liYa
 * @date 2017/7/19 上午11:24.
 * @see FitHelper
 */
public class FitWindowsFrameLayout extends FrameLayout {

    private FitHelper helper;

    public FitWindowsFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FitWindowsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            helper = new FitHelper(this, attrs, new FitHelper.FitWindowsProxy() {
                @Override
                public boolean fitSystemWindowsProxy(Rect insets, boolean callSuper) {
                    return callSuper ?
                            FitWindowsFrameLayout.super.fitSystemWindows(insets)
                            : fitSystemWindows(insets);
                }
            });
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (helper != null) {
            helper.fitInsetsChildView(child);
        }
    }

    @Override
    public android.view.WindowInsets dispatchApplyWindowInsets(android.view.WindowInsets insets) {
        if (helper != null) {
            helper.setWindowInsets(insets);
        }
        return super.dispatchApplyWindowInsets(insets);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (helper != null) {
            return helper.fitSystemWindows(insets);
        }
        return super.fitSystemWindows(insets);
    }
}
