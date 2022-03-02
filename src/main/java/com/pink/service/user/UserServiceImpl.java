package com.pink.service.user;

import com.pink.dao.BaseDao;
import com.pink.dao.user.UserDao;
import com.pink.dao.user.UserDaoImpl;
import com.pink.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService{

    //业务层都要调用dao层，所以我们要引入dao层
    private final UserDao userDao;
    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }
    public User login(String userCode, String userPassword) {
        System.out.println(userCode+":"+userPassword);
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

    public boolean updatePwd(int id, String userPassword) {
        System.out.println("UserServiceImpl:"+userPassword);
        Connection conn = null;
        boolean flag = false;
        try {
            conn = BaseDao.getConnection();
            int i = userDao.updatePwd(conn, id, userPassword);
            System.out.println("影响行数:"+i);
            if (i>0){
                flag = true;
                System.out.println("密码修改成功");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(conn,null,null);
        }
        return flag;
    }

    public int getUserCount(String userName, int userRole) {
        Connection conn = null;
        int i = 0;
        try {
            conn = BaseDao.getConnection();
            i = userDao.getUserCount(conn, userName, userRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(conn,null,null);
        }
        return i;

    }

    public List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize) {
        System.out.println("UserService--> userName: "+userName);
        System.out.println("UserService--> userRole: "+userRole);
        System.out.println("UserService--> currentPageNo: "+currentPageNo);
        System.out.println("UserService--> pageSize: "+pageSize);
        Connection conn = null;
        List<User> userList = null;
        try {
            conn = BaseDao.getConnection();
            userList = userDao.getUserList(conn, userName, userRole, currentPageNo, pageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(conn,null,null);
        }
        return userList;
    }
}
