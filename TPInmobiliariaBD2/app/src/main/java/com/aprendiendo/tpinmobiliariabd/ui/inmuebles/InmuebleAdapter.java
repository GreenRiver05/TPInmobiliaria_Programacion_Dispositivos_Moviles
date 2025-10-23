package com.aprendiendo.tpinmobiliariabd.ui.inmuebles;

import static com.aprendiendo.tpinmobiliariabd.request.ApiClient.BASE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;
import com.bumptech.glide.Glide;

import java.util.List;

public class InmuebleAdapter extends RecyclerView.Adapter<InmuebleAdapter.ViewHolderInmueble> {

    private List<Inmueble> listaInmuebles;
    private Context context;
    private LayoutInflater inflater;

    public InmuebleAdapter(List<Inmueble> listaInmuebles, Context context, LayoutInflater inflater) {
        this.listaInmuebles = listaInmuebles;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderInmueble onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.iteminmueble,parent,false);
        return new ViewHolderInmueble(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInmueble holder, int position) {
    Inmueble inmActual = listaInmuebles.get(position);
    holder.direccion.setText(inmActual.getDireccion());
    holder.precio.setText(inmActual.getValor()+"");
        Glide.with(context)
                .load(BASE_URL + inmActual.getImagen())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)
                .into(holder.portada);
    }

    @Override
    public int getItemCount() {
        return listaInmuebles.size();
    }

    public class ViewHolderInmueble extends RecyclerView.ViewHolder  {

        private TextView direccion, precio;
        private ImageView portada;
        public ViewHolderInmueble(@NonNull View itemView) {
            super(itemView);
            direccion = itemView.findViewById(R.id.tvDireccion);
            precio = itemView.findViewById(R.id.tvValor);
            portada= itemView.findViewById(R.id.imgPortada);
        }
    }
}
