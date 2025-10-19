package com.aprendiendo.tpinmobiliariabd.ui.perfil;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aprendiendo.tpinmobiliariabd.modelos.Propietario;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mNombre = new MutableLiveData<>();
    private MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();


    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Boolean> getmEstado() {
        return mEstado;
    }

    public LiveData<String> getmNombre() {
        return mNombre;
    }

    public LiveData<Propietario> getmPropietario() {
        return mPropietario;
    }


    public void obtenerPefil() {

        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmoServices api = ApiClient.getInmoServices();
        Call<Propietario> call = api.getPropietarios(token);
        call.enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    mPropietario.postValue(response.body());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener el perfil", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Toast.makeText(getApplication(), "Error en la API", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void cambioBoton(String nombreBoton, String nombre, String apellido, String dni, String telefono, String email) {

        if (nombreBoton.equalsIgnoreCase("Editar")) {
            mEstado.setValue(true);
            mNombre.setValue("Guardar");
        } else {
            mEstado.setValue(false);
            mNombre.setValue("Editar");


            Propietario propietario = new Propietario();
            propietario.setIdPropietario(mPropietario.getValue().getIdPropietario());
            propietario.setNombre(nombre);
            propietario.setApellido(apellido);
            propietario.setDni(dni);
            propietario.setTelefono(telefono);
            propietario.setEmail(email);

            String token = ApiClient.obtenerToken(getApplication());
            ApiClient.InmoServices api = ApiClient.getInmoServices();
            Call<Propietario> call = api.actualizarPropietario(token, propietario);
            call.enqueue(new Callback<Propietario>() {
                @Override
                public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Perfil actualizado", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplication(), "Error al actualizar el perfil", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<Propietario> call, Throwable t) {
                    Toast.makeText(getApplication(), "Error en la API", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}