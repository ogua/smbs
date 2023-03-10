package com.oguses.cartcount.Models;

public class Productresponse {

    private String success;
    private String id;
    private String name;
    private String img;
    private String ptype;
    private String amnt;
    private String dscnt;
    private String desc;


    public Productresponse() {
    }

    public Productresponse(String success, String id, String name, String img, String ptype, String amnt, String dscnt, String desc) {
        this.success = success;
        this.id = id;
        this.name = name;
        this.img = img;
        this.ptype = ptype;
        this.amnt = amnt;
        this.dscnt = dscnt;
        this.desc = desc;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getAmnt() {
        return amnt;
    }

    public void setAmnt(String amnt) {
        this.amnt = amnt;
    }

    public String getDscnt() {
        return dscnt;
    }

    public void setDscnt(String dscnt) {
        this.dscnt = dscnt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
