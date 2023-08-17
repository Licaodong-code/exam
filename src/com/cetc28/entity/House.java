package com.cetc28.entity;

import java.sql.Date;

/**
 * @author Licaodong
 * @project exam
 * @date 2023/8/3 16:07
 **/
public class House {
    private String id;
    private String address;
    private double area;
    private double price;
    private int status;
    private Date rent_date;

    public House() {
    }

    public House(String id, String address, double area, double price, int status, Date rent_date) {
        this.id = id;
        this.address = address;
        this.area = area;
        this.price = price;
        this.status = status;
        this.rent_date = rent_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getRent_date() {
        return rent_date;
    }

    public void setRent_date(Date rent_date) {
        this.rent_date = rent_date;
    }

    @Override
    public String toString() {
        return "House{" +
                "id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", area=" + area +
                ", price=" + price +
                ", status=" + status +
                ", rent_date=" + rent_date +
                '}';
    }
}
