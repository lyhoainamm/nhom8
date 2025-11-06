package com.edutime.model;
public class ClassSection { public int id, courseId, lecturerId, semesterId, expectedStudents, capacity; public String sectionCode, status;
  public ClassSection(int id,String sc,int c,int l,int sem,int ex,int cap,String status){ this.id=id; this.sectionCode=sc; this.courseId=c; this.lecturerId=l; this.semesterId=sem; this.expectedStudents=ex; this.capacity=cap; this.status=status; }
  @Override public String toString(){ return sectionCode; }
}