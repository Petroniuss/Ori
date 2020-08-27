package com.softy.ori.multiplayer.game;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.softy.ori.multiplayer.model.FoodMessage;
import com.softy.ori.multiplayer.model.GameState;

import java.util.List;
import java.util.stream.Collectors;

@SuppressLint("ViewConstructor")
public class FoodManager extends View {

    private List<Food> foodList;

    public FoodManager(Context context, GameState gameState) {
        super(context);

        this.foodList = gameState.getFoodList()
                .stream()
                .map(msg -> Food.fromFoodMessage(msg, context))
                .collect(Collectors.toList());
    }

    public void update(List<FoodMessage> updated) {
        updated.forEach(msg -> {
            final Food mapped = mapMessage(msg);
            mapped.update(msg);
        });

        foodList.stream()
                .filter(GameObject::isVisible)
                .forEach(Food::update);

        postInvalidate();
    }

    @SuppressLint("WrongCall")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        foodList.stream()
                .filter(GameObject::isVisible)
                .forEach(food -> food.drawItself(canvas));
    }

    private Food mapMessage(FoodMessage msg) {
        return foodList.stream()
                .filter(food -> Integer.parseInt(food.id()) == msg.getId())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Food [id: " + msg.getId() + "] not found"));
    }

}
