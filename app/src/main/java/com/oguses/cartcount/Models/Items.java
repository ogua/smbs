package com.oguses.cartcount.Models;

public class Items {

    private String id;
    private String appid;
    private String name;
    private String img;
    private String ptype;
    private String price;
    private String qty;
    private String dscnt;
    private String total;
    private String date;

    public Items() {
    }

    public Items(String id, String appid, String name, String img, String ptype, String price, String qty, String dscnt, String total, String date) {
        this.id = id;
        this.appid = appid;
        this.name = name;
        this.img = img;
        this.ptype = ptype;
        this.price = price;
        this.qty = qty;
        this.dscnt = dscnt;
        this.total = total;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDscnt() {
        return dscnt;
    }

    public void setDscnt(String dscnt) {
        this.dscnt = dscnt;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
