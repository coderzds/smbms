package com.pink.service.user;

import com.pink.dao.BaseDao;
import com.pink.dao.user.UserDao;
import com.pink.dao.user.UserDaoImpl;
import com.pink.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService{

    //业务层都要调用dao层，所以我们要引入dao层
    private final UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }
    public User login(String userCode, String userPassword) {
        Connection conn;
        User user = null;
        conn = BaseDao.getConnection();
        try {
            //通过业务层调用对应具体的数据库操作
            user = userDao.getLoginUser(conn, userCode);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(conn,null,null);
        }
        return user;
    }
}
