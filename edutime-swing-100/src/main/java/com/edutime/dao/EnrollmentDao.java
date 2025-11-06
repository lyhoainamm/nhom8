package com.edutime.dao;
import java.sql.*; 
public class EnrollmentDao{
  public java.util.List<Integer> sectionsOfStudent(int studentUserId) throws SQLException{
    return com.edutime.dao.DaoUtil.query("SELECT section_id FROM enrollments WHERE student_user_id=?",
      rs->{ try{ return rs.getInt(1); }catch(Exception e){ throw new RuntimeException(e);} }, studentUserId);
  }
  public int enroll(int studentUserId,int sectionId) throws SQLException{
    return com.edutime.dao.DaoUtil.update("INSERT INTO enrollments(student_user_id,section_id) VALUES(?,?)", studentUserId, sectionId);
  }
  public int unenroll(int studentUserId,int sectionId) throws SQLException{
    return com.edutime.dao.DaoUtil.update("DELETE FROM enrollments WHERE student_user_id=? AND section_id=?", studentUserId, sectionId);
  }
}