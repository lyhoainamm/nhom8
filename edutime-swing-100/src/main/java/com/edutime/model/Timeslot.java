package com.edutime.model;
public class Timeslot { public int id,dayOfWeek; public String start,end,pattern;
  public Timeslot(int id,int dow,String start,String end,String pattern){ this.id=id; this.dayOfWeek=dow; this.start=start; this.end=end; this.pattern=pattern; }
  @Override public String toString(){ return "T"+dayOfWeek+" "+start+"-"+end+(pattern!=null&&pattern.length()>0?" ["+pattern+"]":""); }
}