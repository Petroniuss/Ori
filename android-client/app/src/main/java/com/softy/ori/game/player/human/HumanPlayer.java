package com.softy.ori.game.player.human;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;

import com.softy.ori.game.controller.Grid;
import com.softy.ori.game.controller.OnDirectionChangedListener;
import com.softy.ori.game.player.Player;
import com.softy.ori.util.Vector;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 12.09.2019 </i>
 */
@SuppressLint("ViewConstructor")
public class HumanPlayer extends Player {

    public HumanPlayer(Context context, OnDirectionChangedListener listener, Vector initPosition, String name) {
        super(context, initPosition, name);
        this.relativePosition = Grid.getInstance().relativeCenter();

        listener.setOnDirectionChanged(vector -> direction = vector);

        initView(context);
    }

    @Override
    public void update() {
        super.update();

        final Vector v = direction.apply(d -> d * speed);

        absolutePosition = absolutePosition.apply(Double::sum, v);
        tail.onBeforeDraw(relativePosition, v.apply(Player::scale), scale(radius), true);

        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle((float) relativePosition.x,(float) relativePosition.y,
                (float) (scale(radius)), circlePaint);

        skin.draw(canvas);

        canvas.drawText(name, (float) relativePosition.x, (float) (relativePosition.y - scale(radius) - (textPaint.descent() / 2)), textPaint);
        canvas.drawText(String.valueOf((int) radius),
                (float) relativePosition.x, (float) relativePosition.y - (textPaint.ascent() / 2), textPaint);

        super.onDraw(canvas);
    }

}
