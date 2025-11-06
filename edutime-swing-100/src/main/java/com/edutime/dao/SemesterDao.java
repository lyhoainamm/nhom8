package com.edutime.dao;
import com.edutime.model.Semester; import java.sql.*; 
public class SemesterDao{
  public java.util.List<Semester> list() throws SQLException{
    return com.edutime.dao.DaoUtil.query("SELECT id,name,academic_year,start_date,end_date,is_published FROM semesters ORDER BY start_date DESC",
      rs->{ try{ return new Semester(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDate(4).toString(),rs.getDate(5).toString(),rs.getInt(6)==1);}catch(Exception e){throw new RuntimeException(e);} });
  }
  public int insert(String name,String year,String s,String e,boolean pub) throws SQLException{
    return com.edutime.dao.DaoUtil.update("INSERT INTO semesters(name,academic_year,start_date,end_date,is_published) VALUES(?,?,?,?,?)", name,year,s,e,pub?1:0);
  }
  public int update(int id,String name,String year,String s,String e,boolean pub) throws SQLException{
    return com.edutime.dao.DaoUtil.update("UPDATE semesters SET name=?, academic_year=?, start_date=?, end_date=?, is_published=? WHERE id=?", name,year,s,e,pub?1:0,id);
  }
  public int delete(int id) throws SQLException{ return com.edutime.dao.DaoUtil.update("DELETE FROM semesters WHERE id=?", id); }
}