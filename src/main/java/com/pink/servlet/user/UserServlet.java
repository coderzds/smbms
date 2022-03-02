package com.pink.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import com.pink.pojo.Role;
import com.pink.pojo.User;
import com.pink.service.role.RoleServiceImpl;
import com.pink.service.user.UserServiceImpl;
import com.pink.utils.Constant;
import com.pink.utils.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//实现代码复用
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method.equals("savepwd")){
            this.updatePwd(req,resp);
        }else if (method.equals("pwdmodify")){
            this.pwdmodify(req,resp);
        }else if (method.equals("query")){
            this.query(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    //
    public void query(HttpServletRequest req, HttpServletResponse resp){
        //查询用户列表

        //从前端获取数据
        String queryUserName = req.getParameter("queryUserName");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;

        //获取用户列表
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = null;
        //第一次走这个请求，一定是第一页，并且页面大小是固定的
        int pageSize = 5;//可以写在配置文件，方便后期修改
        int currentPageNo = 1;

        if(queryUserName==null){
            queryUserName = "";
        }
        if (temp!=null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);// 给查询赋值
        }
        if (pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户总数（分页：上一页，下一页）
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount = pageSupport.getTotalPageCount();
        //控制首页和尾页
        //如果页面页数要小于1了，就显示第一页的东西
        if (totalPageCount<1){
            currentPageNo = 1;
        }else if (totalPageCount>totalCount){//当前页面大于最后一页
            currentPageNo = totalPageCount;
        }

        //获取用户列表展示
        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList",userList);

        //获取角色列表展示
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList",roleList);

        System.out.println("totalCount: "+totalCount);
        System.out.println("currentPageNo: "+currentPageNo);
        System.out.println("totalPageCount: "+totalPageCount);
        //获取分页展示
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);

        //获取搜索placeholder内容
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);

        //返回前端
        try {
            req.getRequestDispatcher(req.getContextPath()+"userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    //修改密码
    public void updatePwd(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("进入了UserServlet...");
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        String newpassword = req.getParameter("newpassword");

        System.out.println("前台输入密码:"+newpassword);
        boolean flag = false;

        if (o!=null && !StringUtils.isNullOrEmpty(newpassword)){
            UserServiceImpl userService = new UserServiceImpl();
            System.out.println("当前用户ID:"+((User)o).getId());
            flag = userService.updatePwd(((User)o).getId(), newpassword);
            if (flag){
                req.setAttribute("message","修改密码成功，请使用新密码重新登录");
                //密码移除成功，修改session
                req.getSession().removeAttribute(Constant.USER_SESSION);
            }else {
                req.setAttribute("message","密码修改失败");
            }
        }else {
            req.setAttribute("message","新密码有问题");
        }
        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //验证旧密码，使用session中用户的密码
    public void pwdmodify(HttpServletRequest req, HttpServletResponse resp){
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");
        //万能的Map,结果集
        Map<String, String> resultMap = new HashMap();
        if (o==null){
            //session失效或过期
            resultMap.put("result","sessionerror");
        }else if (StringUtils.isNullOrEmpty(oldpassword)){
            //输入密码为空
            resultMap.put("result","error");
        }else {
            String userPassword = ((User) o).getUserPassword();
            if (oldpassword.equals(userPassword)){
                resultMap.put("result","true");
            }else {
                resultMap.put("result","false");
            }
        }
        resp.setContentType("application/json");
        try {
            PrintWriter writer = resp.getWriter();
            //JSONArray 阿里巴巴JSON工具类,转换格式
            //resultMap= ["result","true","result","false"]
            //Json格式 = {key:value}
            writer.write(JSONArray.toJSONString(resultMap));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
