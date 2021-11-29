package com.est.agenda_est;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList id,nome,sobrenome,contacto;
    private MyViewHolder holder;
    private int position;

    Animation translate_anim;

    CustomAdapter(Activity activity,Context context, ArrayList id,ArrayList nome,ArrayList sobrenome,ArrayList contacto){
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.contacto = contacto;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        this.holder = holder;
        this.position = position;
        holder.id_txt.setText(String.valueOf(id.get(position)));
        holder.nome_txt.setText(String.valueOf(nome.get(position)));
        holder.sobrenome_txt.setText(String.valueOf(sobrenome.get(position)));
        holder.contacto_txt.setText(String.valueOf(contacto.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("id",String.valueOf(id.get(position)));
                intent.putExtra("nome",String.valueOf(nome.get(position)));
                intent.putExtra("sobrenome",String.valueOf(sobrenome.get(position)));
                intent.putExtra("contacto",String.valueOf(contacto.get(position)));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id_txt,nome_txt,sobrenome_txt,contacto_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_txt = itemView.findViewById(R.id.id_txt);
            nome_txt = itemView.findViewById(R.id.nome_txt);
            sobrenome_txt = itemView.findViewById(R.id.sobrenome_txt);
            contacto_txt = itemView.findViewById(R.id.contacto_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //RecyclerView Animado
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
