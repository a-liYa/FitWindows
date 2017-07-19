package com.aliya.view.fitsys;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 自定义拓展FitSystemWindow属性 - FrameLayout.
 * 可以分别适配状态栏和导航栏
 * <p>
 * 通过自定属性fitType来设置:
 * <code>
 * app:fitType="both"      // 默认属性
 * app:fitType="top"       // fit仅适配状态栏
 * app:fitType="bottom"    // fit仅适配导航栏
 * </code>
 *
 * @author a_liYa
 * @date 2017/7/19 上午11:24.
 */
public class FitWindowsFrameLayout extends FrameLayout {

    private FitHelper helper;

    public FitWindowsFrameLayout(Context context) {
        this(context, null);
    }

    public FitWindowsFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FitWindowsFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        helper = new FitHelper(context, attrs);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        return false & super.fitSystemWindows(helper.fitSystemWindows(insets));
    }

}
