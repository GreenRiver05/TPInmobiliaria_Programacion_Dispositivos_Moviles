package com.aprendiendo.tpinmobiliariabd.ui.perfil;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.aprendiendo.tpinmobiliariabd.R;
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
        mv.getFondoEditText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer fondo) {
                binding.etTelefono.setBackgroundResource(fondo);
                binding.etNombre.setBackgroundResource(fondo);
                binding.etApellido.setBackgroundResource(fondo);
                binding.etDni.setBackgroundResource(fondo);
            }
        });
        mv.getColorTexto().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer color) {
                binding.etNombre.setTextColor(ContextCompat.getColor(requireContext(), color));
                binding.etApellido.setTextColor(ContextCompat.getColor(requireContext(), color));
                binding.etDni.setTextColor(ContextCompat.getColor(requireContext(), color));
                binding.etTelefono.setTextColor(ContextCompat.getColor(requireContext(), color));
            }
        });
        mv.getColorBoton().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer color) {
                binding.btEditarGuardar.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), color));
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

        binding.btResetearClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mv.resetearClave(binding.etEmail.getText().toString());
            }
        });

        binding.btActualizarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("email", binding.etEmail.getText().toString());
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_content_main).navigate(R.id.cambiarContraseniaFragment, bundle );
            }
        });


        mv.obtenerPefil();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mv.reiniciarPerfil();
        binding = null;
    }
}