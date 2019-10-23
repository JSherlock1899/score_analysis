package com.slxy.analysis.student.POJO;

import java.io.Serializable;

public class StudentBasic implements Serializable {
    private  String id;
    private  String password;
    private  String name;
    private  String sex;
    private  String nation;
    private  String id_card;
    private  String enter_time;
    private  String telephone;
    private  String native_place;
    private  String homeAddress;
    private  String school;
    private  String picture;
    private  String classNumber;

    @Override
    public String toString() {
        return "StudentBasic{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", id_card='" + id_card + '\'' +
                ", enter_time='" + enter_time + '\'' +
                ", telephone='" + telephone + '\'' +
                ", native_place='" + native_place + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", school='" + school + '\'' +
                ", picture='" + picture + '\'' +
                ", classNumber='" + classNumber + '\'' +
                '}';
    }

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

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getEnter_time() {
        return enter_time;
    }

    public void setEnter_time(String enter_time) {
        this.enter_time = enter_time;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNative_place() {
        return native_place;
    }

    public void setNative_place(String native_place) {
        this.native_place = native_place;
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
