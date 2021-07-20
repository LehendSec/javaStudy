<%--
  Created by IntelliJ IDEA.
  User: Lehend
  Date: 2021/6/21
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<%--
scope 作用域

    public static final int PAGE_SCOPE = 1;
    public static final int REQUEST_SCOPE = 2;
    public static final int SESSION_SCOPE = 3;
    public static final int APPLICATION_SCOPE = 4;


public void setAttribute(String name, Object attribute, int scope) {
        switch(scope) {
        case 1:
            this.mPage.put(name, attribute);
            break;
        case 2:
            this.mRequest.put(name, attribute);
            break;
        case 3:
            this.mSession.put(name, attribute);
            break;
        case 4:
            this.mApp.put(name, attribute);
            break;
        default:
            throw new IllegalArgumentException("Bad scope " + scope);
        }

    }

--%>

<%
    //从pagecontext取出,我们通过寻找的方式来
    //从底层到高层（作用域):page->request-->session-->application
    // VM:双亲委派

    //此处只在session作用域中有效,等价于session.setAttribute()
pageContext.setAttribute("name6","lehend6",PageContext.SESSION_SCOPE);

%>
</body>
</html>
