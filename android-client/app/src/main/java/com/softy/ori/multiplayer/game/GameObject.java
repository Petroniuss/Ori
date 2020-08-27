package com.softy.ori.multiplayer.game;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import com.softy.ori.util.Vector;

import java.util.Objects;

public abstract class GameObject extends View {

    protected Vector absolutePosition;
    protected Vector relativePosition;
    protected String id;

    protected boolean visible = false;

    public GameObject(Context ctx, String id, Vector initialAbsolutePosition) {
        super(ctx);

        this.id = id;
        this.absolutePosition = initialAbsolutePosition;

        calcRelativePosition();
    }

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

    public String id() {
        return id;
    }

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

    protected Rect calcBounds(double radius, double factor) {
        double phi = scale(factor * radius);

        return new Rect(
                (int) (relativePosition.x - phi),
                (int) (relativePosition.y - phi),
                (int) (relativePosition.x + phi),
                (int) (relativePosition.y + phi));
    }

    protected static double scale(double v) {
        return Grid.getInstance().scale(v);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof GameObject))
            return false;

        final GameObject that = (GameObject) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
