package com.example.classroom.classroom;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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

/**
 * Created by Fatih on 21.12.2017.
 */

public class MesajAyrinti extends AppCompatActivity {

    private TextView mesaj,textViewesajlar;
    private Button gonder;
    private Uyarilar uyarilar;
    private String mesaj_text;
    private LocalVeriTabani localVeriTabani;
    private VeriTabani veriTabani;
    private ListView listView;
    private ArrayList<MesajSablon> mesajList=new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseUser girisYapanKullanici;
    private MesajAdapter adapter;
    private Kullanici mesajGonderilecekKisi;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_ayrinti);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uyarilar=new Uyarilar(MesajAyrinti.this);
        localVeriTabani=new LocalVeriTabani(MesajAyrinti.this);
        veriTabani=new VeriTabani(MesajAyrinti.this);
        girisYapanKullanici=FirebaseAuth.getInstance().getCurrentUser();
        mesajGonderilecekKisi=localVeriTabani.mesajGonderilecekKisi();

        textViewesajlar=(TextView)findViewById(R.id.mesajlarText);
        textViewesajlar.setText(localVeriTabani.mesajGonderilecekKisi().getAd()+" "+localVeriTabani.mesajGonderilecekKisi().getSoyad()+" ile Mesajlarım");


        mesaj=(EditText)findViewById(R.id.mesaj);

        mesaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.smoothScrollToPosition(listView.getCount());
            }
        });

        gonder=(Button)findViewById(R.id.gonder);

        listView=(ListView)findViewById(R.id.mesajlar_listesi);
        listView.smoothScrollToPosition(listView.getCount());
        listView.setDivider(null);
        adapter=new MesajAdapter(MesajAyrinti.this,mesajList,FirebaseAuth.getInstance().getCurrentUser());
        listView.setAdapter(adapter);
        MesajAyrinti.this.setTitle(localVeriTabani.mesajGonderilecekKisi().getAd()+" "+localVeriTabani.mesajGonderilecekKisi().getSoyad());
        gonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesaj_text=mesaj.getText().toString();
                if (!TextUtils.isEmpty(mesaj_text)){
                    SimpleDateFormat bicim=new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
                    Date tarih=new Date();
                    String zaman=bicim.format(tarih);
                    MesajSablon yeniMesaj=new MesajSablon(localVeriTabani.mesajGonderilecekKisi().getId(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            mesaj_text,
                            zaman);

                    veriTabani.mesajEkle(yeniMesaj);

                    adapter.notifyDataSetChanged();
                    listView.smoothScrollToPosition(listView.getCount());
                    mesaj.setText("");
                }else{
                    uyarilar.hata("Boş mesaj!");
                }

            }
        });
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("mesajlar");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mesajList.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    MesajSablon gelen=dataSnapshot1.getValue(MesajSablon.class);
                    if (girisYapanKullanici.getUid().equalsIgnoreCase(gelen.getGondericiId())&&mesajGonderilecekKisi.getId().equalsIgnoreCase(gelen.getAliciId())){
                        mesajList.add(gelen);
                    }else if(girisYapanKullanici.getUid().equalsIgnoreCase(gelen.getAliciId())&&mesajGonderilecekKisi.getId().equalsIgnoreCase(gelen.getGondericiId())){
                        mesajList.add(gelen);
                    }


                }

                adapter.notifyDataSetChanged();
                listView.smoothScrollToPosition(listView.getCount());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void scrollBottom() {

    }



}
