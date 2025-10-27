package com.aprendiendo.tpinmobiliariabd.ui.inmuebles;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.databinding.FragmentCrearInmuebleBinding;

public class CrearInmuebleFragment extends Fragment {

    private CrearInmuebleViewModel mv;
    private FragmentCrearInmuebleBinding binding;
    private Intent intent;
    private ActivityResultLauncher<Intent> arl;

    public static CrearInmuebleFragment newInstance() {
        return new CrearInmuebleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentCrearInmuebleBinding.inflate(inflater, container, false);
        mv = new ViewModelProvider(this).get(CrearInmuebleViewModel.class);
        abrirGaleria();


        mv.getmUri().observe(getViewLifecycleOwner(), new Observer<Uri>() {
            @Override
            public void onChanged(Uri uri) {
                binding.imgPreview.setImageURI(uri);
            }
        });
        mv.getmBotonNombre().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.btnGuardarInmueble.setText(s);
            }
        });
        mv.getmColorBoton().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.btnGuardarInmueble.setBackgroundTintList(getResources().getColorStateList(integer));
            }
        });
        mv.getmColorTexto().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.etDireccion.setTextColor(getResources().getColor(integer));
                binding.etUso.setTextColor(getResources().getColor(integer));
                binding.etTipo.setTextColor(getResources().getColor(integer));
                binding.etAmbientes.setTextColor(getResources().getColor(integer));
                binding.etSuperficie.setTextColor(getResources().getColor(integer));
                binding.etLatitud.setTextColor(getResources().getColor(integer));
                binding.etLongitud.setTextColor(getResources().getColor(integer));
                binding.etValor.setTextColor(getResources().getColor(integer));
            }
        });
        mv.getmColorBotonImagen().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.btnSeleccionarImagen.setBackgroundTintList(getResources().getColorStateList(integer));
            }
        });
        mv.getmEstado().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.btnSeleccionarImagen.setEnabled(aBoolean);
                binding.etDireccion.setEnabled(aBoolean);
                binding.etUso.setEnabled(aBoolean);
                binding.etTipo.setEnabled(aBoolean);
                binding.etAmbientes.setEnabled(aBoolean);
                binding.etSuperficie.setEnabled(aBoolean);
                binding.etLatitud.setEnabled(aBoolean);
                binding.etLongitud.setEnabled(aBoolean);
                binding.etValor.setEnabled(aBoolean);
            }
        });
        mv.getmFondoEditText().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer fondo) {
                binding.etDireccion.setBackgroundResource(fondo);
                binding.etUso.setBackgroundResource(fondo);
                binding.etTipo.setBackgroundResource(fondo);
                binding.etAmbientes.setBackgroundResource(fondo);
                binding.etSuperficie.setBackgroundResource(fondo);
                binding.etLatitud.setBackgroundResource(fondo);
                binding.etLongitud.setBackgroundResource(fondo);
                binding.etValor.setBackgroundResource(fondo);
            }
        });
        mv.getmLimpiarCampos().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.etDireccion.setText("");
                binding.etUso.setText("");
                binding.etTipo.setText("");
                binding.etAmbientes.setText("");
                binding.etSuperficie.setText("");
                binding.etLatitud.setText("");
                binding.etLongitud.setText("");
                binding.etValor.setText("");
                binding.imgPreview.setImageDrawable(null);
            }
        });


        binding.btnSeleccionarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arl.launch(intent);
            }
        });

        binding.btnGuardarInmueble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String direccion = binding.etDireccion.getText().toString();
                String uso = binding.etUso.getText().toString();
                String tipo = binding.etTipo.getText().toString();
                String ambientes = binding.etAmbientes.getText().toString();
                String superficie = binding.etSuperficie.getText().toString();
                String latitud = binding.etLatitud.getText().toString();
                String longitud = binding.etLongitud.getText().toString();
                String valor = binding.etValor.getText().toString();
                String boton = binding.btnGuardarInmueble.getText().toString();

                mv.guardarInmueble(direccion, uso, tipo, ambientes, superficie, latitud, longitud, valor, boton);
            }
        });

        return binding.getRoot();

    }

    private void abrirGaleria() {
        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                mv.recuperarImagen(result);
            }
        });
    }

}