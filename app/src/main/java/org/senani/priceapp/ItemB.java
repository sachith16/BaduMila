package org.senani.priceapp;

/**
 * Created by sachith on 3/5/18.
 */

public class ItemB {

    private String n;
    private float p;
    private String t;
    private double q=0;

    public ItemB(String n, float p, String t, double q) {
        this.n = n;
        this.p = p;
        this.t = t;
        this.q = q;
    }

    public ItemB() {

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


    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }
}
