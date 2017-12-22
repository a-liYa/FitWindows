package com.aliya.view.fitsys;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;

/**
 * fitSystemWindow - 助手
 *
 * @author a_liYa
 * @date 2017/7/19 11:16.
 */
public class FitHelper {

    public static final int STATUS_BOTH = 0;
    public static final int STATUS_TOP = 1;
    public static final int STATUS_BOTTOM = 2;

    private int fitType = STATUS_BOTH;

    Rect rectInsets;
    Object windowInsets;
    FitWindowsProxy mProxy;

    @Deprecated
    public FitHelper(Context context, AttributeSet attrs) {
        this(context, attrs, null);
    }

    public FitHelper(Context context, AttributeSet attrs, FitWindowsProxy proxy) {
        mProxy = proxy;
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FitSystemWindow);
            fitType = a.getInt(R.styleable.FitSystemWindow_fitType, STATUS_BOTH);
            a.recycle();
        }
    }

    public void setRectInsets(Rect insets) {
        if (rectInsets == null || !rectInsets.equals(insets)) {
            rectInsets = new Rect(insets);
        }
    }

    public void setWindowInsets(Object insets) {
        this.windowInsets = insets;
    }

    public boolean fitSystemWindows(Rect insets) {
        setRectInsets(insets);
        if (mProxy == null) return false;

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (mProxy.getFitsSystemWindows()) {
                mProxy.setFitsSystemWindows(false);
                int left = insets.left;
                int top = insets.top;
                int right = insets.right;
                int bottom = insets.bottom;
                mProxy.fitSuperSystemWindows(insets);
                insets.set(left, top, right, bottom);
                mProxy.setFitsSystemWindows(true);
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (mProxy.getFitsSystemWindows()) {
                insets = fitInsets(insets);
            }
        }
        return false & mProxy.fitSuperSystemWindows(insets);
    }

    public Rect fitInsets(Rect insets) {
        if (insets != null) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                insets = new Rect(insets);
            }
            switch (fitType) {
                case STATUS_TOP:
                    insets.set(insets.left, insets.top, insets.right, 0);
                    break;
                case STATUS_BOTTOM:
                    insets.set(insets.left, 0, insets.right, insets.bottom);
                    break;
            }
        }
        return insets;
    }

    public void fitChildView(View child) {
        if (child == null || mProxy == null) return;
        // 若系统已分发过，需要手动分发给子View
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (rectInsets != null) {
                mProxy.fitSuperSystemWindows(new Rect(rectInsets));
            }
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            if (windowInsets != null && windowInsets instanceof WindowInsets) {
                child.dispatchApplyWindowInsets((WindowInsets) windowInsets);
            }
        }
    }

    public interface FitWindowsProxy {

        boolean fitSuperSystemWindows(Rect insets);

        void setFitsSystemWindows(boolean fitSystemWindows);

        boolean getFitsSystemWindows();

    }

}
