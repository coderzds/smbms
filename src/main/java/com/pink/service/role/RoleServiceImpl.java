package com.pink.service.role;

import com.pink.dao.BaseDao;
import com.pink.dao.role.RoleDao;
import com.pink.dao.role.RoleDaoImpl;
import com.pink.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl implements RoleService{
    //引入Dao
    private final RoleDao roleDao;
    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    public List<Role> getRoleList() {
        Connection conn = null;
        List<Role> roleList = null;
        try {
            conn = BaseDao.getConnection();
            roleList = roleDao.getRoleList(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeResource(conn,null,null);
        }
        return roleList;
    }
}
