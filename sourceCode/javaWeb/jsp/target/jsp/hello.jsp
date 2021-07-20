<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String name = "Lehend";
%>
<%--
下述就是jsp表达式
用于将程序输出到客户端页面
<%=变量或表达式%>
--%>
name:<%=name%>
<hr>
<%--
jsp脚本片段

--%>
<%
    int sum = 0;
    for (int i = 1; i <= 100; i++) {
        sum += i;
    }
    out.println("<h1>Sum=" + sum + "</h1>");
%>

<%
    for (int i = 0; i < 5; i++) {
%>
<h1>Hello,World <%=i%>
</h1>
<%
    }
%>

<%!
    static {
        System.out.println("Loading Servlet!");
    }

    private int globalVar = 0;

    public void Lehend() {
        System.out.println("进入了方法Lehend！");
    }
%>
</body>
</html>
