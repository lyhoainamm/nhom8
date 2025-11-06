package com.edutime.dao;
import java.io.InputStream; import java.sql.*; import java.util.Properties;
public class Db{ private static Connection conn;
  public static Connection get() throws SQLException{
    if(conn!=null && !conn.isClosed()) return conn;
    try(InputStream in=Db.class.getClassLoader().getResourceAsStream("application.properties")){
      if(in==null) throw new RuntimeException("application.properties not found");
      Properties p=new Properties(); p.load(in);
      conn=DriverManager.getConnection(p.getProperty("db.url"), p.getProperty("db.user"), p.getProperty("db.password")); return conn;
    }catch(Exception e){ throw new RuntimeException("DB init failed: "+e.getMessage(), e); }
  }}