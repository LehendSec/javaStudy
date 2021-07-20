<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Lehend
  Date: 2021/6/21
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    ArrayList<String> peoples = new ArrayList<>();
    peoples.add("张三");
    peoples.add("李四");
    peoples.add("王五");
    peoples.add("马六");
    peoples.add("赵7");

    request.setAttribute("peoples",peoples);
%>
<c:forEach var="people" items="${peoples}">
    <c:out value="${people}" /><br >
</c:forEach>
<hr>
<c:forEach var="people" items="${peoples}" begin="1" end="3" step="2">
    <c:out value="${people}"/><br >
</c:forEach>


</body>
</html>
