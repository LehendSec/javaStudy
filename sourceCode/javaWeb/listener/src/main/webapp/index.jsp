<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>当前有<%=this.getServletConfig().getServletContext().getAttribute("OnlineCount")%>人在线</h2>
</body>
</html>
