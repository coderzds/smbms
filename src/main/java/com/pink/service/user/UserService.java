package com.pink.service.user;

import com.pink.pojo.User;

import java.sql.Connection;
import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode,String userPassword);

    //根据用户id修改密码
    boolean updatePwd(int id,String userPassword);

    //根据用户名或者角色查询用户总数
    int getUserCount(String userName,int userRole);

    //根据条件查询用户列表
    List<User> getUserList(String userName, int userRole, int currentPageNo, int pageSize);
}
