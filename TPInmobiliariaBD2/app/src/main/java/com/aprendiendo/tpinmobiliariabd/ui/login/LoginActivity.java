package com.aprendiendo.tpinmobiliariabd.ui.login;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityViewModel mv;
    private ActivityLoginBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mv = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(LoginActivityViewModel.class);

        mv.getmMensaje().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvError.setText(s);
                binding.tvError.setVisibility(View.VISIBLE);
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.tvError.setVisibility(View.INVISIBLE);
                binding.tvError.setText("");
                String usuario = binding.etUsuario.getText().toString();
                String clave = binding.etClave.getText().toString();
                mv.logueo(usuario, clave);
            }
        });



    }
}