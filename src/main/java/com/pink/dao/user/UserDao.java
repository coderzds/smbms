package com.pink.dao.user;

import com.pink.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDao {
    //得到要登录的用户
    User getLoginUser(Connection conn, String userCode) throws SQLException;
}
