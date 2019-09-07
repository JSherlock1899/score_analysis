package com.slxy.analysis.model;


public class Exam {

  private String examName;
  private String examType;
  private String examTime;
  private String examCount;
  private String tableName;
  private String grade;


  public String getExamName() {
    return examName;
  }

  public void setExamName(String examName) {
    this.examName = examName;
  }


  public String getExamType() {
    return examType;
  }

  public void setExamType(String examType) {
    this.examType = examType;
  }


  public String getExamTime() {
    return examTime;
  }

  public void setExamTime(String examTime) {
    this.examTime = examTime;
  }


  public String getExamCount() {
    return examCount;
  }

  public void setExamCount(String examCount) {
    this.examCount = examCount;
  }


  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }


  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  //教师查询处 分页传值
  public Exam(String examName, String tableName) {
    this.examName = examName;
    this.tableName = tableName;
  }
}
