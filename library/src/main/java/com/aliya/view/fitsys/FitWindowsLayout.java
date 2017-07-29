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
public class FitWindowsLayout extends FrameLayout {

    WindowInsets windowInsets;
    Rect rectInsets;

    public FitWindowsLayout(Context context) {
        super(context);
    }

    public FitWindowsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FitWindowsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (windowInsets != null || rectInsets != null) { // 系统已分发过，此时需要手动分发给子View
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                if (rectInsets != null) {
                    super.fitSystemWindows(rectInsets);
                }
            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                if (windowInsets != null) {
                    child.dispatchApplyWindowInsets(windowInsets);
                }
            }
        }
    }

    @Override
    public WindowInsets dispatchApplyWindowInsets(WindowInsets insets) {
        windowInsets = insets;
        return super.dispatchApplyWindowInsets(insets);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        rectInsets = insets;
        return super.fitSystemWindows(insets);
    }
}
