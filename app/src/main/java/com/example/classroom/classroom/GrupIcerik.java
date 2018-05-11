package com.example.classroom.classroom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by blacklake on 12/24/17.
 */

@SuppressLint("ValidFragment")
public class GrupIcerik extends Fragment {

    private VeriTabani veriTabani;
    private LocalVeriTabani localVeriTabani;
    private String grupID;
    private Activity context;
    private EditText paylasilacakMetin;
    private Paylasim paylasim;
    private List<Paylasim> paylasimlar;
    private ListView listView;
    private PaylasimAdapter paylasimAdapter;
    private Grup grup;

    @SuppressLint("ValidFragment")
    public GrupIcerik(String grupID, Activity context) {
        this.grupID=grupID;
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        veriTabani=new VeriTabani(context);
        localVeriTabani=new LocalVeriTabani(context);
        veriTabani.tumPaylasimlar();
        paylasimlar = localVeriTabani.grupPaylasimlariniGetir(grupID);
        grup=localVeriTabani.grupAdiGetir(grupID);
        getActivity().setTitle(grup.getAd());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_grup_icerik,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        paylasilacakMetin = (EditText)view.findViewById(R.id.paylasilacakEditText);

        listView=(ListView)view.findViewById(R.id.paylasimlarList);
        paylasimAdapter=new PaylasimAdapter(context,paylasimlar);//////////////////////////////
        listView.setAdapter(paylasimAdapter);

        Button floatingActionButton = (Button) view.findViewById(R.id.fabPaylasimYap);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date tarih = new Date();
                SimpleDateFormat basitTarih = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");

                paylasim =new Paylasim();
                paylasim.setGrupID(grupID);
                paylasim.setMetin(paylasilacakMetin.getText().toString().trim());
                paylasim.setPaylasanID(FirebaseAuth.getInstance().getCurrentUser().getUid());
                paylasim.setPaylasimZaman(String.valueOf(basitTarih.format(tarih)));
                veriTabani.paylasimYap(paylasim);
                paylasilacakMetin.setText("");

                veriTabani.tumPaylasimlar();
                paylasimlar = localVeriTabani.grupPaylasimlariniGetir(grupID);
                paylasimlar.add(paylasim);
                paylasimAdapter.refresh(paylasimlar);
            }
        });

    }


}
