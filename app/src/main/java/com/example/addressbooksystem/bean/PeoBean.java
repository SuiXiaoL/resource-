package com.example.addressbooksystem.bean;

public class PeoBean {
    private String beginZ;

    private String id;
    private String name;
    private String num;
    private String remark;
    private String sex;

    public String toString() {
        return "PeoBean{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", num='" + this.num + '\'' + ", sex='" + this.sex + '\'' + ", remark='" + this.remark + '\'' + ", beginZ='" + this.beginZ + '\'' + '}';
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num2) {
        this.num = num2;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex2) {
        this.sex = sex2;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark2) {
        this.remark = remark2;
    }

    public String getBeginZ() {
        return this.beginZ;
    }

    public void setBeginZ(String beginZ2) {
        this.beginZ = beginZ2;
    }

    public PeoBean() {
    }

    public PeoBean(String id, String name2, String num2, String sex2, String remark2, String beginZ2) {
        this.id = id;
        this.name = name2;
        this.num = num2;
        this.sex = sex2;
        this.remark = remark2;
        this.beginZ = beginZ2;
    }

    public PeoBean(String id, String name2, String num2, String sex2, String remark2) {
        this.id = id;
        this.name = name2;
        this.num = num2;
        this.sex = sex2;
        this.remark = remark2;
    }
}