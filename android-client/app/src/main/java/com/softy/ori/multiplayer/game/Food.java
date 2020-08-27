package com.softy.ori.multiplayer.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.softy.ori.multiplayer.model.FoodMessage;
import com.softy.ori.util.Vector;

@SuppressLint("ViewConstructor")
public class Food extends GameObject {

    private final double radius;
    private final Paint paint;

    private Food(Context context, String id, Vector position, double radius, int color) {
        super(context, id, position);

        this.radius = radius;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        init(color);
    }

    private void init(int color) {
        paint.setColor(color);
    }

    public static Food fromFoodMessage(FoodMessage message, Context context) {
        final String foodId = String.valueOf(message.getId());
        final Vector foodPosition = message.getPosition();
        final double foodRadius = message.getRadius();
        final int foodColor = message.getColor();

        return new Food(context, foodId, foodPosition, foodRadius, foodColor);
    }

    public void update(FoodMessage message) {
        this.absolutePosition = message.getPosition();
        paint.setColor(message.getColor());
    }

    public void update() {
        calcRelativePosition();
    }

    public void drawItself(Canvas canvas) {
        canvas.drawCircle((float) relativePosition.x, (float) relativePosition.y, (float) scale(radius), paint);
    }

}
