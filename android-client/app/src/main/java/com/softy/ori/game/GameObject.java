package com.softy.ori.game;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import com.softy.ori.game.controller.Grid;
import com.softy.ori.util.Vector;

import java.util.Random;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 08.09.2019 </i>
 */
public abstract class GameObject extends View {

    protected final static Random random = new Random();

    protected Vector absolutePosition;
    protected Vector relativePosition;
    protected boolean visible = false;

    public GameObject(Context ctx) {
        super(ctx);
    }

    public GameObject(Context ctx, Vector initialAbsolutePosition) {
        this(ctx);

        this.absolutePosition = initialAbsolutePosition;
        calcRelativePosition();
    }

    /**
     * Should be called {@link com.softy.ori.game.controller.GameEngine} on every frame.
     */
    public abstract void update();

    /**
     * Should be called from a constructor.
     * <br>
     * Code that manages view code shouldn't be in the constructor!
     */
    protected abstract void initView(Context context);

    /**
     * Note that it also caches calculated position.
     */
    protected void calcRelativePosition() {
        relativePosition = Grid.getInstance().calcDimensions(absolutePosition);
    }

    /**
     * @return whether this is within the screen
     */
    public boolean isVisible() {
        visible = Grid.getInstance().visible(absolutePosition);

        return visible;
    }

    //-------------------------Getters----------------------------------

    public Vector getAbsolutePosition() {
        return absolutePosition;
    }

    public double getAbsoluteX() {
        return absolutePosition.x;
    }

    public double getAbsoluteY() {
        return absolutePosition.y;
    }

    //--------------------------Util-----------------------------------

    public boolean withinCircle(Vector center, double radius) {
        double distance = absolutePosition.distance(center);

        return distance < radius;
    }

    protected Rect calcBounds(double radius, double factor) {
        double phi = scale(factor * radius);

        return new Rect(
                (int) (relativePosition.x - phi),
                (int) (relativePosition.y - phi),
                (int) (relativePosition.x + phi),
                (int) (relativePosition.y + phi));
    }

    protected static Vector randomPosition() {
        return Grid.randomPosition();
    }

    protected static double scale(double v) {
        return Grid.getInstance().scale(v);
    }

}
