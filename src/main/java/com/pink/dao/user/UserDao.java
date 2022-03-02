package com.pink.dao.user;

import com.pink.pojo.Role;
import com.pink.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    //得到要登录的用户
    User getLoginUser(Connection conn, String userCode) throws SQLException;

    //修改当前用户密码
    int updatePwd(Connection conn,int id,String userPassword) throws SQLException;

    //根据用户名或者角色查询用户总数
    int getUserCount(Connection conn,String userName,int userRole) throws SQLException;

    //通过条件查询-userList
    List<User> getUserList(Connection conn,String userName,int userRole,int currentPageNo,int pageSize) throws SQLException;
}
