package com.appix.storemangaementapp;

public class Store {
    int id;
    String name;
    String item_scale;
    String item_price;
    String item_quantity;
    float total_price;
    String qeemat_frokht;
  public  Store(){

    }
    public Store(int id, String name, String item_scale, String item_price, String item_quantity, float total_price,String qeemat_frokht) {
        this.id = id;
        this.name = name;
        this.item_scale = item_scale;
        this.item_price = item_price;
        this.item_quantity = item_quantity;
        this.total_price = total_price;
        this.qeemat_frokht = qeemat_frokht;
    }

    public Store(String name, String item_scale, String item_price, String item_quantity, float total_price,String qeemat_frokht) {
        this.name = name;
        this.item_scale = item_scale;
        this.item_price = item_price;
        this.item_quantity = item_quantity;
        this.total_price = total_price;
        this.qeemat_frokht = qeemat_frokht;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem_scale() {
        return item_scale;
    }

    public void setItem_scale(String item_scale) {
        this.item_scale = item_scale;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(String item_quantity) {
        this.item_quantity = item_quantity;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQeemat_frokht() {
        return qeemat_frokht;
    }

    public void setQeemat_frokht(String qeemat_frokht) {
        this.qeemat_frokht = qeemat_frokht;
    }
}