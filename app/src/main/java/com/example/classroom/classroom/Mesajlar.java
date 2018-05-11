package com.example.classroom.classroom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Fatih on 20.12.2017.
 */

@SuppressLint("ValidFragment")
public class Mesajlar extends Fragment{

    private KullaniciAdapter adapter;
    private List<Kullanici> mesajlasilanlar=new ArrayList<>();
    private List<Kullanici> tumKullanicilar;
    private ListView listView;
    private Activity context;
    private LocalVeriTabani localVeriTabani;
    private DatabaseReference reference,reference2;
    private FirebaseUser girisYapanKullanici;

    @SuppressLint("ValidFragment")
    public Mesajlar(Activity context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.activity_mesajlar,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView=(ListView)view.findViewById(R.id.liste_mesajlasilanlar);
        adapter=new KullaniciAdapter(getActivity(),mesajlasilanlar);
        listView.setAdapter(adapter);
        localVeriTabani=new LocalVeriTabani(getActivity());
        tumKullanicilar=localVeriTabani.tumKullanicilariGetir();
        reference=FirebaseDatabase.getInstance().getReference("mesajlar");

        girisYapanKullanici= FirebaseAuth.getInstance().getCurrentUser();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    MesajSablon mesajSablon=snapshot.getValue(MesajSablon.class);
                    if (girisYapanKullanici.getUid().equalsIgnoreCase(mesajSablon.getAliciId())){
                        reference2=FirebaseDatabase.getInstance().getReference("kullanicilar/"+mesajSablon.getGondericiId());
                        reference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Kullanici kullanici=dataSnapshot.getValue(Kullanici.class);
                                if (!kullaniciVarMi(mesajlasilanlar,kullanici)){
                                    mesajlasilanlar.add(kullanici);

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }else if (girisYapanKullanici.getUid().equalsIgnoreCase(mesajSablon.getGondericiId())){
                        reference2=FirebaseDatabase.getInstance().getReference("kullanicilar/"+mesajSablon.getAliciId());
                        reference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Kullanici kullanici=dataSnapshot.getValue(Kullanici.class);
                                if (!kullaniciVarMi(mesajlasilanlar,kullanici)){
                                    mesajlasilanlar.add(kullanici);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FloatingActionButton floatingActionButton=(FloatingActionButton)view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new MesajYazilacakKisiler(context);
                MainActivity.tempFragment=fragment;
                if (fragment!=null){
                    FragmentTransaction transaction=getFragmentManager().beginTransaction();
                    transaction.replace(Mesajlar.this.getId(), fragment);
                    transaction.commit();
                }
            }
        });

    }

    public boolean kullaniciVarMi(List<Kullanici> kullanicilar,Kullanici kullanici){
        boolean temp=false;
        for (int i=0;i<kullanicilar.size();i++){
            if (kullanicilar.get(i).getId().equalsIgnoreCase(kullanici.getId())){
               temp=true;
               break;
            }
        }
        return temp;
    }
}
