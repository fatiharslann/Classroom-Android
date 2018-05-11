package com.example.classroom.classroom;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private ImageView user;
    private EditText kullanici_adi_text,sifre_text;
    private TextInputLayout l_sifre;
    private Button giris_yap;
    private TextView kayit_ol;
    private EkranBoyutu ekran;
    private Uyarilar uyarilar;
    private InternetKontrolu internetKontrolu;
    private LinearLayout linearLayout;
    private VeriTabani veriTabani;
    public static Fragment tempFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        uyarilar=new Uyarilar(MainActivity.this);
        internetKontrolu=new InternetKontrolu(this);
        veriTabani=new VeriTabani(MainActivity.this);
        ekran=new EkranBoyutu(this);

        try {
            if (internetKontrolu.internetBaglantiKontrol()){

                giris_yap=(Button) findViewById(R.id.giris_yap);
                ekran.ekrana_ayarla(giris_yap,(ekran.getGenislik()/10)*8);

                kayit_ol=(TextView)findViewById(R.id.kayit_ol);
                ekran.ekrana_ayarla(kayit_ol,(ekran.getGenislik()/10)*7);


                l_sifre=(TextInputLayout)findViewById(R.id.sifre);
                ekran.ekrana_ayarla(l_sifre,(ekran.getGenislik()/10)*8);

                linearLayout=(LinearLayout)findViewById(R.id.linear);
                ekran.ekrana_ayarla(linearLayout,(ekran.getGenislik()/10)*8);

                kullanici_adi_text=(EditText)findViewById(R.id.kullanici_adi_text);
                sifre_text=(EditText)findViewById(R.id.sifre_text);

                giris_yap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (bosAlanKontrol(kullanici_adi_text.getText().toString().trim(),sifre_text.getText().toString().trim())){

                            veriTabani.girisYap(kullanici_adi_text.getText().toString().trim()+"@ktu.edu.tr",sifre_text.getText().toString().trim());

                        }else{
                            uyarilar.hata("Boş Alan var!");
                        }
                        kullanici_adi_text.setText("");
                        sifre_text.setText("");
                    }
                });
                kayit_ol.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this,UyeOl.class));

                    }
                });

            }
        }catch (Exception e){
            uyarilar.uygulamaKapat();
        }
        veriTabani.tumKullanicilar();




    }

    private boolean bosAlanKontrol(String mail,String sifre){
        if (TextUtils.isEmpty(mail)||TextUtils.isEmpty(sifre))
            return false;
        return true;
    }


}

