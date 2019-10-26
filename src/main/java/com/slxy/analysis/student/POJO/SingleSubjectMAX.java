package com.slxy.analysis.student.POJO;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


public class SingleSubjectMAX implements Serializable {
    private String chineseGrades;
    private String mathGrades;
    private String englishGrades;
    private String physicsGrades;
    private String chemistryGrades;
    private String biologyGrades;
    private String historyGrades;
    private String politicsGrades;
    private String geographyGrades;
    private String technologyGrades;



    public String getChineseGrades() {
        return chineseGrades;
    }

    public void setChineseGrades(String chineseGrades) {
        this.chineseGrades = chineseGrades;
    }

    public String getMathGrades() {
        return mathGrades;
    }

    public void setMathGrades(String mathGrades) {
        this.mathGrades = mathGrades;
    }

    public String getEnglishGrades() {
        return englishGrades;
    }

    public void setEnglishGrades(String englishGrades) {
        this.englishGrades = englishGrades;
    }

    public String getPhysicsGrades() {
        return physicsGrades;
    }

    public void setPhysicsGrades(String physicsGrades) {
        this.physicsGrades = physicsGrades;
    }

    public String getChemistryGrades() {
        return chemistryGrades;
    }

    public void setChemistryGrades(String chemistryGrades) {
        this.chemistryGrades = chemistryGrades;
    }

    public String getBiologyGrades() {
        return biologyGrades;
    }

    public void setBiologyGrades(String biologyGrades) {
        this.biologyGrades = biologyGrades;
    }

    public String getHistoryGrades() {
        return historyGrades;
    }

    public void setHistoryGrades(String historyGrades) {
        this.historyGrades = historyGrades;
    }

    public String getPoliticsGrades() {
        return politicsGrades;
    }

    public void setPoliticsGrades(String politicsGrades) {
        this.politicsGrades = politicsGrades;
    }

    public String getGeographyGrades() {
        return geographyGrades;
    }

    public void setGeographyGrades(String geographyGrades) {
        this.geographyGrades = geographyGrades;
    }

    public String getTechnologyGrades() {
        return technologyGrades;
    }

    public void setTechnologyGrades(String technologyGrades) {
        this.technologyGrades = technologyGrades;
    }

    @Override
    public String toString() {
        return "SingleSubjectMAX{" +
                "chineseGrades='" + chineseGrades + '\'' +
                ", mathGrades='" + mathGrades + '\'' +
                ", englishGrades='" + englishGrades + '\'' +
                ", physicsGrades='" + physicsGrades + '\'' +
                ", chemistryGrades='" + chemistryGrades + '\'' +
                ", biologyGrades='" + biologyGrades + '\'' +
                ", historyGrades='" + historyGrades + '\'' +
                ", politicsGrades='" + politicsGrades + '\'' +
                ", geographyGrades='" + geographyGrades + '\'' +
                ", technologyGrades='" + technologyGrades + '\'' +
                '}';
    }
}
