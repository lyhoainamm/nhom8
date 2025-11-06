package com.edutime.model;
public class Course { public int id, credits; public String code,name,dept;
  public Course(int id,String code,String name,int credits){ this.id=id; this.code=code; this.name=name; this.credits=credits; }
  public Course(int id,String code,String name,int credits,String dept){ this(id,code,name,credits); this.dept=dept; }
  @Override public String toString(){ return code+" - "+name; }
}