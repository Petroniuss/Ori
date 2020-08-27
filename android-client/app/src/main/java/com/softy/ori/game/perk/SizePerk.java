package com.softy.ori.game.perk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.softy.ori.R;
import com.softy.ori.game.Constants;
import com.softy.ori.game.player.Player;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 12.09.2019 </i>
 */
public class SizePerk extends PerkObject {

    private Drawable skin;
    private Paint paint;
    private boolean applied = false;

    public SizePerk(Context context) {
        super(context, randomPosition());

        initView(context);
    }

    @Override
    public void update() {
        boolean wasVisible = visible;

        if (isVisible() || wasVisible) {
            calcRelativePosition();
            skin.setBounds(calcBounds(Constants.PERK_SIZE, 1.0));
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

        skin = context.getDrawable(R.drawable.ic_health_care);
        skin.setBounds(calcBounds(Constants.PERK_SIZE, 0.75));
    }

    @Override
    public void apply(Player player) {
        player.setRadius(player.getRadius() + 10);

        applied = true;
    }

    @Override
    public void attach(Player player) {

    }

    @Override
    public boolean isActive() {
        return !applied;
    }
}
