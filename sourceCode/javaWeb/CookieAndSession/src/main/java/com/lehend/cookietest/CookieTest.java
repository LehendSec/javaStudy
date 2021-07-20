package com.lehend.cookietest;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class CookieTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        解决中文乱码
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        PrintWriter out = resp.getWriter();

//        服务器从客户端获取cookie，这里的cookie的客户端的访问服务器的时间
        Cookie[] cookies = req.getCookies();
//        判断cookie是否存在，如果不存在，说明该浏览器的用户之前没有访问过
        if (cookies != null) {
//            如果存在就打印上一次的访问时间
            out.write("你上一次的访问时间:");
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
//                判断cookie数组中的的name是否是之前访问时设置的name
                if (cookie.getName().equals("lastLoginTime")) {
//                    获取cookie的值，这里是访问时间
                    long lastLoginTime = Long.parseLong(cookie.getValue());
                    Date date = new Date(lastLoginTime);
                    out.write(date.toLocaleString());
                }
            }
        }else {
            out.write("这是你第一次访问本网站");
        }
//        服务器给客户端设置一个cookie
        Cookie cookie = new Cookie("lastLoginTime",String.valueOf(System.currentTimeMillis()));
//        设置cookie的有效期
        cookie.setMaxAge(24*60*60);
//        将cookie添加到响应中发送给而客户端
        resp.addCookie(cookie);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
