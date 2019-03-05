package org.senani.priceapp;

public class Item {

    private String n;
    private float p;
    private String t;

    public Item(String n, float p, String t) {
        this.n = n;
        this.p = p;
        this.setT(t);
    }

    public Item() {

    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }


    public float getP() {
        return p;
    }

    public void setP(float p) {
        this.p = p;
    }


    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }
}
