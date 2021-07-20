package com.lehend;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

//统计网站在线人数 ： 通过统计session统计在线人数
public class OnlineCountTotal implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
//        创建session监听，在创建session时会触发这个监听事件
        ServletContext servletContext = se.getSession().getServletContext();
//        输出当前sessionid个数，也就是在线人数
        System.out.println(se.getSession().getId());
        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");
//        如果OnlineCount为null，那么当前访问的就只有一个，说明在线人数为1
        if (onlineCount == null) {
            onlineCount = new Integer(1);
        } else {
            int count = onlineCount.intValue();
            onlineCount = new Integer(count + 1);
        }
        servletContext.setAttribute("OnlineCount", onlineCount);
    }

    //    当session被销毁的时候就将onlineCount-1
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext servletContext = se.getSession().getServletContext();
        Integer onlineCount = (Integer) servletContext.getAttribute("OnlineCount");
        if (onlineCount == null) {
            onlineCount = new Integer(0);
        } else {
            int count = onlineCount.intValue();
            onlineCount = new Integer(count - 1);
        }
        servletContext.setAttribute("OnlineCount",onlineCount);
    }
    /*
Session销毁：
1. 手动销毁 getSession().invalidate();
2. 自动销毁
*/
}
