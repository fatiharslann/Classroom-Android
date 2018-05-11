package com.example.classroom.classroom;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fatih on 18.12.2017.
 */

public class LocalVeriTabani extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="classroom.db";

    private static final String TABLE_KULLANICILAR="kullanicilar";
    private static final String TABLE_MESAJ_GONDERILECEK_KULLANICI="mesaj_gonderilecek";
    private static final String TABLE_GRUPLAR="gruplar";
    private static final String TABLE_GIRILECEK_GRUP="girilecek_grup";
    private static final String TABLE_GRUP_DETAY="grup_detay";
    private static final String TABLE_PAYLASIM="paylasim";



    private static final String SUTUN_KULLANICI_ID="kullanici_id";
    private static final String SUTUN_KULLANICI_ADI="kullanici_adi";
    private static final String SUTUN_KULLANICI_SOYADI="kullanici_soyadi";
    private static final String SUTUN_KULLANICI_TUR="kullanici_tur";
    private static final String SUTUN_KULLANICI_DEGISKEN="kullanici_degisken";

    private static final String SUTUN_GRUP_ID="grup_id";
    private static final String SUTUN_GRUP_ADI="grup_adi";


    private static final String SUTUN_PAYLASIM_METIN="paylasim_metin";
    private static final String SUTUN_PAYLASIM_ZAMAN="paylasim_zaman";



    private String table_kullanicilar="CREATE TABLE "+TABLE_KULLANICILAR+"("
            +SUTUN_KULLANICI_ID+" TEXT NOT NULL PRIMARY KEY,"
            +SUTUN_KULLANICI_ADI+" TEXT NOT NULL,"
            +SUTUN_KULLANICI_SOYADI+" TEXT NOT NULL,"
            +SUTUN_KULLANICI_TUR+" TEXT NOT NULL,"
            +SUTUN_KULLANICI_DEGISKEN+" TEXT NOT NULL"+
            ")";

    private String table_mesaj_gonderilecek_kisi="CREATE TABLE "+TABLE_MESAJ_GONDERILECEK_KULLANICI+"("
            +SUTUN_KULLANICI_ID+" TEXT,"
            +SUTUN_KULLANICI_ADI+" TEXT,"
            +SUTUN_KULLANICI_SOYADI+" TEXT,"
            +SUTUN_KULLANICI_TUR+" TEXT,"
            +SUTUN_KULLANICI_DEGISKEN+" TEXT"+
            ")";

    private String table_gruplar="CREATE TABLE "+TABLE_GRUPLAR+"("
            +SUTUN_GRUP_ID+" TEXT PRIMARY KEY,"
            +SUTUN_GRUP_ADI+" TEXT"+
            ")";

    private String table_girilecek_grup="CREATE TABLE "+TABLE_GIRILECEK_GRUP+"("
            +SUTUN_GRUP_ID+" TEXT,"
            +SUTUN_GRUP_ADI+" TEXT"+
            ")";


    private String table_grup_detay="CREATE TABLE "+TABLE_GRUP_DETAY+"("
            +SUTUN_GRUP_ID+" TEXT NOT NULL,"
            +SUTUN_KULLANICI_ID+" TEXT NOT NULL"+
            ")";

    private String table_paylasim="CREATE TABLE "+TABLE_PAYLASIM+"("
            +SUTUN_GRUP_ID+" TEXT NOT NULL,"
            +SUTUN_PAYLASIM_METIN+" TEXT NOT NULL,"
            +SUTUN_KULLANICI_ID+" TEXT NOT NULL,"
            +SUTUN_PAYLASIM_ZAMAN+" TEXT NOT NULL"+
            ")";



    private Context context;


    public LocalVeriTabani(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(table_kullanicilar);
        database.execSQL(table_mesaj_gonderilecek_kisi);
        database.execSQL(table_gruplar);
        database.execSQL(table_girilecek_grup);
        database.execSQL(table_grup_detay);
        database.execSQL(table_paylasim);

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_KULLANICILAR);
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_MESAJ_GONDERILECEK_KULLANICI);
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_GRUPLAR);
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_GIRILECEK_GRUP);
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_GRUP_DETAY);
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_PAYLASIM);
        onCreate(database);
    }

    public void kullaniciEkle(Kullanici kullanici){
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(SUTUN_KULLANICI_ID,kullanici.getId());
            values.put(SUTUN_KULLANICI_ADI,kullanici.getAd());
            values.put(SUTUN_KULLANICI_SOYADI,kullanici.getSoyad());
            values.put(SUTUN_KULLANICI_TUR,kullanici.getTur());
            values.put(SUTUN_KULLANICI_DEGISKEN,kullanici.getDegisken());
            db.insert(TABLE_KULLANICILAR,null,values);
            db.close();
        }catch (Exception e){

        }


    }


    public List<Kullanici> tumKullanicilariGetir(){
        List<Kullanici> kullanicilar=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String[] sutunlar={SUTUN_KULLANICI_ID,SUTUN_KULLANICI_ADI,SUTUN_KULLANICI_SOYADI,SUTUN_KULLANICI_TUR,SUTUN_KULLANICI_DEGISKEN};
        Cursor cursor=db.query(TABLE_KULLANICILAR,sutunlar,null,null,null,null,null);
        while (cursor.moveToNext()){
            Kullanici kullanici=new Kullanici();
            kullanici.setId(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_ID)));
            kullanici.setAd(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_ADI)));
            kullanici.setSoyad(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_SOYADI)));
            kullanici.setTur(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_TUR)));
            kullanici.setDegisken(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_DEGISKEN)));
            kullanicilar.add(kullanici);

        }
        return kullanicilar;
    }



    public Kullanici girisYapanKullanici(){
        List<Kullanici> kullanicilar=tumKullanicilariGetir();
        Kullanici girisyapan=new Kullanici();
        for (int i=0;i<kullanicilar.size();i++){
            if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(kullanicilar.get(i).getId())){
                girisyapan=kullanicilar.get(i);
                break;
            }
        }
        return girisyapan;


    }

    public void mesajGonderilecekKisiEkle(Kullanici kullanici){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(SUTUN_KULLANICI_ID,kullanici.getId());
        values.put(SUTUN_KULLANICI_ADI,kullanici.getAd());
        values.put(SUTUN_KULLANICI_SOYADI,kullanici.getSoyad());
        values.put(SUTUN_KULLANICI_TUR,kullanici.getTur());
        values.put(SUTUN_KULLANICI_DEGISKEN,kullanici.getDegisken());
        db.insert(TABLE_MESAJ_GONDERILECEK_KULLANICI,null,values);
        db.close();
    }

    public Kullanici mesajGonderilecekKisi(){
        Kullanici gonderilecek=new Kullanici();
        SQLiteDatabase db=this.getReadableDatabase();
        String[] sutunlar={SUTUN_KULLANICI_ID,SUTUN_KULLANICI_ADI,SUTUN_KULLANICI_SOYADI,SUTUN_KULLANICI_TUR,SUTUN_KULLANICI_DEGISKEN};
        Cursor cursor=db.query(TABLE_MESAJ_GONDERILECEK_KULLANICI,sutunlar,null,null,null,null,null);
        while (cursor.moveToNext()){
            gonderilecek=new Kullanici();
            gonderilecek.setId(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_ID)));
            gonderilecek.setAd(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_ADI)));
            gonderilecek.setSoyad(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_SOYADI)));
            gonderilecek.setTur(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_TUR)));
            gonderilecek.setDegisken(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_DEGISKEN)));
        }
        return gonderilecek;
    }

    public void mesajKisiTabloBosalt(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_MESAJ_GONDERILECEK_KULLANICI);
        db.close();
    }

    public void grupEkle(Grup grup){
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(SUTUN_GRUP_ID,grup.getId());
            values.put(SUTUN_GRUP_ADI,grup.getAd());
            db.insert(TABLE_GRUPLAR,null,values);
            db.close();
        }catch (Exception e){

        }
    }

    public List<Grup> tumGruplariGetir(){
        List<Grup> gruplar=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        String[] sutunlar={SUTUN_GRUP_ID,SUTUN_GRUP_ADI};
        Cursor cursor=db.query(TABLE_GRUPLAR,sutunlar,null,null,null,null,null);
        while (cursor.moveToNext()){
            Grup grup = new Grup();
            grup.setId(cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ID)));
            grup.setAd(cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ADI)));
            gruplar.add(grup);

        }
        return gruplar;
    }

    public void girilecekGrupEkle(Grup grup){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(SUTUN_GRUP_ID,grup.getId());
        values.put(SUTUN_GRUP_ADI,grup.getAd());
        db.insert(TABLE_GIRILECEK_GRUP,null,values);
        db.close();
    }
    public Grup girilecekGrup(){
        Grup girilecek=new Grup();
        SQLiteDatabase db=this.getReadableDatabase();
        String[] sutunlar={SUTUN_GRUP_ID,SUTUN_GRUP_ADI};
        Cursor cursor=db.query(TABLE_GIRILECEK_GRUP,sutunlar,null,null,null,null,null);
        while (cursor.moveToNext()){
            girilecek=new Grup();
            girilecek.setId(cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ID)));
            girilecek.setAd(cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ADI)));
        }
        return girilecek;
    }
    public void girilecekGrupTabloBosalt(){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_GIRILECEK_GRUP);
        db.close();
    }
//////////////////////////////////////////////////////////////////////////

    public void grupDetayEkle(GrupKullanici grupKullanici){
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(SUTUN_GRUP_ID,grupKullanici.getGrupID());
            values.put(SUTUN_KULLANICI_ID,grupKullanici.getKullaniciID());
            db.insert(TABLE_GRUP_DETAY,null,values);
            db.close();
        }catch (Exception e){

        }
    }

    public List<Grup> kullaniciGruplariniGetir(){

        Kullanici girisYapanKullanici=girisYapanKullanici();
        List<Grup> gruplar=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT DISTINCT "+TABLE_GRUPLAR+"."+SUTUN_GRUP_ID+", "+TABLE_GRUPLAR+"."+SUTUN_GRUP_ADI+", "+TABLE_GRUP_DETAY+"."+SUTUN_KULLANICI_ID+" FROM "+TABLE_GRUPLAR+" INNER JOIN "+TABLE_GRUP_DETAY+" ON "+TABLE_GRUPLAR+"."+SUTUN_GRUP_ID+" = "+TABLE_GRUP_DETAY+"."+SUTUN_GRUP_ID+" WHERE "+TABLE_GRUP_DETAY+"."+SUTUN_KULLANICI_ID+" = '"+girisYapanKullanici.getId()+"'",null);
        while (cursor.moveToNext()){
            Grup grup = new Grup();
            grup.setId(cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ID)));
            grup.setAd(cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ADI)));
            gruplar.add(grup);

        }
        return gruplar;
    }

    ///////////////////////////////////////////////////////////////////

    public void paylasimEkle(Paylasim paylasim){
        try {
            SQLiteDatabase db=this.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(SUTUN_GRUP_ID,paylasim.getGrupID());
            values.put(SUTUN_PAYLASIM_METIN,paylasim.getMetin());
            values.put(SUTUN_KULLANICI_ID,paylasim.getPaylasanID());
            values.put(SUTUN_PAYLASIM_ZAMAN,paylasim.getPaylasimZaman());
            db.insert(TABLE_PAYLASIM,null,values);
            db.close();
        }catch (Exception e){

        }
    }

    public List<Paylasim> tumPaylasimlariGetir(){
        List<Paylasim> paylasimlar=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT DISTINCT "+SUTUN_GRUP_ID+", "+SUTUN_PAYLASIM_METIN+", "+SUTUN_KULLANICI_ID+", "+SUTUN_PAYLASIM_ZAMAN+" FROM "+TABLE_PAYLASIM,null);
        while (cursor.moveToNext()){
            Paylasim paylasim = new Paylasim();
            paylasim.setGrupID(cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ID)));
            paylasim.setMetin(cursor.getString(cursor.getColumnIndex(SUTUN_PAYLASIM_METIN)));
            paylasim.setPaylasanID(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_ID)));
            paylasim.setPaylasimZaman(cursor.getString(cursor.getColumnIndex(SUTUN_PAYLASIM_ZAMAN)));
            paylasimlar.add(paylasim);

        }
        return paylasimlar;
    }

    public List<Paylasim> grupPaylasimlariniGetir(String grupID){
        List<Paylasim> paylasimlar=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT DISTINCT "+SUTUN_GRUP_ID+", "+SUTUN_PAYLASIM_METIN+", "+SUTUN_KULLANICI_ID+", "+SUTUN_KULLANICI_ID+", "+SUTUN_PAYLASIM_ZAMAN+" FROM "+TABLE_PAYLASIM,null);
        while (cursor.moveToNext()){
            Paylasim paylasim = new Paylasim();
            String degiskenID=cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ID));
            paylasim.setGrupID(degiskenID);
            paylasim.setMetin(cursor.getString(cursor.getColumnIndex(SUTUN_PAYLASIM_METIN)));
            paylasim.setPaylasanID(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_ID)));
            paylasim.setPaylasimZaman(cursor.getString(cursor.getColumnIndex(SUTUN_PAYLASIM_ZAMAN)));

            if (degiskenID.equals(grupID))
            {
                paylasimlar.add(paylasim);
            }

        }

        return paylasimlar;
    }
    public Grup grupAdiGetir(String grupID){
        SQLiteDatabase db=this.getReadableDatabase();
        Grup grup=new Grup();
        String[] sutunlar={SUTUN_GRUP_ID,SUTUN_GRUP_ADI};
        Cursor cursor=db.query(TABLE_GIRILECEK_GRUP,sutunlar,null,null,null,null,null);
        while (cursor.moveToNext()){
            if (cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ID)).equalsIgnoreCase(grupID)){
                grup.setId(cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ID)));
                grup.setAd(cursor.getString(cursor.getColumnIndex(SUTUN_GRUP_ADI)));
            }

        }


        return grup;
    }
    public Kullanici kullaniciGetir(String kId){
        List<Kullanici> kullanicilar=tumKullanicilariGetir();
        Kullanici kullanici=new Kullanici();
        for (int i=0;i<kullanicilar.size();i++){
            if (kId.equals(kullanicilar.get(i).getId())){
                kullanici=kullanicilar.get(i);
                break;
            }
        }
        return kullanici;



    }


    public Kullanici paylasimdanKisiBul(Paylasim paylasim)
    {
        List<Kullanici> kullanicilar=tumKullanicilariGetir();
        Kullanici paylasan=new Kullanici();
        for (int i=0;i<kullanicilar.size();i++){////////////////////////////////////////////////////
            if (paylasim.getPaylasanID().equals(kullanicilar.get(i).getId())){
                paylasan=kullanicilar.get(i);
                break;
            }
        }
        return paylasan;
    }

    public Grup paylasimdanGrupBul(Paylasim paylasim)
    {
        List<Grup> gruplar = tumGruplariGetir();
        Grup paylasilanGrup=new Grup();
        for (int i=0;i<gruplar.size();i++){////////////////////////////////////////////////////
            if (paylasim.getMetin().equals(gruplar.get(i).getId())){
                paylasilanGrup=gruplar.get(i);
                break;
            }
        }
        return paylasilanGrup;
    }




}
