package com.lehend.sessiontest;

import com.lehend.pojo.Persion;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SessionTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //    解决乱码问题
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

//        获取session
        HttpSession session = req.getSession();
//        往session中存东西
        session.setAttribute("name",new Persion("Lhend邹",15));
        String sessionId = session.getId();
//        判断当前session是不是新创建的
        if(session.isNew()){
            resp.getWriter().write("session创建成功,is："+sessionId);
        }else {
            resp.getWriter().write("session在服务器中已经存在，不需要再创建");
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


}
