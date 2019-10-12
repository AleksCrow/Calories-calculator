package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealDao {

    Meal findMealById(Long id);

    void addMeal(Meal meal);

    void updateMeal(Meal meal);

    void deleteMeal(Long id);

    Collection<Meal> findAllMeals();
}
