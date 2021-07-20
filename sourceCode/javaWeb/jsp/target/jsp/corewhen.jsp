<%--
  Created by IntelliJ IDEA.
  User: Lehend
  Date: 2021/6/21
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:set var="score" value="85"></c:set>
<c:choose>
    <c:when test="${score >= 90}">
        你的成绩算优秀
    </c:when>
    <c:when test="${score >= 80}">
        你的成绩算良好
    </c:when>
    <c:when test="${score >= 70}">
        你的成绩算一般
    </c:when>
    <c:when test="${score >= 60}">
        你的成绩为合格
    </c:when>
    <c:when test="${score < 60}">
        你的成绩不合格
    </c:when>
</c:choose>
</body>
</html>
