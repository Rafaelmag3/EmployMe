package com.example.navbotdialog.Herramientas.Notas.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navbotdialog.Herramientas.Notas.Click_Notas;
import com.example.navbotdialog.Herramientas.Notas.Modales.Notas;
import com.example.navbotdialog.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotasListaAdapter extends RecyclerView.Adapter<NotasViewHolder>{
    Context context;
    List<Notas> list;
    Click_Notas listener;
    public NotasListaAdapter(Context context, List<Notas> list, Click_Notas listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }
    @NonNull
    @Override
    public NotasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotasViewHolder(LayoutInflater.from(context).inflate(R.layout.lista_notas,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotasViewHolder holder, int position) {
        holder.txt_titulo.setText(list.get(position).getTitulo());
        holder.txt_titulo.setSelected(true);

        holder.txt_notas.setText(list.get(position).getNotas());

        holder.txt_fecha.setText(list.get(position).getFecha());
        holder.txt_fecha.setSelected(true);

        if(list.get(position).isAnclar()){
            holder.imagen_anclar.setImageResource(R.drawable.ic_anclar);
        }else {
            holder.imagen_anclar.setImageResource(0);
        }

        int Codigo_color = getRandomColor();
        holder.contenedor_notas.setCardBackgroundColor(holder.itemView.getResources().getColor(Codigo_color, null));

        holder.contenedor_notas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));
            }
        });

        holder.contenedor_notas.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()),holder.contenedor_notas);
                return true;
            }
        });

    }

    private int getRandomColor(){
        List<Integer> CodigoColores = new ArrayList<>();

        CodigoColores.add(R.color.white);

        Random random = new Random();
        int ramdom_color = random.nextInt(CodigoColores.size());

        return CodigoColores.get(ramdom_color);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class NotasViewHolder extends RecyclerView.ViewHolder{

    CardView  contenedor_notas;
    TextView txt_titulo, txt_notas, txt_fecha;
    ImageView imagen_anclar;

    public NotasViewHolder(@NonNull View itemView) {
        super(itemView);

        contenedor_notas = itemView.findViewById(R.id.contenedor_notas);
        txt_titulo = itemView.findViewById(R.id.txt_titulo);
        txt_notas = itemView.findViewById(R.id.txt_notas);
        txt_fecha = itemView.findViewById(R.id.txt_fecha);
        imagen_anclar = itemView.findViewById(R.id.imagen_anclar);


    }
}
