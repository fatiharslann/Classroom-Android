package com.example.classroom.classroom;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Fatih on 26.12.2017.
 */

public class SifreDegistir extends Fragment {

    private EditText sifre,sifreTekrar;
    private Button btnSifreDegis;
    private TextInputLayout l_sifre,l_sifreTekrar;
    private EkranBoyutu ekranBoyutu;
    private FirebaseUser user;
    private Uyarilar uyarilar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_sifredegistir,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ekranBoyutu=new EkranBoyutu(getActivity());
        uyarilar=new Uyarilar(getActivity());
        user= FirebaseAuth.getInstance().getCurrentUser();

        sifre=(EditText)view.findViewById(R.id.sifre_text);
        sifreTekrar=(EditText)view.findViewById(R.id.sifretekrar_text);
        btnSifreDegis=(Button) view.findViewById(R.id.btnSifreDegis);
        l_sifre=(TextInputLayout)view.findViewById(R.id.sifre);
        l_sifreTekrar=(TextInputLayout)view.findViewById(R.id.sifretekrar);

        ekranBoyutu.ekrana_ayarla(l_sifre,(ekranBoyutu.getGenislik()/10)*8);
        ekranBoyutu.ekrana_ayarla(l_sifreTekrar,(ekranBoyutu.getGenislik()/10)*8);
        ekranBoyutu.ekrana_ayarla(btnSifreDegis,(ekranBoyutu.getGenislik()/10)*8);

        btnSifreDegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uyarilar.uyariBaslat("","İşleminiz devam ediyor. Lütfen bekleyiniz...");
                if (sifrelerAynimi()){
                    user.updatePassword(sifre.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                uyarilar.uyariDurdur();
                                uyarilar.kayitBasarili();
                                startActivity(new Intent(getActivity(),Anasayfa.class));
                            }else{
                                uyarilar.uyariDurdur();
                                try{
                                    //uyarilar.kayitBasarisiz();
                                    uyarilar.uyariDurdur();
                                    throw task.getException();
                                }catch (FirebaseAuthWeakPasswordException e){
                                    uyarilar.hata("Zayıf Parola!");
                                }catch (Exception e){

                                }

                            }
                        }
                    });
                }else{
                    uyarilar.uyariDurdur();

                }
            }
        });


    }
    public boolean sifrelerAynimi(){
        if (sifreTekrar.getText().toString().trim().equalsIgnoreCase(sifre.getText().toString().trim())){
            return true;
        }
        return false;
    }
}
