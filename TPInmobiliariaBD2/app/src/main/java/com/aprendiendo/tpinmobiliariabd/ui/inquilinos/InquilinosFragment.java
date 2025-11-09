package com.aprendiendo.tpinmobiliariabd.ui.inquilinos;

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
import com.aprendiendo.tpinmobiliariabd.databinding.FragmentInquilinosBinding;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;
import com.aprendiendo.tpinmobiliariabd.modelos.Inquilino;

import java.util.List;

public class InquilinosFragment extends Fragment {

    private InquilinosViewModel mv;
    private FragmentInquilinosBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mv = new ViewModelProvider(this).get(InquilinosViewModel.class);
        binding = FragmentInquilinosBinding.inflate(inflater, container, false);

        mv.getmInquilino().observe(getViewLifecycleOwner(), new Observer<Inquilino>() {
            @Override
            public void onChanged(Inquilino inquilino) {
                binding.tvCodigo.setText(inquilino.getIdInquilino() + "");
                binding.tvNombre.setText(inquilino.getNombre() + "");
                binding.tvApellido.setText(inquilino.getApellido() + "");
                binding.tvDni.setText(inquilino.getDni());
                binding.tvTelefono.setText(inquilino.getTelefono() + "");
                binding.tvEmail.setText(inquilino.getEmail() + "");
            }
        });

        mv.recuperarInquilino(getArguments());
        return binding.getRoot();
    }


}