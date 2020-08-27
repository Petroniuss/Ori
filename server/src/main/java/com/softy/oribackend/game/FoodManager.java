package com.softy.oribackend.game;

import com.softy.oribackend.model.GameConstants;
import com.softy.oribackend.util.MathUtils;
import com.softy.oribackend.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class FoodManager {

    private static final int BATCH_SIZE = 1000;

    private final List<List<Food>> foodLists;
    private final int row = GameConstants.BOARD_SIZE / BATCH_SIZE;

    FoodManager() {
        final int size = (int) Math.pow(row, 2);
        this.foodLists = new ArrayList<>(size);

        IntStream.range(0, size).forEach(i -> foodLists.add(new ArrayList<>()));
    }

    void initFood(int N) {
        IntStream.range(0, N)
                .mapToObj(Food::new)
                .forEach(food -> {
                    final int index = calculateFoodListIndex(food.getPosition());
                    foodLists.get(index).add(food);
                });
    }

    List<Food> update(List<Player> players) {
        return players.stream()
                .map(this::updatePlayer)
                .reduce(new ArrayList<>(), (a, b) -> {
                    a.addAll(b);
                    return b;
                });
    }

    private List<Food> updatePlayer(Player player) {
        final var index = calculateFoodListIndex(player.getPosition());

        List<Food> updated = toCheck(index).stream()
                .filter(food -> food.getPosition().distance(player.getPosition()) <= player.getRadius())
                .collect(Collectors.toList());

        final Double deltaR = updated.stream()
                .reduce(0.0, (a, f) -> MathUtils.pythagoras(a, f.getRadius()), Double::sum);

        updated.stream()
                .peek(food -> {
                    final var i = calculateFoodListIndex(food.getPosition());
                    foodLists.get(i).remove(food);
                })
                .peek(Food::relocate)
                .forEach(food -> {
                    final var i = calculateFoodListIndex(food.getPosition());
                    foodLists.get(i).add(food);
                });

        updated.stream()
                .findAny()
                .ifPresent(food -> player.setColor(food.getColor()));

        player.grow(deltaR);

        return updated;
    }


    /**
     * Note that we're checking area above, below, right and left with respect to player
     * This works fine but to make sure all the food is collected we could also check areas that are on the diagonal.
     */
    private List<Food> toCheck(int index) {
        int upper = index - row;
        int bottom = index + row;
        int left = index - 1;
        int right = index + 1;

        final var toCheck = new ArrayList<>(foodLists.get(index));

        if (upper >= 0)
            toCheck.addAll(foodLists.get(upper));
        if (bottom < row * row)
            toCheck.addAll(foodLists.get(bottom));
        if (left >= 0 && left % row < row - 1)
            toCheck.addAll(foodLists.get(left));
        if (right < row * row && right % row > 0)
            toCheck.addAll(foodLists.get(right));

        return toCheck;
    }

    private int calculateFoodListIndex(Vector v) {
        int x = (int) (v.x / BATCH_SIZE);
        int y = (int) (v.y / BATCH_SIZE);

        x = MathUtils.between(0, x, row - 1);
        y = MathUtils.between(0, y, row - 1);

        return x + (y * row);
    }

    List<Food> getFoodList() {
        return foodLists.stream()
                .reduce(new ArrayList<>(GameConstants.FOOD_COUNT), (a, b) -> {
                    a.addAll(b);
                    return a;
                });
    }

}
