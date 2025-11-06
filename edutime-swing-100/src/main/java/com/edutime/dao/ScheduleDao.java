package com.edutime.dao;
import com.edutime.model.ScheduleEntry; import java.sql.*;
public class ScheduleDao{
  private boolean weekOverlap(String p1,String p2){
    if(p1==null||p1.isEmpty()||p2==null||p2.isEmpty()) return true;
    if(p1.equalsIgnoreCase("all")||p2.equalsIgnoreCase("all")) return true;
    if(p1.equalsIgnoreCase(p2)) return true;
    return true;
  }
  private boolean hasConflict(Connection c,int roomId,int classSectionId,int timeslotId,String startDate,String endDate) throws SQLException{
    String q = "SELECT se.id, ts.week_pattern FROM schedule_entries se "
             + "JOIN timeslots ts ON se.timeslot_id=ts.id "
             + "WHERE se.timeslot_id=? AND ((se.room_id=?) OR (se.class_section_id=?)) "
             + "AND NOT (se.end_date < ? OR se.start_date > ?) LIMIT 1";
    try(PreparedStatement ps=c.prepareStatement(q)){
      ps.setInt(1, timeslotId);
      ps.setInt(2, roomId);
      ps.setInt(3, classSectionId);
      ps.setString(4, startDate);
      ps.setString(5, endDate);
      try(ResultSet rs=ps.executeQuery()){
        if(rs.next()){
          String existing=rs.getString(2);
          String incoming=null;
          try(PreparedStatement ps2=c.prepareStatement("SELECT week_pattern FROM timeslots WHERE id=?")){
            ps2.setInt(1, timeslotId);
            try(ResultSet rs2=ps2.executeQuery()){ if(rs2.next()) incoming=rs2.getString(1); }
          }
          return weekOverlap(existing,incoming);
        }
      }
    }
    return false;
  }
  public java.util.List<ScheduleEntry> list() throws SQLException{
    return DaoUtil.query("SELECT id,class_section_id,room_id,timeslot_id,start_date,end_date,note FROM schedule_entries",
      rs->{ try{ return new ScheduleEntry(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getString(6),rs.getString(7)); }catch(Exception e){ throw new RuntimeException(e);} });
  }
  public int insert(int cs,int room,int ts,String s,String e,String note) throws SQLException{
    try(Connection c=Db.get()){ if(hasConflict(c,room,cs,ts,s,e)) throw new SQLException("Lịch trùng (phòng hoặc lớp HP) tại cùng khung giờ/tuần."); }
    return DaoUtil.update("INSERT INTO schedule_entries(class_section_id,room_id,timeslot_id,start_date,end_date,note) VALUES(?,?,?,?,?,?)", cs,room,ts,s,e,note);
  }
  public int delete(int id) throws SQLException{ return DaoUtil.update("DELETE FROM schedule_entries WHERE id=?", id); }
}