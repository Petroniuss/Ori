package com.softy.ori.multiplayer.game;

import com.softy.ori.game.Constants;
import com.softy.ori.game.GameObject;
import com.softy.ori.util.Vector;

import java.util.Random;

/**
 * This class is responsible for scaling and calculating coordinates for {@link GameObject} on the canvas.
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 08.09.2019 </i>
 */
public class Grid {

    private static final Random random = new Random();
    private static Grid instance;

    public int screenWidth;
    public int screenHeight;

    private double scale = 1.0; // >= 1 (zooming out)
    private Vector center; // absolute

    //-----------Optimization------------------------------

    private Vector scaledTopLeft;
    private Vector scaledBottomRight;
    private Vector dV;

    //-----------------Constructors------------------------

    private Grid(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.center = absoluteCenter();

        update(center, 1.0);
    }

    public static Grid init(int screenWidth, int screenHeight) {
        instance = new Grid(screenWidth, screenHeight);

        return instance;
    }

    public static Grid getInstance() {
        if (instance == null)
            throw new AssertionError("Grid should be initialized first!");

        return instance;
    }

    /**
     * @param ratio under normal circumstances should be <code> player.radius / player.initRadius </code>
     * @param center absolute position of player
     */
    public void update(Vector center, double ratio) {
        updateCenter(center);
        updateScale(ratio);
    }

    /**
     * @param center All views will be drawn relative to this point
     */
    private void updateCenter(Vector center) {
        dV = center.subtract(this.center);
        this.center = center;
    }

    /**
     * @param ratio player.radius / initRadius
     */
    private void updateScale(double ratio) {
        scale = 0.25 + Math.sqrt(ratio);

        final Vector scaledScreen = Vector.create(screenWidth, screenHeight)
                .apply(d -> d * scale)
                .apply(d -> d / 2);

        scaledTopLeft = center.apply((a, b) -> a - b, scaledScreen);
        scaledBottomRight = center.apply(Double::sum, scaledScreen);
    }

    //------------------Methods-for-GameObject--------------------

    /**
     * @param abs absolute position of the view
     */
    public boolean visible(Vector abs) {
        return abs.x >= scaledTopLeft.x && abs.y >= scaledTopLeft.y &&
                abs.x <= scaledBottomRight.x && abs.y <= scaledBottomRight.y;
    }

    /**
     * @param abs absolute position of the view
     * @return relative (to the canvas) position
     */
    public Vector calcDimensions(Vector abs) {
        final Vector scaledRelative = abs.apply((a, b) -> a - b, scaledTopLeft);

        return scaledRelative.apply(x -> x / scale);
    }

    /**
     * @param v - value to be scaled (eg. radius)
     * @return scaled value.
     */
    public double scale(double v) {
        return v / scale;
    }

    //-------------------------Some-utility-methods---------------------

    public Vector relativeCenter() {
        return Vector.create(screenWidth, screenHeight).apply(v -> v / 2);
    }

    /**
     * @return vector representing screen update.
     */
    public Vector dV() {
        return dV;
    }

    public static Vector absoluteCenter() {
        return Vector.create(Constants.WIDTH, Constants.HEIGHT).apply(v -> v / 2);
    }

    public static Vector randomPosition() {
        return Vector.create(
                random.nextInt(Constants.WIDTH),
                random.nextInt(Constants.HEIGHT));
    }

}
