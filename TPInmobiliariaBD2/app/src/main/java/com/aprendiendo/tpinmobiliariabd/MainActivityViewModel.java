package com.aprendiendo.tpinmobiliariabd;

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

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
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
}
