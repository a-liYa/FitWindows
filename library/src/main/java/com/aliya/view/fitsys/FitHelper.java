package com.aliya.view.fitsys;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowInsets;

import java.lang.reflect.Method;

/**
 * fitSystemWindow - 助手
 * <p>
 * 1. 可以分别适配状态栏和导航栏, 通过自定属性fitType来设置:
 * <code>
 * app:fitType="both"      // 默认属性
 * app:fitType="top"       // fit仅适配状态栏
 * app:fitType="bottom"    // fit仅适配导航栏
 * </code>
 * </p>
 * 2. 忽略 fitSystemWindows 的事件消费:
 * <code>
 * app:fitSystemWindows="true"  // 默认false
 * </code>
 * <p>
 * 3. 分发给未来添加的子View:
 * <code>
 * app:fitFutureChild="true"    // 默认false
 * </code>
 * </p>
 *
 * @author a_liYa
 * @date 2017/7/19 11:16.
 */
public class FitHelper {

    public static final int STATUS_BOTH = 0;
    public static final int STATUS_TOP = 1;
    public static final int STATUS_BOTTOM = 2;

    int fitType = STATUS_BOTH;
    boolean fitIgnoreConsume;   // 是否忽略 fitSystemWindows 的事件消费, 默认 false
    boolean fitFutureChild;     // 是否分发给未来添加的子View, 默认 false

    Rect rectInsets;
    Object windowInsets;
    FitWindowsProxy mProxy;

    @Nullable
    View mTarget;

    public FitHelper(View view, AttributeSet attrs, FitWindowsProxy proxy) {
        mProxy = proxy;
        mTarget = view;
        if (attrs != null) {
            TypedArray a = mTarget.getContext()
                    .obtainStyledAttributes(attrs, R.styleable.FitSystemWindow);
            fitType = a.getInt(R.styleable.FitSystemWindow_fitType, STATUS_BOTH);
            fitIgnoreConsume = a.getBoolean(R.styleable.FitSystemWindow_fitIgnoreConsume, false);
            fitFutureChild = a.getBoolean(R.styleable.FitSystemWindow_fitFutureChild, false);
            a.recycle();
        }
    }

    private void setRectInsets(Rect insets) {
        if (rectInsets == null) {
            rectInsets = new Rect(insets);
        } else if (!rectInsets.equals(insets)) {
            rectInsets.set(insets);
        }
    }

    public void setWindowInsets(Object insets) {
        this.windowInsets = insets;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public boolean fitSystemWindows(Rect insets) {
        setRectInsets(insets);

        // 4.4 系统兼容 fitIgnoreConsume 属性的处理逻辑
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            if (mTarget.getFitsSystemWindows() && fitIgnoreConsume) {
                mTarget.setFitsSystemWindows(false);
                final int left = insets.left;
                final int top = insets.top;
                final int right = insets.right;
                final int bottom = insets.bottom;
                mProxy.fitSystemWindowsProxy(insets, true);
                insets.set(left, top, right, bottom);
                mTarget.setFitsSystemWindows(true);
            }
        }

        if (mTarget.getFitsSystemWindows()) {
            insets = fitInsets(insets);
        }
        return !fitIgnoreConsume & mProxy.fitSystemWindowsProxy(insets, true);
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

    /**
     * dispatch WindowInsets to child view.
     *
     * @param child child view.
     * @see ViewCompat#requestApplyInsets(View)
     */
    public void fitInsetsChildView(View child) {
        if (!fitFutureChild || child == null || mProxy == null) return;

        // 若系统已分发过，需要手动分发给子View
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            dispatchApplyWindowInsetsForKitkat(child);
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            dispatchApplyWindowInsets(child);
        }
    }

    private void dispatchApplyWindowInsetsForKitkat(View child) {
        if (rectInsets != null) {
            try {
                Method method = View.class.getDeclaredMethod("fitSystemWindows", Rect.class);
                method.setAccessible(true);
                method.invoke(child, new Rect(rectInsets));
            } catch (Exception e) { // no-op
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void dispatchApplyWindowInsets(View child) {
        if (windowInsets != null && windowInsets instanceof WindowInsets) {
            child.dispatchApplyWindowInsets((WindowInsets) windowInsets);
        }
    }

    public interface FitWindowsProxy {

        boolean fitSystemWindowsProxy(Rect insets, boolean callSuper);
    }
}
