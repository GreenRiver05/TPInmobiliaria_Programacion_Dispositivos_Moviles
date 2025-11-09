package com.aprendiendo.tpinmobiliariabd.ui.logout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.databinding.FragmentLogoutBinding;
import com.aprendiendo.tpinmobiliariabd.ui.login.LoginActivity;

//1- Crear el Fragment
//2- No utilizamos el viewModel
//3- Creamos el metodo muestraDialogo()


public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        muestraDialogo();
        return root;
    }

    //implementacion de Dialo mirando pildora Informativa 6 que se encuentra en el Aula
    //Instancio el dialogo y pasar el contexto (linea 44 )
    //Seteamos el titulo y el mensaje (linea 45-46)
    //Seteamos los botones de aceptar y cancelar (linea 47-52 )
    //Boton aceptar recibe 2 parametros: titulo y accion listener, adentro ponemos el fin del activity
    //Boton cancelar recibe 2 parametros: titulo y accion listener, adentro ponemos un toast con un mensaje de no salio
    //Se muestra el dialogo con el metodo show() (linea 58)

    public void cerrarSesion(Context context) {
        // Elimino el token guardado
        SharedPreferences prefs = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        prefs.edit().clear().apply(); // Borra todo el archivo de sesión

        // Redirigir al login y limpiar el historial
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }


    private void muestraDialogo() {

        new AlertDialog.Builder(getContext())
                .setTitle("Cierre de Sesion")
                .setMessage("¿Está seguro que desea cerrar sesión?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(getContext(), "Se Quedo 😁", Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        cerrarSesion(getContext());
                    }
                })
                .show();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}