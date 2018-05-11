package com.example.classroom.classroom;

/**
 * Created by Fatih on 23.12.2017.
 */

public class MesajSablon {

    private String aliciId,gondericiId,mesaj,zaman;

    public MesajSablon(String aliciId,String gondericiId,String mesaj,String zaman){
        this.aliciId=aliciId;
        this.gondericiId=gondericiId;
        this.mesaj=mesaj;
        this.zaman=zaman;
    }
    public MesajSablon(){

    }

    public String getAliciId() {
        return aliciId;
    }

    public void setAliciId(String aliciId) {
        this.aliciId = aliciId;
    }

    public String getGondericiId() {
        return gondericiId;
    }

    public void setGondericiId(String gondericiId) {
        this.gondericiId = gondericiId;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }
}
