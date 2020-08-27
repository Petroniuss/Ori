package com.softy.ori.multiplayer.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.res.ResourcesCompat;

import com.softy.ori.R;
import com.softy.ori.customization.Skin;
import com.softy.ori.customization.SkinProvider;
import com.softy.ori.game.Constants;
import com.softy.ori.multiplayer.model.PlayerMessage;
import com.softy.ori.util.Vector;

@SuppressLint("ViewConstructor")
public class Player extends GameObject {

    private final String name;
    private double radius;

    private final Paint circlePaint;
    private final Paint textPaint;
    private Vector direction = Vector.RIGHT;

    private final Tail tail;
    private final Skin skin;

    private Player(Context context, Vector position, String name, String id, double radius, int color, Skin skin) {
        super(context, id, position);

        this.name = name;
        this.radius = radius;
        this.skin = skin;
        this.circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.tail = new Tail(context);

        init(context, color);
    }

    public static Player fromMessage(PlayerMessage msg, Context context) {
        final String playerName = msg.getName();
        final String playerId = msg.getId();
        final Vector playerPosition = msg.getPosition();
        final Skin skin = SkinProvider.getInstance(context).fromName(msg.getSkinName());
        final double playerRadius = msg.getRadius();
        final int color = msg.getColor();

        return new Player(context, playerPosition, playerName, playerId, playerRadius, color, skin);
    }

    private void init(Context context, int color) {
        GameViewController.getInstance().attachView(tail);

        circlePaint.setColor(color);
        textPaint.setColor(0xffffffff);
        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.luckiest_guy));
        textPaint.setTextAlign(Paint.Align.CENTER);
        skin.setBounds(relativePosition, scale(radius));

        adjustTextSize();
    }

    public void update(PlayerMessage message) {
        this.radius = message.getRadius();
        this.absolutePosition = message.getPosition();
        this.direction = message.getDirection();
        changeColor(message.getColor());
    }

    public void update() {
        adjustTextSize();
        calcRelativePosition();
        skin.setBounds(relativePosition, scale(radius));
        postInvalidate();

        if (radius > 100)
            tail.updateSize(32);
        if (radius > 250)
            tail.updateSize(16);
        if (radius > 500)
            tail.updateSize(8);

        tail.onBeforeDraw(relativePosition, Grid.getInstance().dV().apply(Player::scale), direction, scale(radius));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle((float) relativePosition.x,
                (float) relativePosition.y, (float) (scale(radius)), circlePaint);
        skin.drawable().draw(canvas);
        canvas.drawText(String.valueOf((int) radius), (float) relativePosition.x,
                (float) (relativePosition.y - scale(radius) - (4 * textPaint.descent())), textPaint);

        canvas.drawText(name,
                (float) relativePosition.x, (float) (relativePosition.y - scale(radius) - (textPaint.descent() / 4)), textPaint);

    }

    public void changeColor(int color) {
        circlePaint.setColor(color);
        tail.setColor(color);
    }

    public void onDeath() {
        tail.destroy();
    }

    private void adjustTextSize() {
        int spSize = (int) Math.sqrt(radius * 2) + 8;
        float textSize = (float) (scale(spSize * getContext().getResources().getDisplayMetrics().scaledDensity));

        textPaint.setTextSize(textSize);
    }

    //-----------------------Getters&&Setters-------------------

    public double getRadius() {
        return radius;
    }

    //Fixme
    public double getMagnitude() {
        return radius / Constants.PLAYER_INIT_RADIUS;
    }

    public String getName() {
        return name;
    }
}
