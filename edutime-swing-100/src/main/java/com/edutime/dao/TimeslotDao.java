package com.edutime.dao;
import com.edutime.model.Timeslot; import java.sql.*;
public class TimeslotDao{
  public java.util.List<Timeslot> list() throws SQLException{
    return DaoUtil.query("SELECT id,day_of_week,start_time,end_time,COALESCE(week_pattern,'') FROM timeslots ORDER BY day_of_week,start_time",
      rs->{ try{ return new Timeslot(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5)); }catch(Exception e){ throw new RuntimeException(e);} });
  }
  public int insert(int dow,String start,String end,String pattern) throws SQLException{
    return DaoUtil.update("INSERT INTO timeslots(day_of_week,start_time,end_time,week_pattern) VALUES(?,?,?,?)", dow,start,end,(pattern==null||pattern.isEmpty()?null:pattern));
  }
  public int update(int id,int dow,String start,String end,String pattern) throws SQLException{
    return DaoUtil.update("UPDATE timeslots SET day_of_week=?, start_time=?, end_time=?, week_pattern=? WHERE id=?", dow,start,end,(pattern==null||pattern.isEmpty()?null:pattern),id);
  }
  public int delete(int id) throws SQLException{ return DaoUtil.update("DELETE FROM timeslots WHERE id=?", id); }
}