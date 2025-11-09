package com.aprendiendo.tpinmobiliariabd.ui.inmuebles;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.databinding.FragmentDetalleInmuebleBinding;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;
import com.bumptech.glide.Glide;

public class DetalleInmuebleFragment extends Fragment {

    private DetalleInmuebleViewModel mv;
    private FragmentDetalleInmuebleBinding binding;

    private Inmueble inmueble;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        inmueble = new Inmueble();
        mv = new ViewModelProvider(this).get(DetalleInmuebleViewModel.class);
        binding = FragmentDetalleInmuebleBinding.inflate(inflater, container, false);
        mv.getmInmueble().observe(getViewLifecycleOwner(), new Observer<Inmueble>() {
            @Override
            public void onChanged(Inmueble inmueble) {
                DetalleInmuebleFragment.this.inmueble = inmueble;
                binding.tvCodigo.setText("Codigo: " + inmueble.getIdInmueble());
                binding.tvDireccion.setText("Dirección: " + inmueble.getDireccion());
                binding.tvUso.setText("Uso: " + inmueble.getUso());
                binding.tvTipo.setText("Tipo: " + inmueble.getTipo());
                binding.tvAmbientes.setText("Ambientes: " + inmueble.getAmbientes());
                binding.tvSuperficie.setText("Superficie: " + inmueble.getSuperficie() + " m2");
                binding.tvValor.setText("$ " + inmueble.getValor());
                Glide.with(getContext())
                        .load(ApiClient.BASE_URL + inmueble.getImagen())
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error_placeholder)
                        .into(binding.imgDetalle);
            }
        });

        mv.getmEstado().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvDisponible.setText(s);
            }
        });
        mv.getmBotonNombre().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnCambiarEstado.setText(s);
            }
        });
        mv.getColorEstado().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvDisponible.setTextColor(integer);
            }
        });
        mv.getColorBoton().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.btnCambiarEstado.setBackgroundColor(integer);
            }
        });


        binding.btnCambiarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mv.actualizarInmueble(inmueble);
            }
        });

        mv.recuperarInmueble(getArguments());
        return binding.getRoot();
    }


}