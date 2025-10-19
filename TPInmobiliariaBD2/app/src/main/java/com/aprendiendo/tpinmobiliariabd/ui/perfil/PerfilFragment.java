package com.aprendiendo.tpinmobiliariabd.ui.perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aprendiendo.tpinmobiliariabd.databinding.FragmentPerfilBinding;
import com.aprendiendo.tpinmobiliariabd.modelos.Propietario;

public class PerfilFragment extends Fragment {

    private FragmentPerfilBinding binding;
    private PerfilViewModel mv;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        mv = new ViewModelProvider(this).get(PerfilViewModel.class);
        View root = binding.getRoot();


        mv.getmEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etNombre.setEnabled(aBoolean);
                binding.etApellido.setEnabled(aBoolean);
                binding.etDni.setEnabled(aBoolean);
                binding.etTelefono.setEnabled(aBoolean);
            }
        });

        mv.getmNombre().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btEditarGuardar.setText(s);
            }
        });

        mv.getmPropietario().observe(getViewLifecycleOwner(), new Observer<Propietario>() {
            @Override
            public void onChanged(Propietario propietario) {
                binding.etNombre.setText(propietario.getNombre());
                binding.etApellido.setText(propietario.getApellido());
                binding.etDni.setText(propietario.getDni());
                binding.etTelefono.setText(propietario.getTelefono());
                binding.etEmail.setText(propietario.getEmail());
            }
        });

        binding.btEditarGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.cambioBoton(binding.btEditarGuardar.getText().toString(),
                        binding.etNombre.getText().toString(),
                        binding.etApellido.getText().toString(),
                        binding.etDni.getText().toString(),
                        binding.etTelefono.getText().toString(),
                        binding.etEmail.getText().toString());
            }
        });

        mv.obtenerPefil();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}