package com.aliya.view.fitsys;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;

/**
 * fitSystemWindow - 助手
 *
 * @author a_liYa
 * @date 2017/7/19 11:16.
 */
public class FitHelper {

    private int fitType = STATUS_BOTH;

    public static final int STATUS_BOTH = 0;
    public static final int STATUS_TOP = 1;
    public static final int STATUS_BOTTOM = 2;

    public FitHelper(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FitSystemWindow);
            fitType = a.getInt(R.styleable.FitSystemWindow_fitType, STATUS_BOTH);
            a.recycle();
        }
    }

    public Rect fitSystemWindows(Rect insets) {
        if (insets != null) {
            if (fitType != STATUS_BOTTOM && Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
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

}
