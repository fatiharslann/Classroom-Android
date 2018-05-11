package com.example.classroom.classroom;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * Created by Fatih on 23.12.2017.
 */

public class Bildirimler extends Fragment {

    private ListView listView;
    private BildirimAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseUser girisYapanKullanici;
    private List<BildirimSablon> bildirimler=new ArrayList<>();

    public Bildirimler(){
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("bildirimler");
        girisYapanKullanici=FirebaseAuth.getInstance().getCurrentUser();

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_bildirimler,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView=(ListView)view.findViewById(R.id.liste_bildirimler);
        adapter=new BildirimAdapter(getActivity(),bildirimler);
        listView.setAdapter(adapter);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bildirimler.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    BildirimSablon bildirim=snapshot.getValue(BildirimSablon.class);
                    if (girisYapanKullanici.getUid().equalsIgnoreCase(bildirim.getAliciId())){
                        bildirimler.add(bildirim);
                    }
                }
                Collections.reverse(bildirimler);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
