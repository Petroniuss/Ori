package com.softy.ori.game.food;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.softy.ori.game.Constants;
import com.softy.ori.game.GameObject;
import com.softy.ori.util.ColorUtils;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 05.09.2019 </i>
 */
public class Food extends GameObject {

    private double radius;
    private Paint circlePaint;

    public Food(Context context) {
        super(context);

        radius = random.nextInt(5) + Constants.FOOD_RADIUS;
        absolutePosition = randomPosition();

        initView(context);
    }

    @Override
    protected void initView(Context context) {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(ColorUtils.rainbowColor());
    }

    @Override
    public void update() {
        calcRelativePosition();
    }

    public void drawItself(Canvas canvas) {
        canvas.drawCircle((float) relativePosition.x, (float) relativePosition.y, (float) (scale(radius)), circlePaint);
    }

    public void relocate() {
        absolutePosition = randomPosition();
    }

    public int getColor() {
        return circlePaint.getColor();
    }

    public double getRadius() {
        return radius;
    }
}
