package com.example.classroom.classroom;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Fatih on 14.12.2017.
 */

public class InternetKontrolu {
    private AppCompatActivity appCompatActivity;
    public InternetKontrolu(AppCompatActivity a){
        appCompatActivity=a;
    }
    boolean internetBaglantiKontrol() {

        ConnectivityManager baglantiYonetici = (ConnectivityManager)appCompatActivity.getSystemService (Context.CONNECTIVITY_SERVICE);

        if (
        baglantiYonetici.getActiveNetworkInfo().isAvailable()&&
        baglantiYonetici.getActiveNetworkInfo().isConnected()&&
        baglantiYonetici.getActiveNetworkInfo() != null) {
            return true;
        } else {
            return false;
        }
    }

}
