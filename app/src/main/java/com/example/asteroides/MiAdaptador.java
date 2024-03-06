package com.example.asteroides;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup; // Cambiado a ViewGroup
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull; // Añadido
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MiAdaptador extends RecyclerView.Adapter<MiAdaptador.ViewHolder> {
    private LayoutInflater inflador;
    private List<String> lista;

    protected View.OnClickListener onClickListener;
    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    public MiAdaptador(Context context, List<String> lista) {
        this.lista = lista;
        inflador = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull // Añadido
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // Cambiado a ViewGroup
        View v = inflador.inflate(R.layout.elemento_lista, parent, false);
        v.setOnClickListener(onClickListener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.titulo.setText(lista.get(i));
        switch (Math.round((float) Math.random() * 2)) { // Cambiado a 2 para corresponder a las opciones de 0, 1, 2
            case 0:
                holder.icon.setImageResource(R.drawable.asteroide1);
                break;
            case 1:
                holder.icon.setImageResource(R.drawable.asteroide2);
                break;
            default:
                holder.icon.setImageResource(R.drawable.asteroide3);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo, subtitulo;
        public ImageView icon;
        ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo);
            subtitulo = itemView.findViewById(R.id.subtitulo);
            icon = itemView.findViewById(R.id.icono);
        }
    }
}
