package com.example.classroom.classroom;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by blacklake on 12/22/17.
 */

public class GrupEkle extends Fragment {


    private VeriTabani veriTabani;
    private EditText textGrupAdi;
    private LocalVeriTabani localVeriTabani;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        veriTabani=new VeriTabani(getActivity());


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_grup_ekle,container,false);


        textGrupAdi =(EditText)rootView.findViewById(R.id.grupAdi);
        localVeriTabani=new LocalVeriTabani(getActivity().getApplicationContext());

        return  rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button btn_grupEkle = (Button) view.findViewById(R.id.btn_grupekle);
        btn_grupEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Grup yeniGrup=new Grup();
                yeniGrup.setAd(textGrupAdi.getText().toString().trim());
                veriTabani.grupEkle(yeniGrup);

                localVeriTabani.girilecekGrupTabloBosalt();
                localVeriTabani.girilecekGrupEkle(yeniGrup);
                veriTabani.grupKayit(localVeriTabani.girilecekGrup());

            }
        });
    }
}
