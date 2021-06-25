<%--
  Created by IntelliJ IDEA.
  User: Eugene
  Date: 02.06.2021
  Time: 0:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="/auth">
    <table>
        <tr>
            <td>
                Name: <input type="text" name="userNameParam">
            </td>
            <td>
                Password: <input type="password" name="passwordParam">
            </td>
        </tr>
    </table>
    <input type="submit" value="sign in">
</form>
</body>
</html>
