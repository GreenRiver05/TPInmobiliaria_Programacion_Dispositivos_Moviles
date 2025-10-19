package com.aprendiendo.tpinmobiliariabd.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.databinding.FragmentInicioBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class InicioFragment extends Fragment {

   private InicioViewModel mv;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mv = new ViewModelProvider(this).get(InicioViewModel.class);

        mv.getMapaActual().observe(getViewLifecycleOwner(), new Observer<InicioViewModel.MapaActual>() {
            @Override
            public void onChanged(InicioViewModel.MapaActual mapaActual) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(mapaActual);
            }
        });

        mv.obtenerMapaActual();
        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }


}