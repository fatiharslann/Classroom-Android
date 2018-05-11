package com.example.classroom.classroom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by alper on 16/07/2017.
 */

public class MesajAdapter extends ArrayAdapter<MesajSablon> {

    private FirebaseUser firebaseUser;

    public MesajAdapter(Context context, ArrayList<MesajSablon> chatList,FirebaseUser firebaseUser) {
        super(context, 0, chatList);
        this.firebaseUser = firebaseUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MesajSablon message = getItem(position);
        if (firebaseUser.getUid().equalsIgnoreCase(message.getGondericiId())){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_gonderilen_mesaj, parent, false);

            TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessageRight);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTimeRight);
            txtMessage.setText(message.getMesaj());
            txtTime.setText(message.getZaman());

        }else{

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_alinan_mesaj, parent, false);

            TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessageLeft);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTimeLeft);
            txtMessage.setText(message.getMesaj());
            txtTime.setText(message.getZaman());

        }

        return convertView;
    }
}