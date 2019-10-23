package com.slxy.analysis.teacher.model;



public class Student extends User {

  private String schoolName;
  private String nativePlace;
  private String homeAddress;
  private String school;
  private String picture;
  private String classNumber;

  /**
   *科目
   */
  public static final String[] SUBJECT = {"chinese","math","english","physics","chemistry","biology","history","politics","geography","technology","total_point"};

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

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

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getClassNumber() {
    return classNumber;
  }

  public void setClassNumber(String classNumber) {
    this.classNumber = classNumber;
  }
}
