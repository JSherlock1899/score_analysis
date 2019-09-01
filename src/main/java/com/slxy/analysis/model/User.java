package com.slxy.analysis.model;

public class User {

    private String id;
    private String password;
    private String name;
    private String sex;
    private String nation;
    private String idCard;
    private String telephone;

    /**
     * 权限角色
     */
    private String role;
    //定义用户权限角色常量
    public static final String  STUDENT = "student";
    public static final String  TEACHER = "teacher";
    public static final String  HEACHER_TEACHER = "head_teacher";
    public static final String  GRADE_TEACHER = "grade_leader";
    public static final String  ADMIN = "admin";
    public static final String  SUPER_ADMIN = "super_admin";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
