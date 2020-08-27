package com.softy.ori.customization;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.softy.ori.util.Vector;

public class Skin {

    private final Drawable drawable;
    private final double factor;
    private final String name;
    private SkinDrawable skinDrawable;

    private Skin(int resId, double factor, String name, Context context) {
        this.drawable = context.getDrawable(resId);
        this.factor = factor;
        this.name = name;
        this.skinDrawable = SkinDrawable.valueOf(name);
    }

    public static Skin create(SkinDrawable skinDrawable, Context context) {
        return new Skin(skinDrawable.resId, skinDrawable.scaleFactor, skinDrawable.name(), context);
    }

    public Drawable drawable() {
        return drawable;
    }

    public void setBounds(Vector relativeCenter, double a) {
        a *= factor;

        final int left = (int) (relativeCenter.x - a);
        final int top = (int) (relativeCenter.y - a);
        final int right = (int) (relativeCenter.x + a);
        final int bottom = (int) (relativeCenter.y + a);

        final Rect bounds = new Rect(left, top, right, bottom);

        drawable.setBounds(bounds);
    }

    public SkinDrawable getSkinDrawable() {
        return skinDrawable;
    }

    public String getName() {
        return name;
    }

}
