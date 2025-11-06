package com.edutime.model;
public class Room { public int id,capacity; public String code,name,type;
  public Room(int id,String code,String name,int capacity,String type){ this.id=id; this.code=code; this.name=name; this.capacity=capacity; this.type=type; }
  @Override public String toString(){ return code; }
}