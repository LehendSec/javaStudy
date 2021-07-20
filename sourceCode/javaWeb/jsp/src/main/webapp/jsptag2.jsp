<%--
  Created by IntelliJ IDEA.
  User: Lehend
  Date: 2021/6/21
  Time: 20:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--
    通过jsp标签携带参数
http://localhost:8080/jsptag.jsp?name=kuangshen&age=12
--%>
<jsp:forward page="index2.jsp">
    <jsp:param name="name" value="lehend"></jsp:param>
    <jsp:param name="age" value="24"></jsp:param>
</jsp:forward>

</body>
</html>
