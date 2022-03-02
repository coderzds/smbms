package com.pink.dao.user;

import com.mysql.jdbc.StringUtils;
import com.pink.dao.BaseDao;
import com.pink.pojo.Role;
import com.pink.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao{
    //得到要登录的用户
    public User getLoginUser(Connection conn, String userCode) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        if (conn!=null){
            String sql = "select * from smbms_user where userCode = ?";
            Object[] params = {userCode};
            rs = BaseDao.execute(conn, sql, params, rs, pstm);
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getDate("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getDate("modifyDate"));
            }
            BaseDao.closeResource(conn,pstm,rs);
        }
        return user;

    }

    public int updatePwd(Connection conn, int id, String userPassword) throws SQLException {
        System.out.println("UserDaoImpl密码"+userPassword);
        System.out.println("UserDaoImpl中的id:"+id);
        PreparedStatement pstm = null;
        int i = 0;
        if (conn!=null){
            String sql = "update smbms_user set userPassword = ? where id = ? ";
            Object[] params = {userPassword,id};
            System.out.println(userPassword);
            System.out.println(id);
            i = BaseDao.execute(conn, sql, params, pstm);
            BaseDao.closeResource(conn,pstm,null);
        }
        return i;
    }

    //根据用户名或者角色查询用户总数
    public int getUserCount(Connection conn, String userName, int userRole) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if(conn!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            ArrayList<java.io.Serializable> list = new ArrayList<java.io.Serializable>();
            if (!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ? ");
                list.add("%"+userName+"%");// index:0
            }
            if (userRole>0){
                sql.append(" and u.userRole = ? ");
                list.add(userRole);// index:1
            }
            //怎么把List转换为数组
            Object[] params = list.toArray();
            System.out.println("UserDaoImpl-->getUserCount:"+ sql);//输出最后完整的SQL语句
            rs = BaseDao.execute(conn, sql.toString(), params, rs, pstm);
            if (rs.next()){
                count = rs.getInt("count");//从结果集中获取最终的数量
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return count;
    }

    public List<User> getUserList(Connection conn, String userName, int userRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if (conn!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.RoleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ? ");
                list.add("%"+userName+"%");// index:0
            }
            if (userRole>0){
                sql.append(" and u.userRole = ? ");
                list.add(userRole);// index:1
            }
            sql.append(" order by creationDate desc limit ?,? ");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("getUserList-->sql :"+sql);
            rs = BaseDao.execute(conn,sql.toString(),params,rs,pstm);
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setUserRole(rs.getInt("userRole"));
                user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(user);
            }
            BaseDao.closeResource(conn,pstm,rs);
        }
        return userList;
    }
}
