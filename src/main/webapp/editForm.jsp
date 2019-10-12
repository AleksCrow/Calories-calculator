<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Изменить еду</title>
</head>
<body>
<h3><a href="meals">К списку</a></h3>
<hr>
<h2>Изменить данные о еде</h2>
<form method="post">
    <input type="hidden" name="command" value="editMeal"/>
    <input type="hidden" name="id" value='${meal.id}'>
    <label><input type="datetime-local" name="dateTime" id="dateTime" placeholder="${meal.dateTime}" value="${meal.dateTime}" required></label><br>
    <label><input type="text" name="description" id="description" placeholder="${meal.description}" value="${meal.description}" required></label><br>
    <label><input type="number" name="calories" id="calories" placeholder="${meal.calories}" value="${meal.calories}" required></label><br>
    <input type="submit" value="Изменить" name="ok"><br>
</form>
</body>
</html>