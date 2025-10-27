package com.aprendiendo.tpinmobiliariabd.ui.inmuebles;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Inmueble> mInmueble = new MutableLiveData<>();
    private MutableLiveData<String> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mBotonNombre = new MutableLiveData<>();
    private MutableLiveData<Integer> mColorEstado = new MutableLiveData<>();
    private MutableLiveData<Integer> mColorBoton = new MutableLiveData<>();
    public DetalleInmuebleViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inmueble> getmInmueble() {return mInmueble;}

    public LiveData<String> getmEstado() {return mEstado;}

    public LiveData<String> getmBotonNombre() {return mBotonNombre;}

    public LiveData<Integer> getColorEstado() {return mColorEstado;}

    public LiveData<Integer> getColorBoton() {return mColorBoton;}

    public void recuperarInmueble(Bundle bundle) {
        Inmueble inmueble = (Inmueble) bundle.get("inmueble");
        if (inmueble != null) {
            mInmueble.setValue(inmueble);
            if (inmueble.isDisponible()) {
                mEstado.setValue("Disponible");
                mBotonNombre.setValue("Deshabilitar Inmueble");
                mColorEstado.setValue(ContextCompat.getColor(getApplication(), R.color.verde_estado));
                mColorBoton.setValue(ContextCompat.getColor(getApplication(), R.color.rojo_boton));
            } else {
                mEstado.setValue("No disponible");
                mBotonNombre.setValue("Habilitar Inmueble");
                mColorEstado.setValue(ContextCompat.getColor(getApplication(), R.color.rojo_estado));
                mColorBoton.setValue(ContextCompat.getColor(getApplication(), R.color.verde_boton));
            }
        } else {
            Toast.makeText(getApplication(), "Error al recuperar el inmueble", Toast.LENGTH_LONG).show();
        }


    }

    public void actualizarInmueble(Inmueble inmueble) {
        ApiClient.InmoServices api = ApiClient.getInmoServices();
        String token = ApiClient.obtenerToken(getApplication());
        inmueble.setDisponible(!inmueble.isDisponible());

        Call<Inmueble> call = api.actualizarInmueble(token, inmueble);
        call.enqueue(new Callback<Inmueble>() {

            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {

                if (response.isSuccessful()) {
                    if (response.body().isDisponible()) {
                        mEstado.setValue("Disponible");
                        mBotonNombre.setValue("Deshabilitar Inmueble");
                        mColorEstado.setValue(ContextCompat.getColor(getApplication(), R.color.verde_estado));
                        mColorBoton.setValue(ContextCompat.getColor(getApplication(), R.color.rojo_boton));
                        Toast.makeText(getApplication(), "Inmueble Habilitado", Toast.LENGTH_LONG).show();

                    } else {
                        mEstado.setValue("No disponible");
                        mBotonNombre.setValue("Habilitar Inmueble");
                        mColorEstado.setValue(ContextCompat.getColor(getApplication(), R.color.rojo_estado));
                        mColorBoton.setValue(ContextCompat.getColor(getApplication(), R.color.verde_boton));
                        Toast.makeText(getApplication(), "Inmueble Deshabilitado", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(getApplication(), "Error al actualizar", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable throwable) {
                Log.e("ErrorActualizarInmueble", "Error al actualizar inmueble: " + throwable.getMessage());
                Toast.makeText(getApplication(), "Error al actualizar inmueble: ", Toast.LENGTH_LONG).show();
            }
        });

    }
}




