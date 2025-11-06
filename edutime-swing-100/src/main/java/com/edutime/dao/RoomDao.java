package com.edutime.dao;
import com.edutime.model.Room; import java.sql.*;
public class RoomDao{
  public java.util.List<Room> list() throws SQLException{
    return DaoUtil.query("SELECT id,code,name,capacity,type FROM rooms", rs->{ try{ return new Room(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getString(5)); }catch(Exception e){ throw new RuntimeException(e);} });
  }
  public int insert(String code,String name,int capacity,String type) throws SQLException{
    return DaoUtil.update("INSERT INTO rooms(code,name,capacity,type) VALUES(?,?,?,?)", code,name,capacity,type);
  }
  public int update(int id,String name,int capacity,String type) throws SQLException{
    return DaoUtil.update("UPDATE rooms SET name=?, capacity=?, type=? WHERE id=?", name,capacity,type,id);
  }
  public int delete(int id) throws SQLException{ return DaoUtil.update("DELETE FROM rooms WHERE id=?", id); }
}