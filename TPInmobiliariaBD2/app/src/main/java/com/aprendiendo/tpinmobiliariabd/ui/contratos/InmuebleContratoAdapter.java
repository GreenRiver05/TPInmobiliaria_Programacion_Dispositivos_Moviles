package com.aprendiendo.tpinmobiliariabd.ui.contratos;

import static com.aprendiendo.tpinmobiliariabd.request.ApiClient.BASE_URL;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class InmuebleContratoAdapter extends RecyclerView.Adapter<InmuebleContratoAdapter.ViewHolderInmuebleContrato> {
    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater inflater;

    public InmuebleContratoAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater inflater) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInmuebleContrato onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_inmueble_contrato, parent, false);
        return new ViewHolderInmuebleContrato(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmuebleContrato holder, int position) {
        Inmueble inmActual = listaInmuebles.get(position);
        holder.direccion.setText(inmActual.getDireccion());
        if (inmActual.isDisponible()) {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.fondoInmuebleDisponible));
        } else {
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.fondoInmuebleNoDisponible));
        }
        Glide.with(context)
                .load(BASE_URL + inmActual.getImagen())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)
                .into(holder.portada);
        ((ViewHolderInmuebleContrato) holder).botonContrato.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inmueble", inmActual);
                Navigation.findNavController((Activity) context, R.id.nav_host_fragment_content_main).navigate(R.id.detalleContratoFragment, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }


    public class ViewHolderInmuebleContrato extends RecyclerView.ViewHolder {
        private TextView direccion;
        private ImageView portada;

        private MaterialButton botonContrato;

        public ViewHolderInmuebleContrato(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            portada = itemView.findViewById(R.id.imgPortada);
            botonContrato = itemView.findViewById(R.id.btnContrato);
        }
    }
}
