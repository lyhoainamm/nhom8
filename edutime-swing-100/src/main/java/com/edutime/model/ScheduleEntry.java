package com.edutime.model;
public class ScheduleEntry { public int id,classSectionId,roomId,timeslotId; public String startDate,endDate,note;
  public ScheduleEntry(int id,int cs,int r,int t,String s,String e,String n){ this.id=id; this.classSectionId=cs; this.roomId=r; this.timeslotId=t; this.startDate=s; this.endDate=e; this.note=n; }
}