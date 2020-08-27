package com.softy.ori.game.food;

import com.softy.ori.util.Vector;

import java.util.List;

public interface FoodProvider {

    List<Food> foodWithinRadius(Vector center, double radius);

}
