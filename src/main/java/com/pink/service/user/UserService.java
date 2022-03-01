package com.pink.service.user;

import com.pink.pojo.User;

public interface UserService {
    //用户登录
    public User login(String userCode,String userPassword);
}
