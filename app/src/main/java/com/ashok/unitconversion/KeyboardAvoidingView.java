package com.ashok.unitconversion;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class KeyboardAvoidingView extends RelativeLayout {

    public KeyboardAvoidingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            // If height mode is exactly, no need to adjust
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            // If height mode is not exactly, adjust for keyboard
            int keyboardHeight = getKeyboardHeight();
            if (keyboardHeight > 0) {
                int newHeight = MeasureSpec.getSize(heightMeasureSpec) - keyboardHeight;
                int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY);
                super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }

    private int getKeyboardHeight() {
        Rect rect = new Rect();
        getWindowVisibleDisplayFrame(rect);
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int keyboardHeight = screenHeight - rect.bottom;
        if (keyboardHeight > screenHeight / 3) {
            return keyboardHeight;
        } else {
            return 0;
        }
    }
}