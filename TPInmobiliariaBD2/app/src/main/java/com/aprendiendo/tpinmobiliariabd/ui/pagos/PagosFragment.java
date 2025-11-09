package com.aprendiendo.tpinmobiliariabd.ui.pagos;

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
import com.aprendiendo.tpinmobiliariabd.databinding.FragmentPagosBinding;
import com.aprendiendo.tpinmobiliariabd.modelos.Pago;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel mv;
    private FragmentPagosBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mv = new ViewModelProvider(this).get(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater, container, false);

        mv.getmListaPagos().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {

            @Override
            public void onChanged(List<Pago> pagos) {
                PagosAdapter adapter = new PagosAdapter(pagos, getContext(), getLayoutInflater());
                GridLayoutManager lm = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
                binding.listaPagos.setLayoutManager(lm);
                binding.listaPagos.setAdapter(adapter);
            }
        });

        mv.obtenerListaPagos(getArguments());
        return binding.getRoot();
    }


}