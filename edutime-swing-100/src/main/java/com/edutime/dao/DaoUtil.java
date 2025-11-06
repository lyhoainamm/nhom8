package com.edutime.dao;
import java.sql.*; import java.util.*; import java.util.function.Function;
public class DaoUtil{
  public static <T> java.util.List<T> query(String sql, java.util.function.Function<ResultSet,T> mapper, Object... params) throws SQLException{
    try(Connection c=Db.get(); PreparedStatement ps=c.prepareStatement(sql)){
      for(int i=0;i<params.length;i++) ps.setObject(i+1, params[i]);
      try(ResultSet rs=ps.executeQuery()){ java.util.List<T> out=new java.util.ArrayList<>(); while(rs.next()) out.add(mapper.apply(rs)); return out; }
    }}
  public static int update(String sql, Object... params) throws SQLException{
    try(Connection c=Db.get(); PreparedStatement ps=c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
      for(int i=0;i<params.length;i++) ps.setObject(i+1, params[i]);
      int n=ps.executeUpdate(); try(ResultSet rs=ps.getGeneratedKeys()){ if(rs.next()) return rs.getInt(1);} return n;
    }}
}