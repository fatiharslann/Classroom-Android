package com.example.classroom.classroom;

/**
 * Created by Fatih on 20.12.2017.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.List;

/**
 * Created by Fatih on 20.12.2017.
 */

@SuppressLint("ValidFragment")
public class MesajYazilacakKisiler extends Fragment {

    private KullaniciAdapter adapter;
    private List<Kullanici> kullanicilar;
    private LocalVeriTabani localVeriTabani;
    private ListView listView;
    private Activity context;
    private VeriTabani veriTabani;

    @SuppressLint("ValidFragment")
    public MesajYazilacakKisiler(Activity context){
        this.context=context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        veriTabani=new VeriTabani(context);
        localVeriTabani=new LocalVeriTabani(context);
        veriTabani.tumKullanicilar();
        kullanicilar=localVeriTabani.tumKullanicilariGetir();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_mesajlar_yazilacak_kisiler,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=(ListView)view.findViewById(R.id.liste_mesajlar);
        adapter=new KullaniciAdapter(context,kullanicilar);

        listView.setAdapter(adapter);
    }


}
