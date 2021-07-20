<%@ page import="com.lehend.pojo.People" %><%--
  Created by IntelliJ IDEA.
  User: Lehend
  Date: 2021/6/21
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
//    People people = new People();
//    people.setAddress("xxx");
//    people.setAge(19);
//    people.setId(1);
//    people.setAddress("xx");
//    和下面的功能是一样的
%>
<%--<%=people.getAddress()%>--%>
<%--<%=people.getId()%>--%>
<%--<%=people.getName()%>--%>
<%--<%=people.getAge()%>--%>

<jsp:useBean id="people" class="com.lehend.pojo.People" scope="page"/>
<jsp:setProperty name="people" property="name" value="lehend"/>
<jsp:setProperty name="people" property="age" value="25"/>
<jsp:setProperty name="people" property="address" value="beijing"/>
<jsp:setProperty name="people" property="id" value="1"/>


id：<jsp:getProperty name="people" property="id"/>
姓名：<jsp:getProperty name="people" property="name"/>
年龄：<jsp:getProperty name="people" property="age"/>
地址：<jsp:getProperty name="people" property="address"/>
</body>
</html>
