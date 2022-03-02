package com.pink.dao.role;

import com.pink.dao.BaseDao;
import com.pink.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl implements RoleDao{
    public List<Role> getRoleList(Connection conn) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Role> roleList = new ArrayList<Role>();
        if (conn!=null){
            String sql = "select * from smbms_role";
            Object[] params = {};
            rs = BaseDao.execute(conn, sql, params, rs, pstm);

            while (rs.next()){
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleCode(rs.getString("roleCode"));
                role.setRoleName(rs.getString("roleName"));
                roleList.add(role);
            }
            BaseDao.closeResource(conn,pstm,rs);
        }
        return roleList;
    }
}
