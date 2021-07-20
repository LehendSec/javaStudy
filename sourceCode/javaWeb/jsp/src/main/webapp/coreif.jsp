<%--
  Created by IntelliJ IDEA.
  User: Lehend
  Date: 2021/6/21
  Time: 20:41
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h4>测试</h4>
<hr />
<form method="get" action="coreif.jsp">
<%--
    使用el表达式获取html标签中的数据
--%>
    <input type="text" name="username" value="${param.username}">
    <input type="submit" value="登录">

    <c:if test="${param.username == 'admin'}" var="isAdmin">
        <c:out value="登陆成功"/>
    </c:if>
    <c:out value="${isAdmin}"/>
</form>
</body>
</html>
