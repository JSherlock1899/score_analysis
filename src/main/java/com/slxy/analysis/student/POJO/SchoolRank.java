package com.slxy.analysis.student.POJO;

import com.slxy.analysis.teacher.model.Grade;


public class SchoolRank extends Grade {

    private Integer ChineseSchoolRanking;
    private Integer MathSchoolRanking;
    private Integer EnglishSchoolRanking;
    private Integer PhysicsSchoolRanking;
    private Integer ChemistrySchoolRanking;
    private Integer biologySchoolRanking;
    private Integer HistorySchoolRanking;
    private Integer PoliticsSchoolRanking;
    private Integer GeographySchoolRanking;
    private Integer TechnologySchoolRanking;
    private Integer TotalPointSchoolRanking;

    @Override
    public String toString() {
        return super.toString()+"SchoolRank{" +
                "ChineeseSchoolRanking=" + ChineseSchoolRanking +
                ", MathSchoolRanking=" + MathSchoolRanking +
                ", EnglishSchoolRanking=" + EnglishSchoolRanking +
                ", PhysicsSchoolRanking=" + PhysicsSchoolRanking +
                ", ChemistrySchoolRanking=" + ChemistrySchoolRanking +
                ", biologySchoolRanking=" + biologySchoolRanking +
                ", HistorySchoolRanking=" + HistorySchoolRanking +
                ", PoliticsSchoolRanking=" + PoliticsSchoolRanking +
                ", GeographySchoolRanking=" + GeographySchoolRanking +
                ", TechnologySchoolRanking=" + TechnologySchoolRanking +
                ", TotalPointSchoolRanking=" + TotalPointSchoolRanking +
                '}';
    }

    public Integer getChineseSchoolRanking() {
        return ChineseSchoolRanking;
    }

    public void setChineseSchoolRanking(Integer chineseSchoolRanking) {
        ChineseSchoolRanking = chineseSchoolRanking;
    }

    public Integer getMathSchoolRanking() {
        return MathSchoolRanking;
    }

    public void setMathSchoolRanking(Integer mathSchoolRanking) {
        MathSchoolRanking = mathSchoolRanking;
    }

    public Integer getEnglishSchoolRanking() {
        return EnglishSchoolRanking;
    }

    public void setEnglishSchoolRanking(Integer englishSchoolRanking) {
        EnglishSchoolRanking = englishSchoolRanking;
    }

    public Integer getPhysicsSchoolRanking() {
        return PhysicsSchoolRanking;
    }

    public void setPhysicsSchoolRanking(Integer physicsSchoolRanking) {
        PhysicsSchoolRanking = physicsSchoolRanking;
    }

    public Integer getChemistrySchoolRanking() {
        return ChemistrySchoolRanking;
    }

    public void setChemistrySchoolRanking(Integer chemistrySchoolRanking) {
        ChemistrySchoolRanking = chemistrySchoolRanking;
    }

    public Integer getBiologySchoolRanking() {
        return biologySchoolRanking;
    }

    public void setBiologySchoolRanking(Integer biologySchoolRanking) {
        this.biologySchoolRanking = biologySchoolRanking;
    }

    public Integer getHistorySchoolRanking() {
        return HistorySchoolRanking;
    }

    public void setHistorySchoolRanking(Integer historySchoolRanking) {
        HistorySchoolRanking = historySchoolRanking;
    }

    public Integer getPoliticsSchoolRanking() {
        return PoliticsSchoolRanking;
    }

    public void setPoliticsSchoolRanking(Integer politicsSchoolRanking) {
        PoliticsSchoolRanking = politicsSchoolRanking;
    }

    public Integer getGeographySchoolRanking() {
        return GeographySchoolRanking;
    }

    public void setGeographySchoolRanking(Integer geographySchoolRanking) {
        GeographySchoolRanking = geographySchoolRanking;
    }

    public Integer getTechnologySchoolRanking() {
        return TechnologySchoolRanking;
    }

    public void setTechnologySchoolRanking(Integer technologySchoolRanking) {
        TechnologySchoolRanking = technologySchoolRanking;
    }

    public Integer getTotalPointSchoolRanking() {
        return TotalPointSchoolRanking;
    }

    public void setTotalPointSchoolRanking(Integer totalPointSchoolRanking) {
        TotalPointSchoolRanking = totalPointSchoolRanking;
    }
}
