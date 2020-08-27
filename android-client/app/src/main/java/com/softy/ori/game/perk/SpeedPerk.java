package com.softy.ori.game.perk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.softy.ori.R;
import com.softy.ori.game.Constants;
import com.softy.ori.game.controller.GameEngine;
import com.softy.ori.game.player.Player;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 12.09.2019 </i>
 */
public class SpeedPerk extends PerkObject {

    private int framesLeft = GameEngine.FPS * 10; // 10 seconds

    private Drawable skin;
    private Paint paint;

    public SpeedPerk(Context context) {
        super(context, randomPosition());

        initView(context);
    }

    @Override
    public void update() {
        boolean wasVisible = visible;

        if (isVisible() || wasVisible) {
            calcRelativePosition();
            skin.setBounds(calcBounds(Constants.PERK_SIZE, .8));
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isVisible()) {
            canvas.drawCircle((float) relativePosition.x, (float) relativePosition.y, (float) scale(Constants.PERK_SIZE), paint);
            skin.draw(canvas);
        }

        super.onDraw(canvas);
    }

    @Override
    protected void initView(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(ResourcesCompat.getColor(getResources(), R.color.orange, context.getTheme()));

        skin = context.getDrawable(R.drawable.ic_runner);
        skin.setBounds(calcBounds(Constants.PERK_SIZE, .75));
    }

    @Override
    public void apply(Player player) {
        if (framesLeft == 1 && player.getSpeed() > Constants.INIT_SPEED)
            player.setSpeed(player.getSpeed() / 2);
        else if(player.getSpeed() < 3 * Constants.INIT_SPEED)
            player.setSpeed(player.getSpeed() * 2);

        framesLeft--;
    }

    @Override
    public void attach(Player player) {

    }

    @Override
    public boolean isActive() {
        return framesLeft > 0;
    }
}
