package com.aliya.view.fitsys;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

/**
 * 自定义拓展FitSystemWindow属性 - RecyclerView.
 * <p>
 * 因 RecyclerView child view 频繁添加删除，默认不会分发 child view，
 * 只有 child view 设置 setTag(R.id.tag_need_fit_child, true).
 * </p>
 *
 * @author a_liYa
 * @date 2017/8/21 10:04.
 * @see FitHelper
 */
public class FitWindowsRecyclerView extends RecyclerView {

    private FitHelper helper;

    public FitWindowsRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FitWindowsRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            helper = new FitHelper(this, attrs, new FitHelper.FitWindowsProxy() {
                @Override
                public boolean fitSystemWindowsProxy(Rect insets, boolean callSuper) {
                    return callSuper ?
                            FitWindowsRecyclerView.super.fitSystemWindows(insets)
                            : fitSystemWindows(insets);
                }
            });
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (helper != null && child != null) {
            // 待优化，因child会频繁删除添加，当第二次添加时对比是否已经分发。
            if (child.getTag(R.id.tag_need_fit_child) == Boolean.TRUE) {
                helper.fitInsetsChildView(child);
            }
        }
    }

    @Override
    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
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
