package com.example.classroom.classroom;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VeriTabani {

    private FirebaseDatabase veritabani;
    private DatabaseReference reference;
    private FirebaseAuth giris;
    private FirebaseUser girisYapanKullanici;
    private Context context;
    private Uyarilar uyarilar;
    private LocalVeriTabani localVeriTabani;
    private Kullanici kullanici=new Kullanici();

    public VeriTabani(Context context){
        veritabani=FirebaseDatabase.getInstance();
        reference=veritabani.getReference();
        giris=FirebaseAuth.getInstance();
        this.context=context;
        uyarilar=new Uyarilar(context);
        localVeriTabani=new LocalVeriTabani(context);

    }

    private void ekle(String id,String ad,String soyad,String degisken,String tur){
        reference=veritabani.getReference("kullanicilar");
        Kullanici kullanici=new Kullanici(ad,soyad,degisken,tur);
        kullanici.setId(id);
        reference.child(id).setValue(kullanici);
    }



    public void uyeEkle(final String tur,final String ad,final String soyad,String mail,String sifre,final String degisken){
        uyarilar.uyariBaslat("","İşleminiz devam ediyor. Lütfen bekleyin...");
        giris.createUserWithEmailAndPassword(mail,sifre)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            if (tur=="ÖĞRENCİ"||tur=="AKADEMİSYEN"){
                                ekle(giris.getCurrentUser().getUid(),ad,soyad,degisken,tur);
                                uyarilar.uyariDurdur();
                                uyarilar.kayitBasarili();
                            }else{
                                uyarilar.kayitBasarisiz();
                                uyarilar.uyariDurdur();
                            }
                        }else{
                            try{
                                uyarilar.uyariDurdur();
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                uyarilar.hata("Zayıf Parola!");
                            }catch (FirebaseAuthEmailException e){
                                uyarilar.hata("Geçersiz mail adresi!");
                            } catch(FirebaseAuthUserCollisionException e) {
                                uyarilar.hata("Aynı maile sahip kullanıcı mevcut!");
                            } catch(Exception e) {
                                Log.e(  "0", e.getMessage());
                            }

                        }
                    }
                });


    }

    public void girisYap(String mail,String sifre){
        uyarilar.uyariBaslat("","İşleminiz devam ediyor. Lütfen bekleyiniz...");
        giris.signInWithEmailAndPassword(mail,sifre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    uyarilar.uyariDurdur();
                    context.startActivity(new Intent(context,Anasayfa.class));
                }else{
                    uyarilar.uyariDurdur();

                    try{
                        uyarilar.hata("Giriş Yapılamadı!");
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        uyarilar.hata("Zayıf Parola!");
                    }catch (FirebaseAuthEmailException e){
                        uyarilar.hata("Hatalı mail!");
                    } catch(Exception e) {
                        Log.e(  "0", e.getMessage());
                    }


                }
            }

        });

    }







    public void tumKullanicilar(){
        girisYapanKullanici=giris.getCurrentUser();
        reference=veritabani.getReference("kullanicilar");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Kullanici kullanici=dataSnapshot1.getValue(Kullanici.class);
                    localVeriTabani.kullaniciEkle(kullanici);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void cikisYap(){
        giris.signOut();
        context.startActivity(new Intent(context,MainActivity.class));

    }

    public void mesajEkle(final MesajSablon mesaj){
        veritabani=FirebaseDatabase.getInstance();
        reference=veritabani.getReference("mesajlar");
        reference.push().setValue(mesaj);
        DatabaseReference reference1=veritabani.getReference("kullanicilar/"+mesaj.getGondericiId());
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Kullanici kullanici=dataSnapshot.getValue(Kullanici.class);
                String icerik=kullanici.getAd()+" "+kullanici.getSoyad()+" ' dan yeni mesajınız var";
                SimpleDateFormat bicim=new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
                Date tarih=new Date();
                String zaman=bicim.format(tarih);
                BildirimSablon bildirim=new BildirimSablon(mesaj.getAliciId(),mesaj.getGondericiId(),icerik,zaman);
                bildirimEkle(bildirim);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void bildirimEkle(BildirimSablon bildirimSablon){
        reference=veritabani.getReference("bildirimler").push();
        reference.setValue(bildirimSablon);

    }
    public void grupEkle(Grup gelenGrup)
    {
        uyarilar.uyariBaslat("","İşleminiz devam ediyor. Lütfen bekleyin...");
        reference=veritabani.getReference("gruplar");
        reference=reference.push();
        gelenGrup.setId(reference.getKey());
        reference.setValue(gelenGrup).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    uyarilar.uyariDurdur();
                    context.startActivity(new Intent(context,Anasayfa.class));
                }else{
                    uyarilar.uyariDurdur();
                    uyarilar.hata("İşleminiz Gerçekleştirilemedi");

                }
            }
        });
    }

    public void tumGruplar(){
        reference=veritabani.getReference("gruplar");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Grup grup=dataSnapshot1.getValue(Grup.class);
                    localVeriTabani.grupEkle(grup);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void grupKayit(Grup gelenGrup)
    {
        GrupKullanici detay = new GrupKullanici();
        detay.setKullaniciID(giris.getCurrentUser().getUid());
        detay.setGrupID(gelenGrup.getId());

        reference=veritabani.getReference("grup_detay");
        reference=reference.push();
        reference.setValue(detay);
    }

    public void tumGrupDetay(){
        reference=veritabani.getReference("grup_detay");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    GrupKullanici grupKullanici=dataSnapshot1.getValue(GrupKullanici.class);
                    localVeriTabani.grupDetayEkle(grupKullanici);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void paylasimYap(Paylasim gelenPaylasim)
    {
        uyarilar.uyariBaslat("","İşleminiz devam ediyor. Lütfen bekleyin...");
        reference=veritabani.getReference("paylasim");
        reference=reference.push();
        reference.setValue(gelenPaylasim).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    uyarilar.uyariDurdur();
                }else{
                    uyarilar.uyariDurdur();
                    uyarilar.hata("İşleminiz Gerçekleştirilemedi");

                }
            }
        });
    }

    public void tumPaylasimlar(){
        reference=veritabani.getReference("paylasim");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Paylasim paylasim=dataSnapshot1.getValue(Paylasim.class);
                    localVeriTabani.paylasimEkle(paylasim);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }




}
