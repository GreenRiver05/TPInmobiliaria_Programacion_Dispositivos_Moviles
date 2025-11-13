package com.aprendiendo.tpinmobiliariabd.ui.contratos;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.databinding.FragmentDetalleContratoBinding;
import com.aprendiendo.tpinmobiliariabd.modelos.Contrato;

public class DetalleContratoFragment extends Fragment {

    private DetalleContratoViewModel mv;
    private FragmentDetalleContratoBinding binding;
    private Contrato contrato;

    public static DetalleContratoFragment newInstance() {
        return new DetalleContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mv = new ViewModelProvider(this).get(DetalleContratoViewModel.class);
        binding = FragmentDetalleContratoBinding.inflate(inflater, container, false);

        mv.getmContrato().observe(getViewLifecycleOwner(), new Observer<Contrato>() {
            @Override
            public void onChanged(Contrato contrato) {
                DetalleContratoFragment.this.contrato = contrato;
                binding.tvIdContrato.setText(contrato.getIdContrato() + "");
                binding.tvFechaInicio.setText(contrato.getFechaInicio() + "");
                binding.tvFechaFin.setText(contrato.getFechaFinalizacion() + "");
                binding.tvMonto.setText("$" + contrato.getMontoAlquiler());
                binding.tvNombreInquilino.setText(contrato.getInquilino().getNombre() + " " + contrato.getInquilino().getApellido());
            }
        });

        mv.getmEstado().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvEstado.setText(s);
            }
            });

        binding.btnVerInquilino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("inquilino", contrato.getInquilino());
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.nav_inquilinos, bundle);
            }
        });

        binding.btnVerPagos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("contrato", contrato);
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.pagosFragment, bundle);
            }
        });

        mv.recuperarContrato(getArguments());
        return binding.getRoot();
    }


}