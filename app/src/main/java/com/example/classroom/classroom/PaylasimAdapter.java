package com.example.classroom.classroom;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by blacklake on 12/26/17.
 */

public class PaylasimAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private Context context;
    private List<Paylasim> paylasimlar;
    private Paylasim paylasim;
    private LocalVeriTabani localVeriTabani;

    public PaylasimAdapter(Context context,List<Paylasim> paylasimlar) {
        this.context=context;
        this.paylasimlar=paylasimlar;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        localVeriTabani = new LocalVeriTabani(context.getApplicationContext());
    }

    @Override
    public int getCount() {
        return paylasimlar.size();
    }

    @Override
    public Paylasim getItem(int position) {
        return paylasimlar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        paylasim=paylasimlar.get(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (View) inflater.inflate(R.layout.paylasim, null);
        }

        TextView sahip =(TextView) convertView.findViewById(R.id.sahipAdi);
        TextView metin =(TextView) convertView.findViewById(R.id.metin);
        TextView zaman =(TextView) convertView.findViewById(R.id.zaman);
        ImageView pp=(ImageView)convertView.findViewById(R.id.pp);
        Kullanici kullanici=localVeriTabani.kullaniciGetir(paylasimlar.get(position).getPaylasanID());
        if (kullanici.getTur().equals("ÖĞRENCİ")){
            pp.setImageResource(R.drawable.ogrenci);
        }else{
            pp.setImageResource(R.drawable.akademisyen);
        }
        sahip.setText(localVeriTabani.paylasimdanKisiBul(paylasim).getAd()+" "+kullanici.getSoyad());
        metin.setText(paylasim.getMetin());
        zaman.setText(paylasim.getPaylasimZaman());

        return convertView;
    }

    public void refresh(List<Paylasim> paylasimlar) {
        this.paylasimlar.clear();
        this.paylasimlar.addAll(paylasimlar);
        notifyDataSetChanged();
    }
}
