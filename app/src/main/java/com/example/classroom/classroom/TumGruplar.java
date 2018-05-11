package com.example.classroom.classroom;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

/**
 * Created by blacklake on 12/22/17.
 */

@SuppressLint("ValidFragment")
public class TumGruplar extends Fragment {

    private GrupAdapter grupAdapter;
    private List<Grup> gruplar;
    private LocalVeriTabani localVeriTabani;
    private ListView listView;
    private Activity context;
    private VeriTabani veriTabani;

    @SuppressLint("ValidFragment")
    public TumGruplar(Activity context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        veriTabani=new VeriTabani(context);
        localVeriTabani=new LocalVeriTabani(context);
        veriTabani.tumGruplar();
        gruplar=localVeriTabani.tumGruplariGetir();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_tum_gruplar,container,false);

        return  rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView=(ListView)view.findViewById(R.id.tumGruplarList);
        grupAdapter=new GrupAdapter(context,this,gruplar,"kaydol");
        listView.setAdapter(grupAdapter);
    }


}
