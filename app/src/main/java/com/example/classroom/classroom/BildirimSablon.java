package com.example.classroom.classroom;

/**
 * Created by Fatih on 23.12.2017.
 */

public class BildirimSablon {
    private String aliciId,gondericiId,icerik,tarih;

    public BildirimSablon(){

    }

    public BildirimSablon(String aliciId,String gondericiId,String icerik,String tarih){
        this.aliciId=aliciId;
        this.gondericiId=gondericiId;
        this.icerik=icerik;
        this.tarih=tarih;
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

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
