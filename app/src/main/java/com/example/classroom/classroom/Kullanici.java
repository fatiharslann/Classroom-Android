package com.example.classroom.classroom;

/**
 * Created by Fatih on 15.12.2017.
 */

public class Kullanici {
    private String id,ad,soyad,degisken,tur;



    public Kullanici(String ad, String soyad, String degisken,String tur){
        this.degisken=degisken;
        this.soyad=soyad;
        this.ad=ad;
        this.tur=tur;

    }
    public Kullanici(){

    }
    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getDegisken() {
        return degisken;
    }

    public void setDegisken(String degisken) {
        this.degisken = degisken;
    }
}
