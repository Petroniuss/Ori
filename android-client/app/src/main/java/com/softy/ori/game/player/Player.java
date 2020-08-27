package com.softy.ori.game.player;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.softy.ori.R;
import com.softy.ori.customization.Config;
import com.softy.ori.customization.SkinDrawable;
import com.softy.ori.game.Constants;
import com.softy.ori.game.GameObject;
import com.softy.ori.game.controller.GameViewController;
import com.softy.ori.game.perk.Perk;
import com.softy.ori.util.Vector;

import java.util.LinkedList;
import java.util.List;


/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 05.09.2019 </i>
 */
public abstract class Player extends GameObject {

    protected Vector direction = Vector.UP;
    protected String name;
    protected double radius = Constants.PLAYER_INIT_RADIUS;
    protected double speed = Constants.INIT_SPEED;
    protected List<Perk> perks;

    protected Paint circlePaint;
    protected Paint textPaint;

    protected Tail tail;
    protected Drawable skin;

    public Player(Context context, Vector initPosition, String name) {
        super(context, initPosition);

        this.name = name;
        this.perks = new LinkedList<>();

        calcRelativePosition();
    }

    @Override
    protected void initView(Context context) {
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(ResourcesCompat.getColor(getResources(), R.color.player, context.getTheme()));

        textPaint = new Paint();
        textPaint.setColor(0xffffffff);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.luckiest_guy));
        textPaint.setTextAlign(Paint.Align.CENTER);

        skin = context.getDrawable(SkinDrawable.valueOf(Config.getInstance(context).getSkinName()).resId);
        tail = new Tail(context);

        GameViewController.getInstance().attachView(tail);

        adjustTextSize();
        setBoundsOnDrawable();
    }

    @Override
    public void update() {
        adjustSpeed();
        perks.removeIf(perk -> !perk.isActive());
        perks.forEach(perk -> perk.apply(this));

        bounce();
    }

    public void changeColor(int color) {
        circlePaint.setColor(color);
        if (tail != null)
            tail.setColor(color);
    }

    public void grow(double r) {
        radius = Math.sqrt(Math.pow(r / 2, 2) + Math.pow(radius, 2));

        adjustTextSize();
        setBoundsOnDrawable();
        adjustTailSize();
    }

    public void onDeath() {
        if (tail != null)
            tail.destroy();
    }

    //-----------------------Utility---------------------------\\

    public boolean intersects(GameObject o) {
        double distance = absolutePosition.distance(o.getAbsolutePosition());

        return radius >= distance;
    }

    protected void setBoundsOnDrawable() {
        skin.setBounds(calcBounds(radius, 1.0));
    }

    protected void bounce() {
        if (absolutePosition.x <= 0)
            direction = Vector.RIGHT;
        else if (absolutePosition.y <= 0)
            direction = Vector.DOWN;
        else if (absolutePosition.x >= Constants.WIDTH)
            direction = Vector.LEFT;
        else if (absolutePosition.y >= Constants.HEIGHT)
            direction = Vector.UP;
    }

    protected void adjustTextSize() {
        int spSize = (int) Math.sqrt(radius) + 12;
        textPaint.setTextSize((float) (scale(spSize * getContext().getResources().getDisplayMetrics().scaledDensity)));
    }

    private void adjustTailSize() {
        /*
        if (radius > 150)
            tail.updateSize(50);
        else if (radius > 350)
            tail.updateSize(15);
        else if (radius > 500)
            tail.updateSize(5);
        */
    }

    private void adjustSpeed() {
        /*
        if (radius < 100) {
            speed = Constants.INIT_SPEED * 3/2;
        } else if (radius < 200) {
            speed = Constants.INIT_SPEED;
        } else
            speed = Constants.INIT_SPEED * 4/5;
        */
    }

    //-----------------------Getters&&Setters-------------------

    public void addPerk(Perk perk) {
        perks.add(perk);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double newSpeed) {
        speed = newSpeed;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public double getMagnitude() {
        return radius / Constants.PLAYER_INIT_RADIUS;
    }

    public String getName() {
        return name;
    }
}
