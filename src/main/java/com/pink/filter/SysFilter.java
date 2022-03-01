package com.pink.filter;

import com.pink.pojo.User;
import com.pink.utils.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        //过滤器，从session中获取user对象
        User user = (User) ((HttpServletRequest) req).getSession().getAttribute(Constant.USER_SESSION);
        if (user==null){
            //已经被移除，或者注销了，或者未登录
            ((HttpServletResponse)resp).sendRedirect("/error.jsp");
        }else {
            chain.doFilter(req,resp);
        }
    }

    public void destroy() {

    }
}
