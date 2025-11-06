package com.edutime.dao;
import com.edutime.model.ClassSection; import java.sql.*;
public class ClassSectionDao{
  public java.util.List<ClassSection> list() throws SQLException{
    return DaoUtil.query("SELECT id,section_code,course_id,lecturer_id,semester_id,expected_students,capacity,status FROM class_sections",
      rs->{ try{ return new ClassSection(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),rs.getString(8)); }catch(Exception e){ throw new RuntimeException(e);} });
  }
  public int insert(String sc,int courseId,int lecturerId,int semesterId,int expected,int capacity,String status) throws SQLException{
    return DaoUtil.update("INSERT INTO class_sections(section_code,course_id,lecturer_id,semester_id,expected_students,capacity,status) VALUES(?,?,?,?,?,?,?)", sc,courseId,lecturerId,semesterId,expected,capacity,status);
  }
  public int update(int id,int courseId,int lecturerId,int semesterId,int expected,int capacity,String status) throws SQLException{
    return DaoUtil.update("UPDATE class_sections SET course_id=?, lecturer_id=?, semester_id=?, expected_students=?, capacity=?, status=? WHERE id=?", courseId,lecturerId,semesterId,expected,capacity,status,id);
  }
  public int delete(int id) throws SQLException{ return DaoUtil.update("DELETE FROM class_sections WHERE id=?", id); }
}