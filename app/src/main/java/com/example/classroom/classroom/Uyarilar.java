package com.example.classroom.classroom;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;


public class Uyarilar{
    private ProgressDialog uyari;
    private Builder builder;
    private Context context;

    public Uyarilar(Context context){
        this.context=context;
    }

    public void uyariBaslat(String baslik,String mesaj){
        uyari=new ProgressDialog(context);
        uyari.setTitle(baslik);
        uyari.setMessage(mesaj);
        uyari.show();
    }
    public void uyariDurdur(){
        uyari.dismiss();
    }
    public void kayitBasarili(){
        builder = new Builder(context);
        builder.setTitle("Başarılı!");
        builder.setMessage("İşleminiz başarıyla gerçekleşti.");
        builder.setIcon(R.drawable.tamam);
        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void kayitBasarisiz(){
        builder = new Builder(context);
        builder.setTitle("Başarısız!");
        builder.setMessage("İşleminiz gerçekleşirken bir problem oluştu. Bilgilerinizi kontrol edin ve tekrar deneyin.");
        builder.setIcon(R.drawable.hata);
        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    public void hata(String mesaj){
        builder = new Builder(context);
        builder.setTitle("Başarısız!");
        builder.setMessage(mesaj);
        builder.setIcon(R.drawable.hata);
        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void uygulamaKapat(){
        builder = new Builder(context);
        builder.setTitle("Başarısız!");
        builder.setMessage("İnternet bağlantınızı kontrol edin!");
        builder.setIcon(R.drawable.hata);
        builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
