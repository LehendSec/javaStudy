package com.lehend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.lehend.util.Constant.USER_SESSION;

public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object userSession = req.getSession().getAttribute(USER_SESSION);
        if (userSession != null) {
//            注销，这里就移除对应用户的session信息
            req.getSession().removeAttribute(USER_SESSION);
            resp.sendRedirect("/login.jsp");
        } else {
            resp.sendRedirect("/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
