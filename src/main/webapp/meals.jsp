<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        TABLE {
            border-collapse: collapse;
            width: 800px;
        }
        TH, TD {
            border: 2px solid black;
            text-align: center;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <th>Дата</th>
    <th>Описание</th>
    <th>Каллории</th>
    <th>Превышение нормы</th>
    <th colspan=2>Редактирование</th>
    <c:forEach items="${meals}" var="meal">
        <tr style="color:${meal.excess ? 'red' : 'green'}">
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.excess} </td>
            <td>
                <form method='post'>
                    <input type='hidden' name='id' value='${meal.id}'>
                    <input type="hidden" name="command" value="editForm"/>
                    <button type='submit'>Изменить</button>
                </form>
            </td>
            <td>
                <form method='post'>
                    <input type='hidden' name='id' value='${meal.id}'>
                    <input type="hidden" name="command" value="deleteMeal"/>
                    <button type='submit'>Удалить</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<form method='post'>
    <input type="hidden" name="command" value="addForm"/>
    <button type='submit'>Добавить</button>
</form>
</body>
</html>