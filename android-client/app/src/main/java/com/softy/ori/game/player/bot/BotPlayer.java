package com.softy.ori.game.player.bot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;

import com.softy.ori.game.controller.Grid;
import com.softy.ori.game.player.Player;
import com.softy.ori.util.Vector;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 13.09.2019 </i>
 */
@SuppressLint("ViewConstructor")
public class BotPlayer extends Player {

    private final Brain brain;
    private int phi = 0;

    public BotPlayer(Context context, String name, Brain brain) {
        super(context, randomPosition(), name);
        this.brain = brain;

        initView(context);
        tail.updateSize(12);
    }

    @Override
    public void update() {
        super.update();

        if (++phi % 24 == 0) {
            direction = brain.predict(this);
            phi = 0;
        }
        bounce();

        final Vector v = direction.apply(d -> d * speed);

        absolutePosition = absolutePosition.apply(Double::sum, v);
        tail.onBeforeDraw(relativePosition, Grid.getInstance().dV().apply(Player::scale), scale(radius), visible || isVisible());

        if(visible) {
            calcRelativePosition();
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isVisible()) {
            canvas.drawCircle((float) relativePosition.x,(float) relativePosition.y,
                    (float) (scale(radius)), circlePaint);

            canvas.drawText(name, (float) relativePosition.x, (float) (relativePosition.y - scale(radius) - textPaint.descent()), textPaint);
            canvas.drawText(String.valueOf((int) radius),
                    (float) relativePosition.x, (float) relativePosition.y - (textPaint.ascent() / 2),textPaint);
        }

        super.onDraw(canvas);
    }

}
