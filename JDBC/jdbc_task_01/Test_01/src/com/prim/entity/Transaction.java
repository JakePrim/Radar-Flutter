package com.prim.entity;

public class Transaction {
    private int id;
    private String cardid;//交易卡号
    private String tratype;//交易类型 转入 或者 转出
    private double tramoney;//交易金额
    private String tradate;//交易日期

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getTratype() {
        return tratype;
    }

    public void setTratype(String tratype) {
        this.tratype = tratype;
    }

    public double getTramoney() {
        return tramoney;
    }

    public void setTramoney(double tramoney) {
        this.tramoney = tramoney;
    }

    public String getTradate() {
        return tradate;
    }

    public void setTradate(String tradate) {
        this.tradate = tradate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", cardid='" + cardid + '\'' +
                ", tratype='" + tratype + '\'' +
                ", tramoney=" + tramoney +
                ", tradate='" + tradate + '\'' +
                '}';
    }
}
