<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить еду</title>
</head>
<body>
<h3><a href="meals">К списку</a></h3>
<hr>
<h2>Добавить данные о еде</h2>
<form method="post">
    <label><input type="datetime-local" name="dateTime" id="dateTime" required></label><br>
    <label><input type="text" name="description" id="description" placeholder="Введите описание" required></label><br>
    <label><input type="number" name="calories" id="calories" placeholder="Введите каллории" required></label><br>
    <input type="submit" value="Добавить" name="ok"><br>
    <input type="hidden" name="command" value="addMeal"/>
</form>
</body>
</html>