package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealDao;
import ru.javawebinar.topjava.service.MealList;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private MealDao mealDao = MealList.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List mealsTo = UserMealsUtil.getFilteredWithExcessInOnePass2(mealDao.findAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000);

        request.setAttribute("meals", mealsTo);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("post from meals");
        request.setCharacterEncoding("UTF-8");

        String command = request.getParameter("command");
        String custId = request.getParameter("id");

        switch (command) {
            case "deleteMeal":
                mealDao.deleteMeal(Long.parseLong(custId));
                break;
            case "addMeal":
                Meal meal = getMealFromRequest(request);
                mealDao.addMeal(meal);
                break;
            case "addForm":
                request.getRequestDispatcher("/addForm.jsp").forward(request, response);
                return;
            case "editMeal":
                meal = getMealFromRequest(request);
                meal.setId(Long.parseLong(request.getParameter("id")));
                mealDao.updateMeal(meal);
                break;
            case "editForm":
                Meal editMeal = mealDao.findMealById(Long.parseLong(custId));
                request.setAttribute("meal", editMeal);
                request.getRequestDispatcher("/editForm.jsp").forward(request, response);
                return;
        }

        response.sendRedirect(request.getContextPath() + "/meals");
    }

    private Meal getMealFromRequest(HttpServletRequest request) {
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        LocalDateTime dateTime1 = LocalDateTime.parse(dateTime, dateTimeFormatter);
        return new Meal(dateTime1, description, Integer.valueOf(calories));
    }
}
