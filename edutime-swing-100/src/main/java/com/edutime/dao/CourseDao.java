package com.edutime.dao;
import com.edutime.model.Course; import java.sql.*; 
public class CourseDao{
  public java.util.List<Course> list() throws SQLException{
    return DaoUtil.query("SELECT id,code,name,credits FROM courses", rs->{ try{ return new Course(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)); }catch(Exception e){ throw new RuntimeException(e);} });
  }
  public int insert(String code,String name,int credits) throws SQLException{
    return DaoUtil.update("INSERT INTO courses(code,name,credits) VALUES(?,?,?)", code,name,credits);
  }
  public int update(int id,String name,int credits) throws SQLException{
    return DaoUtil.update("UPDATE courses SET name=?, credits=? WHERE id=?", name,credits,id);
  }
  public int delete(int id) throws SQLException{ return DaoUtil.update("DELETE FROM courses WHERE id=?", id); }
}