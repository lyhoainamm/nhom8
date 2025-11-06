package com.edutime.model;
public class Semester { public int id; public String name, academicYear, startDate, endDate; public boolean published;
  public Semester(int id, String name, String ay, String s, String e, boolean pub){ this.id=id; this.name=name; this.academicYear=ay; this.startDate=s; this.endDate=e; this.published=pub; }
  @Override public String toString(){ return name + " ("+academicYear+")"; }
}