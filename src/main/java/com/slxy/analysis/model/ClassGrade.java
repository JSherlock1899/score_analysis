package com.slxy.analysis.model;

import java.io.Serializable;

/**
 * @author: sherlock
 * @description:班级成绩
 * @date: 2019/9/19 22:34
 */
public class ClassGrade implements Serializable {

    //序列化
    private static final long serialVersionUID = -4639357118615754403L;

    /**
     * 班级号
     */
    private String classNumber;
    /**
     * 各科平均成绩
     */
    private String chineseAverageGrades;
    private String mathAverageGrades;
    private String englishAverageGrades;
    private String physicsAverageGrades;
    private String chemistryAverageGrades;
    private String biologyAverageGrades;
    private String historyAverageGrades;
    private String politicsAverageGrades;
    private String geographyAverageGrades;
    private String technologyAverageGrades;
    /**
     * 总分平均成绩
     */
    private String totalPointAverageGrades;

    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public String getChineseAverageGrades() {
        return chineseAverageGrades;
    }

    public void setChineseAverageGrades(String chineseAverageGrades) {
        this.chineseAverageGrades = chineseAverageGrades;
    }

    public String getMathAverageGrades() {
        return mathAverageGrades;
    }

    public void setMathAverageGrades(String mathAverageGrades) {
        this.mathAverageGrades = mathAverageGrades;
    }

    public String getEnglishAverageGrades() {
        return englishAverageGrades;
    }

    public void setEnglishAverageGrades(String englishAverageGrades) {
        this.englishAverageGrades = englishAverageGrades;
    }

    public String getPhysicsAverageGrades() {
        return physicsAverageGrades;
    }

    public void setPhysicsAverageGrades(String physicsAverageGrades) {
        this.physicsAverageGrades = physicsAverageGrades;
    }

    public String getChemistryAverageGrades() {
        return chemistryAverageGrades;
    }

    public void setChemistryAverageGrades(String chemistryAverageGrades) {
        this.chemistryAverageGrades = chemistryAverageGrades;
    }

    public String getBiologyAverageGrades() {
        return biologyAverageGrades;
    }

    public void setBiologyAverageGrades(String biologyAverageGrades) {
        this.biologyAverageGrades = biologyAverageGrades;
    }

    public String getHistoryAverageGrades() {
        return historyAverageGrades;
    }

    public void setHistoryAverageGrades(String historyAverageGrades) {
        this.historyAverageGrades = historyAverageGrades;
    }

    public String getPoliticsAverageGrades() {
        return politicsAverageGrades;
    }

    public void setPoliticsAverageGrades(String politicsAverageGrades) {
        this.politicsAverageGrades = politicsAverageGrades;
    }

    public String getGeographyAverageGrades() {
        return geographyAverageGrades;
    }

    public void setGeographyAverageGrades(String geographyAverageGrades) {
        this.geographyAverageGrades = geographyAverageGrades;
    }

    public String getTechnologyAverageGrades() {
        return technologyAverageGrades;
    }

    public void setTechnologyAverageGrades(String technologyAverageGrades) {
        this.technologyAverageGrades = technologyAverageGrades;
    }

    public String getTotalPointAverageGrades() {
        return totalPointAverageGrades;
    }

    public void setTotalPointAverageGrades(String totalPointAverageGrades) {
        this.totalPointAverageGrades = totalPointAverageGrades;
    }
}
