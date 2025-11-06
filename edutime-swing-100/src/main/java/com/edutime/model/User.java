package com.edutime.model;
public class User { public int id; public String username, displayName, role, email; public boolean active;
  public User(int id, String username, String displayName, String role, String email, boolean active){
    this.id=id; this.username=username; this.displayName=displayName; this.role=role; this.email=email; this.active=active;
  }
}