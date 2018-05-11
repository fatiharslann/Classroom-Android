package com.example.classroom.classroom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Fatih on 23.12.2017.
 */

public class BildirimAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private List<BildirimSablon> bildirimler;
    private Activity activity;
    private LocalVeriTabani localVeriTabani;
    public BildirimAdapter(Activity activity, List<BildirimSablon> bildirimler) {
        this.activity=activity;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.bildirimler = bildirimler;
        localVeriTabani=new LocalVeriTabani(activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return bildirimler.size();
    }

    @Override
    public BildirimSablon getItem(int position) {

        return bildirimler.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View satirView;

        satirView = mInflater.inflate(R.layout.bildirim_sablon, null);
        TextView textView =(TextView) satirView.findViewById(R.id.bildirim_icerik);
        TextView textView2 =(TextView) satirView.findViewById(R.id.bildirim_tarih);
        String tarih=bildirimler.get(position).getTarih().substring(0,10);
        String saat=bildirimler.get(position).getTarih().substring(10);

        Button bildirimsayisi=(Button)satirView.findViewById(R.id.button2);
        bildirimsayisi.setText(String.valueOf(position+1));

        textView.setText(bildirimler.get(position).getIcerik());
        textView2.setText(tarih+"\n"+saat);

        return satirView;
    }
}
