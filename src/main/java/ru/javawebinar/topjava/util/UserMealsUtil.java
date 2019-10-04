package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        //map with calculate calories in some day
        Map<LocalDate, Integer> sumOfCaloriesFromDay = new HashMap<>();
        for (UserMeal meal : mealList) {
            sumOfCaloriesFromDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        //filtered list of user meal during some time with exceed
        List<UserMealWithExceed> filteredMealList = new ArrayList<>();
        for (UserMeal meal : mealList){
            if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredMealList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        sumOfCaloriesFromDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) > caloriesPerDay));
            }
        }
        return filteredMealList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOption1(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        //map with calculate calories in some day
        Map<LocalDate, Integer> sumOfCaloriesFromDay = mealList.stream()
                .collect(Collectors.toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        //filtered list of user meal during some time with exceed
        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                        sumOfCaloriesFromDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    //with stream api, but not what is required
    public static List<UserMealWithExceed> getFilteredWithExceededOption2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return mealList.stream()
                .filter(x -> TimeUtil.isBetween(x.getDateTime().toLocalTime(), startTime, endTime))
                .map(x -> new UserMealWithExceed(x.getDateTime(), x.getDescription(), x.getCalories(),
                        //filter with calculate calories in some days and boolean result for exceed
                        mealList.stream().filter(y -> y.getDateTime().toLocalDate().equals(x.getDateTime().toLocalDate())).mapToInt(UserMeal::getCalories).sum() > caloriesPerDay))
                .collect(Collectors.toList());
    }

}
