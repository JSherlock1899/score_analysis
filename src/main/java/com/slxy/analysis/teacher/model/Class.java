package com.slxy.analysis.teacher.model;


public class Class {

  private String classNumber;
  private String headTeacher;
  private String startYear;
  private String classCount;


  public String getClassNunber() {
    return classNumber;
  }

  public void setClassNunber(String classNunber) {
    this.classNumber = classNunber;
  }


  public String getHeadTeacher() {
    return headTeacher;
  }

  public void setHeadTeacher(String headTeacher) {
    this.headTeacher = headTeacher;
  }


  public String getStartYear() {
    return startYear;
  }

  public void setStartYear(String startYear) {
    this.startYear = startYear;
  }


  public String getClassCount() {
    return classCount;
  }

  public void setClassCount(String classCount) {
    this.classCount = classCount;
  }

  public Class(String classNunber) {
    this.classNumber = classNunber;
  }
}
