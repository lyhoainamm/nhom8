package com.edutime.dao;
import com.edutime.model.User; import java.sql.*; import java.util.*;
public class UserDao{
  public User login(String username,String password) throws SQLException{
    String sql="SELECT id,username,display_name,role,email,active,password_hash FROM users WHERE username=?";
    try(Connection c=Db.get(); PreparedStatement ps=c.prepareStatement(sql)){
      ps.setString(1,username);
      try(ResultSet rs=ps.executeQuery()){
        if(rs.next()){
          String stored=rs.getString("password_hash");
          if(stored!=null && stored.equals(password) && rs.getInt("active")==1){
            return new User(rs.getInt("id"), rs.getString("username"), rs.getString("display_name"), rs.getString("role"), rs.getString("email"), true);
          } } } }
    return null;
  }
  public List<User> listAll() throws SQLException{
    return com.edutime.dao.DaoUtil.query("SELECT id,username,display_name,role,email,active FROM users",
      rs->{ try{ return new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6)==1);}catch(Exception e){throw new RuntimeException(e);} });
  }
  public List<User> listByRole(String role) throws SQLException{
    return com.edutime.dao.DaoUtil.query("SELECT id,username,display_name,role,email,active FROM users WHERE role=?",
      rs->{ try{ return new User(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6)==1);}catch(Exception e){throw new RuntimeException(e);} }, role);
  }
  public int insert(String u,String p,String n,String r,boolean a,String email) throws SQLException{
    return com.edutime.dao.DaoUtil.update("INSERT INTO users(username,password_hash,display_name,role,email,active) VALUES(?,?,?,?,?,?)", u,p,n,r,email,a?1:0);
  }
  public int update(int id,String n,String email,String r,boolean a) throws SQLException{
    return com.edutime.dao.DaoUtil.update("UPDATE users SET display_name=?, email=?, role=?, active=? WHERE id=?", n,email,r,a?1:0,id);
  }
  public int delete(int id) throws SQLException{ return com.edutime.dao.DaoUtil.update("DELETE FROM users WHERE id=?", id); }
}