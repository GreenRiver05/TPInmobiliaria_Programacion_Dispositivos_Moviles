package com.aprendiendo.tpinmobiliariabd.ui.perfil;


import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CambiarContraseniaViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    //controlar el estado del botón de continuar y del EditText de contraseña actual
    private MutableLiveData<Integer> mColor = new MutableLiveData<>();
    // controlar el color del EditText de contraseña actual
    private MutableLiveData<Integer> mVisibilidad = new MutableLiveData<>();
    //controlar la visibilidad de las vistas desde el ViewModel.
    private MutableLiveData<Integer> mColorBoton = new MutableLiveData<>();
    // controlar el color del botón de continuar





    public CambiarContraseniaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getmEstado() { return mEstado; }
    public LiveData<Integer> getmColor() { return mColor; }
    public LiveData<Integer> getmVisibilidad() { return mVisibilidad; }
    public LiveData<Integer> getmColorBoton() { return mColorBoton; }




    public void continuar(String claveActual, Bundle bundle) {
        if (claveActual.isEmpty()) {
            Toast.makeText(getApplication(), "Debe completar contraseña actual", Toast.LENGTH_LONG).show();
            return;
        }
        String usuario = bundle.getString("email");
        ApiClient.InmoServices inmoServices = ApiClient.getInmoServices();
        Call<String> call = inmoServices.login(usuario, claveActual);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    ApiClient.guardarToken(getApplication(), token);
                    mColor.setValue(R.drawable.edditext_fondo_bloqueado);
                    mColorBoton.setValue(R.color.grisHint);
                    mEstado.setValue(false);
                    mVisibilidad.setValue(View.VISIBLE);
                    //Android define los estados de visibilidad como constantes enteras (View.VISIBLE = 0, INVISIBLE = 4, GONE = 8),
                } else {
                    mColor.setValue(R.drawable.edittext_background);
                    mColorBoton.setValue(R.color.teal_700);
                    mEstado.setValue(true);
                    mVisibilidad.setValue(View.INVISIBLE);
                    Toast.makeText(getApplication(), "Contraseña Actual incorrecta", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void cambiarClave(String claveActual, String claveNueva, String claveConfirmar) {
        if (claveNueva.isEmpty() || claveConfirmar.isEmpty()) {
            Toast.makeText(getApplication(), "Debe completar Ambos campos", Toast.LENGTH_LONG).show();
            return;
        }
        if (!claveNueva.equals(claveConfirmar)) {
            Toast.makeText(getApplication(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            return;
        }

        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmoServices inmoServices = ApiClient.getInmoServices();
        Call<Void> call = inmoServices.cambiarClave(token, claveActual, claveNueva);
        call.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplication(), "Contraseña cambiada con éxito", Toast.LENGTH_LONG).show();
                    mEstado.setValue(true);
                    mColorBoton.setValue(R.color.teal_700);
                    mVisibilidad.setValue(View.INVISIBLE);
                    mColor.setValue(R.drawable.edittext_background);
                } else {
                    Toast.makeText(getApplication(), "Error al cambiar contraseña", Toast.LENGTH_LONG).show();
                    mColor.setValue(R.drawable.edditext_fondo_bloqueado);
                    mColorBoton.setValue(R.color.grisHint);
                    mEstado.setValue(false);
                    mVisibilidad.setValue(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });
    }
}