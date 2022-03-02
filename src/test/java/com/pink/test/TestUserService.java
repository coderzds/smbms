package com.pink.test;

import com.pink.pojo.User;
import com.pink.service.user.UserServiceImpl;
import org.junit.Test;

public class TestUserService {
    @Test
    public void testLogin(){
        UserServiceImpl userService = new UserServiceImpl();
        User admin = userService.login("admin", "1234567");
        System.out.println(admin.getUserPassword());
    }
    @Test
    public void testUser(){
        UserServiceImpl userService = new UserServiceImpl();
        int i = userService.getUserCount(null, 0);
        System.out.println(i);
    }
}
