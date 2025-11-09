package com.aprendiendo.tpinmobiliariabd.ui.pagos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.modelos.Pago;

import java.util.List;

public class PagosAdapter extends RecyclerView.Adapter<PagosAdapter.ViewHolderPago> {

    private List<Pago> listaPagos;
    private Context context;
    private LayoutInflater inflater;

    public PagosAdapter(List<Pago> listaPagos, Context context, LayoutInflater inflater) {
        this.listaPagos = listaPagos;
        this.context = context;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public ViewHolderPago onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_pagos, parent, false);
        return new ViewHolderPago(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderPago holder, int position) {
        Pago pagoActual = listaPagos.get(position);
        holder.codigoPago.setText("Pago N°: " + pagoActual.getIdPago());
        holder.fechaPago.setText("Fecha: " + pagoActual.getFechaPago());
        holder.monto.setText("Monto: $" + pagoActual.getMonto());
        holder.detalle.setText("Detalle: " + pagoActual.getDetalle());
        if (pagoActual.isEstado()) {
            holder.estado.setText("Estado: Pagado");
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.fondoInmuebleDisponible));
        } else {
            holder.estado.setText("Estado: Pendiente");
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.fondoInmuebleNoDisponible));
        }
    }

    @Override
    public int getItemCount() {
        return listaPagos.size();
    }

    public class ViewHolderPago extends RecyclerView.ViewHolder {
        private TextView codigoPago;
        private TextView fechaPago;
        private TextView monto;
        private TextView detalle;
        private TextView estado;

        public ViewHolderPago(@NonNull View itemView) {
            super(itemView);
            codigoPago = itemView.findViewById(R.id.tvCodigoPago);
            fechaPago = itemView.findViewById(R.id.tvFechaPago);
            monto = itemView.findViewById(R.id.tvMonto);
            detalle = itemView.findViewById(R.id.tvDetalle);
            estado = itemView.findViewById(R.id.tvEstado);
        }
    }
}