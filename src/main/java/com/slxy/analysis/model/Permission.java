package com.slxy.analysis.model;


import java.io.Serializable;

public class Permission implements Serializable {

  //序列化
  private static final long serialVersionUID = -2639697846115592793L;
  private long id;
  /**
   *权限名称
   */
  private String name;
  /**
   * 权限：user:list，user:add等
   */
  private String permission;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPermission() {
    return permission;
  }

  public void setPermission(String permission) {
    this.permission = permission;
  }

}
