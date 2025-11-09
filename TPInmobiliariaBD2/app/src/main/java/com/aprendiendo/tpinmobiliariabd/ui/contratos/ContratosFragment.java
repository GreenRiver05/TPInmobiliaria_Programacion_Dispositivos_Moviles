package com.aprendiendo.tpinmobiliariabd.ui.contratos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.databinding.FragmentContratosBinding;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;

import java.util.List;

public class ContratosFragment extends Fragment {

    private ContratosViewModel mv;
    private FragmentContratosBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mv = new ViewModelProvider(this).get(ContratosViewModel.class);
        binding = FragmentContratosBinding.inflate(inflater, container, false);
        mv.getmListaInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                InmuebleContratoAdapter adapter = new InmuebleContratoAdapter(inmuebles, getContext(), getLayoutInflater());
                GridLayoutManager lm = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
                binding.listaInmuebles.setLayoutManager(lm);
                binding.listaInmuebles.setAdapter(adapter);
            }
        });

        mv.obtenerListaInmuebles();
        return binding.getRoot();
    }


}