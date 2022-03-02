package com.pink.servlet.user;

import com.pink.pojo.User;
import com.pink.service.user.UserServiceImpl;
import com.pink.utils.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    //Servlet：控制层，调用业务层代码
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入LoginServlet...");
        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        System.out.println("userCode:"+userCode);
        System.out.println("userPassword:"+userPassword);
        //和数据库的用户名密码对比，调用业务层
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login(userCode, userPassword);//这里已经把登录的人给查出来了
        /*System.out.println("查询当前用户密码:"+user.getUserPassword());
        System.out.println("查询当前用户名:"+user.getUserCode());
        System.out.println("用户输入的密码:"+userPassword);*/
        if (user != null){
            //查有此人
            if (userPassword.equals(user.getUserPassword())){
                //密码正确
                //跳转到内部主页
                req.getSession().setAttribute(Constant.USER_SESSION,user);
                resp.sendRedirect(req.getContextPath()+"/jsp/frame.jsp");
            }else {
                req.setAttribute("error","密码不正确");
                req.getRequestDispatcher("/login.jsp").forward(req,resp);
            }

        }else {
            //查无此人，无法登录，转发回登录页面，顺带提示用户名或密码错误
            req.setAttribute("error","用户名不正确");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
