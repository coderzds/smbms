package com.pink.test;

import com.pink.pojo.Role;
import com.pink.service.role.RoleServiceImpl;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestRoleService {
    @Test
    public void role(){
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        System.out.println(Arrays.toString(roleList.toArray()));
        for (Role role : roleList) {
            System.out.println(role.getRoleName());
        }
    }
}
