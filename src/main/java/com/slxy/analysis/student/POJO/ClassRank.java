package com.slxy.analysis.student.POJO;

public class ClassRank extends SchoolRank{
    private Integer ChineseClassRanking;
    private Integer MathClassRanking;
    private Integer EnglishClassRanking;
    private Integer PhysicsClassRanking;
    private Integer ChemistryClassRanking;
    private Integer BiologyClassRanking;
    private Integer HistoryClassRanking;
    private Integer PoliticsClassRanking;
    private Integer GeographyClassRanking;
    private Integer TechnologyClassRanking;
    private Integer TotalPointClassRanking;

    @Override
    public String toString() {
        return super.toString()+"ClassRank{" +
                "ChineseClassRanking=" + ChineseClassRanking +
                ", MathClassRanking=" + MathClassRanking +
                ", EnglishClassRanking=" + EnglishClassRanking +
                ", PhysicsClassRanking=" + PhysicsClassRanking +
                ", ChemistryClassRanking=" + ChemistryClassRanking +
                ", BiologyClassRanking=" + BiologyClassRanking +
                ", HistoryClassRanking=" + HistoryClassRanking +
                ", PoliticsClassRanking=" + PoliticsClassRanking +
                ", GeographyClassRanking=" + GeographyClassRanking +
                ", TechnologyClassRanking=" + TechnologyClassRanking +
                ", TotalPointClassRanking=" + TotalPointClassRanking +
                '}';
    }

    public Integer getChineseClassRanking() {
        return ChineseClassRanking;
    }

    public void setChineseClassRanking(Integer chineseClassRanking) {
        ChineseClassRanking = chineseClassRanking;
    }

    public Integer getMathClassRanking() {
        return MathClassRanking;
    }

    public void setMathClassRanking(Integer mathClassRanking) {
        MathClassRanking = mathClassRanking;
    }

    public Integer getEnglishClassRanking() {
        return EnglishClassRanking;
    }

    public void setEnglishClassRanking(Integer englishClassRanking) {
        EnglishClassRanking = englishClassRanking;
    }

    public Integer getPhysicsClassRanking() {
        return PhysicsClassRanking;
    }

    public void setPhysicsClassRanking(Integer physicsClassRanking) {
        PhysicsClassRanking = physicsClassRanking;
    }

    public Integer getChemistryClassRanking() {
        return ChemistryClassRanking;
    }

    public void setChemistryClassRanking(Integer chemistryClassRanking) {
        ChemistryClassRanking = chemistryClassRanking;
    }

    public Integer getBiologyClassRanking() {
        return BiologyClassRanking;
    }

    public void setBiologyClassRanking(Integer biologyClassRanking) {
        BiologyClassRanking = biologyClassRanking;
    }

    public Integer getHistoryClassRanking() {
        return HistoryClassRanking;
    }

    public void setHistoryClassRanking(Integer historyClassRanking) {
        HistoryClassRanking = historyClassRanking;
    }

    public Integer getPoliticsClassRanking() {
        return PoliticsClassRanking;
    }

    public void setPoliticsClassRanking(Integer politicsClassRanking) {
        PoliticsClassRanking = politicsClassRanking;
    }

    public Integer getGeographyClassRanking() {
        return GeographyClassRanking;
    }

    public void setGeographyClassRanking(Integer geographyClassRanking) {
        GeographyClassRanking = geographyClassRanking;
    }

    public Integer getTechnologyClassRanking() {
        return TechnologyClassRanking;
    }

    public void setTechnologyClassRanking(Integer technologyClassRanking) {
        TechnologyClassRanking = technologyClassRanking;
    }

    public Integer getTotalPointClassRanking() {
        return TotalPointClassRanking;
    }

    public void setTotalPointClassRanking(Integer totalPointClassRanking) {
        TotalPointClassRanking = totalPointClassRanking;
    }
}
