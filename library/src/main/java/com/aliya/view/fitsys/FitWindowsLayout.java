package com.aliya.view.fitsys;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;

/**
 * 专门针对Fragment适配的Layout，Fragment要依附此布局
 *
 * @author a_liYa
 * @date 2017/7/29 16:46.
 */
public class FitWindowsLayout extends FrameLayout implements FitHelper.FitWindowsProxy {

    private FitHelper helper;

    public FitWindowsLayout(Context context) {
        this(context, null);
    }

    public FitWindowsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FitWindowsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            helper = new FitHelper(context, attrs, this);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (helper != null) {
            helper.fitChildView(child);
        }
    }

    @Override
    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
        if (helper != null) {
            helper.setWindowInsets(insets);
        }
        return super.dispatchApplyWindowInsets(insets);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        if (helper != null) {
            helper.setRectInsets(insets);
        }
        return super.fitSystemWindows(insets);
    }

    @Override
    public boolean fitSuperSystemWindows(Rect insets) {
        return super.fitSystemWindows(insets);
    }

}
