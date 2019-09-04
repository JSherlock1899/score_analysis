package com.slxy.analysis.model;

/**
 *
 */
public class Teacher extends User {


  /**
   * 籍贯
   */
  private String nativePlace;
  /**
   * 家庭住址
   */
  private String homeAddress;
  /**
   * 职称
   */
  private String jobGrade;
  /**
   * 政治面貌
   */
  private String polStat;
  /**
   * 入职时间
   */
  private String employedTime ;
  /**
   * 毕业院校
   */
  private String graduateSchool;
  /**
   * 证件照路径
   */
  private String picture;

  /**
   * 所教课程
   */
  private String subject;

  public String getNativePlace() {
    return nativePlace;
  }

  public void setNativePlace(String nativePlace) {
    this.nativePlace = nativePlace;
  }

  public String getHomeAddress() {
    return homeAddress;
  }

  public void setHomeAddress(String homeAddress) {
    this.homeAddress = homeAddress;
  }

  public String getJobGrade() {
    return jobGrade;
  }

  public void setJobGrade(String jobGrade) {
    this.jobGrade = jobGrade;
  }

  public String getPolStat() {
    return polStat;
  }

  public void setPolStat(String polStat) {
    this.polStat = polStat;
  }

  public String getEmployedTime() {
    return employedTime;
  }

  public void setEmployedTime(String employedTime) {
    this.employedTime = employedTime;
  }

  public String getGraduateSchool() {
    return graduateSchool;
  }

  public void setGraduateSchool(String graduateSchool) {
    this.graduateSchool = graduateSchool;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }
}
