package com.example.classroom.classroom;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Fatih on 8.08.2017.
 */

public class KullaniciAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Kullanici> kullanicilar;
    private Activity activity;
    private LocalVeriTabani localVeriTabani;
    public KullaniciAdapter(Activity activity, List<Kullanici> kullanicilar) {
        this.activity=activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.kullanicilar = kullanicilar;
        localVeriTabani=new LocalVeriTabani(activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return kullanicilar.size();
    }

    @Override
    public Kullanici getItem(int position) {

        return kullanicilar.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.satir_ici, null);
        TextView textView =(TextView) satirView.findViewById(R.id.textView2);
        ImageView imageView =(ImageView) satirView.findViewById(R.id.simge);
        textView.setText(kullanicilar.get(position).getAd()+" "+kullanicilar.get(position).getSoyad());
        if (kullanicilar.get(position).getTur().toString().trim().equals("ÖĞRENCİ")){
            imageView.setImageResource(R.drawable.ogrenci);
        }else{
            imageView.setImageResource(R.drawable.akademisyen);
        }
        satirView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                localVeriTabani.mesajKisiTabloBosalt();
                localVeriTabani.mesajGonderilecekKisiEkle(kullanicilar.get(position));
                activity.startActivity(new Intent(activity,MesajAyrinti.class));



            }
        });


        return satirView;
    }
}
