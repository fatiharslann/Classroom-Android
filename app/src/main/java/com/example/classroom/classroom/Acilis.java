package com.example.classroom.classroom;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Fatih on 27.12.2017.
 */

public class Acilis extends Fragment {
    private ImageView pp;
    private TextView t1,t2,t3,t4;
    private LocalVeriTabani localVeriTabani;
    private Kullanici user;
    private FirebaseUser kullanici;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_acilis,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        localVeriTabani=new LocalVeriTabani(getActivity());
        user=localVeriTabani.girisYapanKullanici();
        kullanici= FirebaseAuth.getInstance().getCurrentUser();
        t1=(TextView)view.findViewById(R.id.textView9);
        t1.setText(user.getAd()+" "+user.getSoyad());
        t2=(TextView)view.findViewById(R.id.textView10);
        t2.setText(kullanici.getEmail());
        t3=(TextView)view.findViewById(R.id.textView11);
        t3.setText(user.getDegisken());
        t4=(TextView)view.findViewById(R.id.textView12);
        t4.setText(user.getTur());
        pp=(ImageView)view.findViewById(R.id.imageView);
        if (user.getTur().equals("ÖĞRENCİ")) {
            pp.setImageResource(R.drawable.ogrenci);
        }else{
            pp.setImageResource(R.drawable.akademisyen);
        }



    }
}
