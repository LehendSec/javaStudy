package com.lehend;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SendRedirect extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
/*
resp.setHeader("Location","/r/img");
resp.setStatus(302);
*/
        resp.sendRedirect("/img");//重定向到验证码页面
    }
}
