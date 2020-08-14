package com.aliya.view.fitsys;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 自定义拓展FitSystemWindow属性 - LinearLayout.
 *
 * @author a_liYa
 * @date 2017/7/19 12:40.
 * @see FitHelper
 */
public class FitWindowsLinearLayout extends LinearLayout {

    private FitHelper helper;

    public FitWindowsLinearLayout(Context context) {
        this(context, null);
    }

    public FitWindowsLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FitWindowsLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            helper = new FitHelper(this, attrs, new FitHelper.FitWindowsProxy() {
                @Override
                public boolean fitSystemWindowsProxy(Rect insets, boolean callSuper) {
                    return callSuper ?
                            FitWindowsLinearLayout.super.fitSystemWindows(insets)
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
