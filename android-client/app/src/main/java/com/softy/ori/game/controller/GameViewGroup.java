package com.softy.ori.game.controller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * The whole point of writing custom ViewGroup is to avoid any redundant computations.
 *
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 10.09.2019 </i>
 */
public class GameViewGroup extends ViewGroup {

    public GameViewGroup(Context context) {
        super(context);
    }

    public GameViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int childCount = getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            if(child.getVisibility() != GONE)
                child.layout(left, top, right, bottom);
        }
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    /**
     * Not overriding this method will default to setting size of a background
     * which is correct assumption in our case.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
