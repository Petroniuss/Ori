package com.softy.ori.game.food;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.softy.ori.game.Constants;
import com.softy.ori.game.GameObject;
import com.softy.ori.game.player.Player;
import com.softy.ori.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <br> <br>
 * Created by <b> Patryk Wojtyczek </b> on <i> 11.09.2019 </i>
 */
public class FoodManager extends View implements FoodProvider {

    private static final int COLUMNS = Constants.WIDTH / Constants.FOOD_DISTRICT_SIZE;
    private static final int ROWS = Constants.WIDTH / Constants.FOOD_DISTRICT_SIZE;
    private static final int SIZE = COLUMNS * ROWS;

    private List<List<Food>> foodDistricts;
    private List<Food> foodList;

    public FoodManager(Context context) {
        super(context);

        foodDistricts = new ArrayList<>(SIZE);
        IntStream.range(0, SIZE).forEach(i -> foodDistricts.add(new ArrayList<>()));

        init();
    }

    private void init() {
        IntStream.range(0, Constants.FOOD_COUNT)
                .mapToObj(i -> new Food(getContext()))
                .peek(GameObject::update)
                .forEach(f -> foodDistricts.get(district(f)).add(f));

        foodList = concatFoodDistricts();
    }

    public void update(Collection<? extends Player> players) {
        players.forEach(this::checkForIntersections);

        updateFood();
        postInvalidate();
    }

    //Todo improve this method!
    @Override
    public List<Food> foodWithinRadius(Vector center, double radius) {
        return foodList.stream()
                .filter(food -> food.withinCircle(center, radius))
                .collect(Collectors.toList());
    }

    private void checkForIntersections(Player player) {
        List<Food> filtered = districts(player).stream()
                .map(district -> foodDistricts.get(district))
                .reduce(new ArrayList<>(25), (a, b) -> {
                    a.addAll(b);
                    return a;
                }).stream()
                .filter(player::intersects)
                .collect(Collectors.toList());

        final double deltaR = filtered.stream()
                .mapToDouble(Food::getRadius)
                .reduce(0.0, Double::sum);

        filtered.stream().findAny().ifPresent(food -> {
            player.grow(deltaR);
            player.changeColor(food.getColor());
        });

        filtered.forEach(food -> {
            final int oldDistrict = district(food);
            food.relocate();
            final int newDistrict = district(food);

            foodDistricts.get(oldDistrict).remove(food);
            foodDistricts.get(newDistrict).add(food);
        });
    }

    private int district(Vector position) {
        int x = (int) position.x / Constants.FOOD_DISTRICT_SIZE;
        int y = (int) position.y / Constants.FOOD_DISTRICT_SIZE;

        x = Math.min(Math.max(x, 0), COLUMNS - 1);
        y = Math.min(Math.max(y, 0), ROWS - 1);

        return y * ROWS + x;
    }

    private int district(Food food) {
        return district(food.getAbsolutePosition());
    }

    private void updateFood() {
        foodList.stream()
                .filter(Food::isVisible)
                .forEach(Food::update);
    }

    private Set<Integer> districts(Player player) {
        Set<Integer> districts = new HashSet<>();

        districts.add(district(player.getAbsolutePosition()));

        final Vector center = player.getAbsolutePosition();
        double radius = player.getRadius();

        final Vector up = center.add(Vector.UP.scale(radius));
        final Vector down = center.add(Vector.DOWN.scale(radius));
        final Vector right = center.add(Vector.RIGHT.scale(radius));
        final Vector left = center.add(Vector.LEFT.scale(radius));

        districts.addAll(Arrays.asList(
                district(up),
                district(down),
                district(right),
                district(left)
        ));

        return districts;
    }

    private List<Food> concatFoodDistricts() {
        return foodDistricts.stream()
                .reduce(new ArrayList<>(Constants.FOOD_COUNT), (a, b) -> {
            a.addAll(b);
            return a;
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        foodList.stream()
                .filter(Food::isVisible)
                .forEach(food -> food.drawItself(canvas));

        super.onDraw(canvas);
    }
}
