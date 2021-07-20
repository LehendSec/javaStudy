
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    pageContext.setAttribute("name1","Lehend1");
    request.setAttribute("name2","lehend2");
    session.setAttribute("name3","lehend3");
    application.setAttribute("name4","lehend4");
%>

<%
    String name1 = (String) pageContext.getAttribute("name1");
    String name2 = (String) pageContext.getAttribute("name2");
    String name3 = (String) pageContext.getAttribute("name3");
    String name4 = (String) pageContext.getAttribute("name4");
    String name5 = (String) pageContext.getAttribute("name5");  //不存在name5

%>

<%--使用el表达式输出--%>
<h5>取出的值为：</h5>
<h6>${name1}</h6>
<h6>${name2}</h6>
<h6>${name3}</h6>
<h6>${name4}</h6>
<h6>${name5}</h6>
<h6><%=name5%></h6>

</body>
</html>
