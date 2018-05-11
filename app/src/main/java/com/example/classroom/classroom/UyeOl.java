package com.example.classroom.classroom;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.ArrayList;


public class UyeOl extends AppCompatActivity {

    private EditText isim,mail,sifre,sifreTekrar,soyisim;
    private TextInputLayout l_isim,l_sifre,l_sifreTekrar,l_soyisim;
    private LinearLayout linearLayout;
    private Button button_uyeOl;
    private EkranBoyutu ekran;
    private VeriTabani veriTabani;
    private Uyarilar uyarilar;
    private Spinner spinner;
    private  ArrayAdapter<String> adapter ;
    private ArrayList<String> list;
    private  RadioButton r1,r2;
    private TextView girisYap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uyeol);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Veritabanı
        veriTabani=new VeriTabani(UyeOl.this);
        //
        r1=(RadioButton)findViewById(R.id.ogrenci);
        r2=(RadioButton)findViewById(R.id.akademisyen);
        arayuzGuncelle();
        //Uyarıları
        uyarilar=new Uyarilar(UyeOl.this);

        //Edittext bağlantıları
        isim=(EditText)findViewById(R.id.isim_text);
        mail=(EditText)findViewById(R.id.mail_text);
        sifre=(EditText)findViewById(R.id.sifre_text);
        sifreTekrar=(EditText)findViewById(R.id.sifretekrar_text);
        soyisim=(EditText)findViewById(R.id.soyisim_text);

        //TextınputLayout
        l_isim=(TextInputLayout) findViewById(R.id.isim);
        l_soyisim=(TextInputLayout)findViewById(R.id.soyisim);
        linearLayout=(LinearLayout) findViewById(R.id.linear);
        l_sifre=(TextInputLayout)findViewById(R.id.sifre);
        l_sifreTekrar=(TextInputLayout)findViewById(R.id.sifretekrar);



        //eleman boyutları
        ekran=new EkranBoyutu(this);
        ekran.ekrana_ayarla(l_isim,(ekran.getGenislik()/10)*8);
        ekran.ekrana_ayarla(linearLayout,(ekran.getGenislik()/10)*8);
        ekran.ekrana_ayarla(l_sifre,(ekran.getGenislik()/10)*8);
        ekran.ekrana_ayarla(l_sifreTekrar,(ekran.getGenislik()/10)*8);
        ekran.ekrana_ayarla(l_soyisim,(ekran.getGenislik()/10)*8);

        //Spinner
        spinner=(Spinner)findViewById(R.id.spinner);
        list=new ArrayList<>();
        list.add("HAZIRLIK");
        list.add("1.SINIF");
        list.add("2.SINIF");
        list.add("3.SINIF");
        list.add("4.SINIF");
        adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        ekran.ekrana_ayarla(spinner,(ekran.getGenislik()/10)*8);

        //butona tıklama
        button_uyeOl=(Button) findViewById(R.id.uye_ol);
        ekran.ekrana_ayarla(button_uyeOl,(ekran.getGenislik()/10)*8);
        button_uyeOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bosAlanVarMi()){
                    uyarilar.hata("Boş alan var!");
                    uyarilar.uyariDurdur();
                }else{

                        if (sifrelerAyniMi()){
                            uyarilar.hata("Şifreler uyuşmuyor!");
                            uyarilar.uyariDurdur();
                        }else{
                            veriTabani.uyeEkle(
                                    getTur()
                                    ,
                                    isim.getText().toString().trim(),
                                    soyisim.getText().toString().trim(),
                                    mail.getText().toString().trim()+"@ktu.edu.tr",
                                    sifre.getText().toString().trim(),
                                    spinner.getSelectedItem().toString().trim()
                            );

                        }


                }
            }
        });

        girisYap=(TextView)findViewById(R.id.uye_ol_giris);
        girisYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UyeOl.this,MainActivity.class));
            }
        });




    }
    public String getTur(){
        RadioGroup radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        switch (radioGroup.getCheckedRadioButtonId()){
            case R.id.ogrenci:
                return "ÖĞRENCİ";
            case R.id.akademisyen:
                return "AKADEMİSYEN";
                default:
                    return "";
        }
    }
    public void arayuzGuncelle(){
        list=new ArrayList<>();
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                list.add("HAZIRLIK");
                list.add("1.SINIF");
                list.add("2.SINIF");
                list.add("3.SINIF");
                list.add("4.SINIF");
                adapter.notifyDataSetChanged();
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                list.add("PROFESÖR");
                list.add("DOÇENT");
                list.add("YARDIMCI DOÇENT");
                list.add("ÖĞRETİM GÖREVLİSİ");
                list.add("OKUTMAN");
                list.add("ARAŞTIRMA GÖREVLİSİ");
                adapter.notifyDataSetChanged();
            }
        });


    }
    public boolean bosAlanVarMi(){
        if(TextUtils.isEmpty(isim.getText().toString())){
            return true;
        }else if (TextUtils.isEmpty(mail.getText().toString())){
            return  true;
        }else if (TextUtils.isEmpty(sifre.getText().toString())){
            return  true;
        }else if (TextUtils.isEmpty(sifreTekrar.getText().toString())){
            return  true;
        }
        return false;
    }
    public boolean sifrelerAyniMi(){
        if(sifre.getText().toString().trim().equals(sifreTekrar.getText().toString().trim())){
            return false;
        }
        return true;
    }

}
