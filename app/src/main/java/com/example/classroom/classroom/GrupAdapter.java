package com.example.classroom.classroom;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

/**
 * Created by blacklake on 12/22/17.
 */

public class GrupAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Grup> gruplar;
    private Activity activity;
    private LocalVeriTabani localVeriTabani;
    private VeriTabani veriTabani;
    private String fonk;//gir - kaydol
    private Gruplar anaGruplar;
    private TumGruplar anaTumGruplar;
    private Grup grup;
    private Uyarilar uyarilar;


    public GrupAdapter(Activity activity, Gruplar anaGruplar, List<Grup> gruplar, String fonk) {
        this.activity=activity;
        this.anaGruplar=anaGruplar;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.gruplar = gruplar;
        this.fonk=fonk;
        localVeriTabani=new LocalVeriTabani(activity.getApplicationContext());
        veriTabani=new VeriTabani(activity.getApplicationContext());;
        uyarilar=new Uyarilar(activity);
    }
    public GrupAdapter(Activity activity, TumGruplar anaTumGruplar, List<Grup> gruplar, String fonk) {
        this.activity=activity;
        this.anaTumGruplar=anaTumGruplar;
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.gruplar = gruplar;
        this.fonk=fonk;
        localVeriTabani=new LocalVeriTabani(activity.getApplicationContext());
        veriTabani=new VeriTabani(activity.getApplicationContext());
    }

    @Override
    public int getCount() {
        return gruplar.size();
    }

    @Override
    public Grup getItem(int position) {

        return gruplar.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View satirView;

        grup=gruplar.get(position);
        satirView = mInflater.inflate(R.layout.satir_ici, null);
        TextView textView =(TextView) satirView.findViewById(R.id.textView2);
        ImageView imageView =(ImageView) satirView.findViewById(R.id.simge);
        textView.setText(grup.getAd());
        imageView.setImageResource(R.drawable.ic_group_black_24dp);

        satirView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (fonk.equals("kaydol"))
                {
                    Toast.makeText(activity, gruplar.get(position).getAd(), Toast.LENGTH_SHORT).show();
                    localVeriTabani.girilecekGrupTabloBosalt();
                    localVeriTabani.girilecekGrupEkle(gruplar.get(position));
                    veriTabani.grupKayit(localVeriTabani.girilecekGrup());

                    Fragment fragment = new Gruplar(activity,gruplar.get(position));
                    MainActivity.tempFragment = fragment;
                    if (fragment != null) {
                        FragmentTransaction transaction = anaTumGruplar.getFragmentManager().beginTransaction();
                        transaction.replace(anaTumGruplar.getId(), fragment);
                        transaction.commit();
                    }

                }
                if (fonk.equals("gir"))
                {
                    Fragment fragment = new GrupIcerik(gruplar.get(position).getId(),activity);
                    MainActivity.tempFragment=fragment;
                    if (fragment != null) {
                        FragmentTransaction transaction = anaGruplar.getFragmentManager().beginTransaction();
                        transaction.replace(anaGruplar.getId(), fragment);
                        transaction.commit();
                    }
                }

            }
        });

        return satirView;
    }

    public void refresh(List<Grup> gruplar) {
        this.gruplar.clear();
        this.gruplar.addAll(gruplar);
        notifyDataSetChanged();
    }

}
