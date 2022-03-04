package com.vian.prueba_tecnica;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;
import com.vian.prueba_tecnica.DB.DataBaseHandler;
import com.vian.prueba_tecnica.Globals.Globals;
import com.vian.prueba_tecnica.Models.Pelicula;

import java.util.ArrayList;

public class PeliculasAdapter extends RecyclerView.Adapter<PeliculasAdapter.viewHolder> {



    private ArrayList<Pelicula> peliculasModel;
    private Context context;
    private DataBaseHandler db;

    public PeliculasAdapter(ArrayList<Pelicula> peliculasModel, Context context) {
        this.peliculasModel = peliculasModel;
        this.context = context;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pelicula_list, null, false);

        return new viewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        holder.setIsRecyclable(false);
        holder.tvNombre.setText(holder.tvNombre.getText() + peliculasModel.get(position).getOriginal_title());
        holder.tvValoracion.setText(holder.tvValoracion.getText() + peliculasModel.get(position).getVote_average());
        holder.tvPopularidad.setText(holder.tvPopularidad.getText() + peliculasModel.get(position).getPopularity());
        holder.tvFechaDeLanzamiento.setText(holder.tvFechaDeLanzamiento.getText() + peliculasModel.get(position).getRelease_data());
        Picasso.get().load(Globals.IMG_BASE + peliculasModel.get(position).getPoster_path()).into(holder.ivPoster);
    }

    @Override
    public int getItemCount() {
        return peliculasModel.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ImageView ivPoster;
        TextView tvNombre;
        TextView tvPopularidad;
        TextView tvValoracion;
        TextView tvFechaDeLanzamiento;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvValoracion = itemView.findViewById(R.id.tvValoracion);
            tvPopularidad = itemView.findViewById(R.id.tvPopularidad);
            tvFechaDeLanzamiento = itemView.findViewById(R.id.tvFechaDeLanzamiento);
        }
    }
}
