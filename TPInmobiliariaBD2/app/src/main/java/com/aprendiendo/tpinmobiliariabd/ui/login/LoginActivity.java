package com.aprendiendo.tpinmobiliariabd.ui.login;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 100;
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

        validarPermisoLlamada();
        mv.registrarSensor();
    }


    //----------------------------------------Uso de permiso, Referencia: Foro en aula virtual----------------------------------------------------
    //implementaciones de Dialo mirando pildora Informativa 6 que se encuentra en el Aula ademas del foro del aula donde se explica permiso
    private void validarPermisoLlamada() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
        //En versiones < Android 6 no se requiere pedir permisos en tiempo de ejecución.

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) { // Si el permiso de llamada no está otorgado.

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
                mostrarDialogoExplicativo(); //Si el usuario rechazó antes, explico por qué lo necesito.

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
                //Solicito el permiso directamente.
            }
        }
    }

    private void mostrarDialogoExplicativo() {
        new AlertDialog.Builder(this)
                .setTitle("Permiso necesario")
                .setMessage("La app necesita permiso para realizar llamadas automáticas en caso de emergencia.")
                .setPositiveButton("Aceptar", (dialog, which) ->
                        ActivityCompat.requestPermissions(
                                this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                REQUEST_CALL_PERMISSION))
                .setNegativeButton("Cancelar", null)
                .show();
        // Si el usuario acepta el diálogo explicativo, solicito el permiso CALL_PHONE con el código REQUEST_CALL_PERMISSION.
        // Esto lanza el diálogo oficial de Android para que el usuario lo acepte.
        // La respuesta se maneja en onRequestPermissionsResult(...).
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // requestCode: número que identifica qué permiso se está respondiendo (ej. REQUEST_CALL_PERMISSION).
        // permissions: lista de permisos que se solicitaron.
        // grantResults: resultados (aceptado o rechazado) para cada permiso solicitado.


        if (requestCode == REQUEST_CALL_PERMISSION) { // Verifico que el permiso sea el que me interesa
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //grantResults[0] == PackageManager.PERMISSION_GRANTED: significa que el primer permiso fue aceptado.

                Toast.makeText(this, "Permiso de llamada otorgado", Toast.LENGTH_SHORT).show();

            } else {
                mostrarDialogoConfiguracionManual(); //Muestro el dialogo de configuración manual
            }
        }
    }

    private void mostrarDialogoConfiguracionManual() {
        // Este diálogo se muestra si el usuario rechazó el permiso y marcó "No volver a preguntar".
        // Le doy la opción de ir manualmente a Configuración > Aplicaciones para habilitar el permiso CALL_PHONE.


        new AlertDialog.Builder(this)
                .setTitle("Permiso denegado")
                .setMessage("Podés habilitar el permiso manualmente desde Configuración > Aplicaciones.")
                .setPositiveButton("Ir a configuración", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                    //Si el usuario acepta, ejecuto un intent que lo lleva directamente a la pantalla de configuración de esta app.
                    //Construyo una URI con el nombre del paquete de esta app para que Android sepa qué configuración abrir.
                    //Asigno la URI al intent para que apunte a esta app en particular.
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


    protected void onDestroy() {
        super.onDestroy();
        mv.desregistrarSensor();
    }
}