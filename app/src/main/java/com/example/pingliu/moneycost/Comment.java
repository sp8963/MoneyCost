package com.example.pingliu.moneycost;

/**
 * Created by kancheng on 2017/12/6.
 */

public class Comment {
    private long list_id;
    private int money;
    private String name;
    private String datetime;

    @Override
    public String toString() {
        return "Comment{" +
                "list_id=" + list_id +
                ", money=" + money +
                ", name='" + name + '\'' +
                ", datetime='" + datetime + '\'' +
                '}';
    }

    public long getList_id() {
        return list_id;
    }

    public void setList_id(long list_id) {
        this.list_id = list_id;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDatestart() {
        return datetime;
    }

    public void setDatestart(String datestart) {
        this.datetime = datestart;
    }

}
