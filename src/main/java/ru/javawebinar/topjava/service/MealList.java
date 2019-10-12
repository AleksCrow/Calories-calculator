package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealList implements MealDao {

    private AtomicLong id = new AtomicLong();
    private static final MealList mealList = new MealList();
    private ConcurrentMap<Long, Meal> mealMap = new ConcurrentHashMap<>();

    public static MealList getInstance() {
        return mealList;
    }

    private MealList() {
        Meal meal = meal(LocalDateTime.of(2019, Month.MAY, 28, 10, 0), "Завтрак", 500);
        mealMap.put(meal.getId(), meal);
        meal = meal(LocalDateTime.of(2019, Month.MAY, 28, 10, 0), "Завтрак", 500);
        mealMap.put(meal.getId(), meal);
        meal = meal(LocalDateTime.of(2019, Month.MAY, 28, 13, 0), "Обед", 1000);
        mealMap.put(meal.getId(), meal);
        meal = meal(LocalDateTime.of(2019, Month.MAY, 28, 20, 0), "Ужин", 500);
        mealMap.put(meal.getId(), meal);
        meal = meal(LocalDateTime.of(2019, Month.MAY, 29, 9, 0), "Завтрак", 500);
        mealMap.put(meal.getId(), meal);
        meal = meal(LocalDateTime.of(2019, Month.MAY, 29, 12, 0), "Обед", 1000);
        mealMap.put(meal.getId(), meal);
        meal = meal(LocalDateTime.of(2019, Month.MAY, 29, 21, 0), "Ужин", 600);
        mealMap.put(meal.getId(), meal);
        meal = meal(LocalDateTime.of(2019, Month.MAY, 30, 7, 0), "Завтрак", 500);
        mealMap.put(meal.getId(), meal);
        meal = meal( LocalDateTime.of(2019, Month.MAY, 30, 13, 0), "Обед", 1000);
        mealMap.put(meal.getId(), meal);
    }

    private Meal meal(LocalDateTime dateTime, String description, int calories) {
        Meal meal = new Meal(dateTime, description, calories);
        meal.setId(id.getAndIncrement());
        return meal;
    }

    @Override
    public Meal findMealById(Long id) {
        return mealMap.get(id);
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(id.getAndIncrement());
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealMap.computeIfPresent(meal.getId(), (key, oldMeal) ->  oldMeal = meal);
    }

    @Override
    public void deleteMeal(Long id) {
        mealMap.remove(id);
    }

    @Override
    public Collection<Meal> findAllMeals() {
        return mealMap.values();
    }
}
