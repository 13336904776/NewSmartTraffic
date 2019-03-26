package com.example.mrzhang.newsmarttraffic.bean;

public class BillManageBean {
    String operator;
    int carId;
    int rechargeAmount;
    String date;

    public BillManageBean() {
    }

    public BillManageBean(String operator, int carId, int rechargeAmount, String date) {
        this.operator = operator;
        this.carId = carId;
        this.rechargeAmount = rechargeAmount;
        this.date = date;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(int rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
