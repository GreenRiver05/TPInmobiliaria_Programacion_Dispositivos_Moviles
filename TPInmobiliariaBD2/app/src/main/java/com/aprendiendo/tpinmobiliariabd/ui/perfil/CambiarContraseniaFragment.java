package com.aprendiendo.tpinmobiliariabd.ui.perfil;

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
import com.aprendiendo.tpinmobiliariabd.databinding.FragmentCambiarContraseniaBinding;

public class CambiarContraseniaFragment extends Fragment {

    private CambiarContraseniaViewModel mv;
    private FragmentCambiarContraseniaBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mv = new ViewModelProvider(this).get(CambiarContraseniaViewModel.class);
        binding = FragmentCambiarContraseniaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mv.getmEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.btContinuar.setEnabled(aBoolean);
                binding.etClaveActual.setEnabled(aBoolean);
            }
        });

        mv.getmColorBoton().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.btContinuar.setBackgroundTintList(getResources().getColorStateList(integer));
            }
        });

        mv.getmColor().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer color) {
                binding.etClaveActual.setBackgroundResource(color);
            }
        });

        mv.getmVisibilidad().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibilidad) {
                binding.tvTituloNuevaClave.setVisibility(visibilidad);
                binding.tvConfirmarClave.setVisibility(visibilidad);
                binding.etClaveNueva.setVisibility(visibilidad);
                binding.etConfirmarClave.setVisibility(visibilidad);
                binding.btGuardarClave.setVisibility(visibilidad);
            }
        });

        mv.recuperarEmail(getArguments());

        binding.btContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claveActual = binding.etClaveActual.getText().toString();
                mv.continuar(claveActual);
            }
        });

        binding.btGuardarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String claveActual = binding.etClaveActual.getText().toString();
                String claveNueva = binding.etClaveNueva.getText().toString();
                String claveConfirmar = binding.etConfirmarClave.getText().toString();
                mv.cambiarClave(claveActual, claveNueva, claveConfirmar);
            }
        });

        return root;
    }


}