package com.aprendiendo.tpinmobiliariabd.ui.inmuebles;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmueblesViewModel extends AndroidViewModel {

    private MutableLiveData<List<Inmueble>> mlistaInmuebles = new MutableLiveData<>();
    public InmueblesViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Inmueble>> getmListaInmuebles(){ return mlistaInmuebles; }

    public void obtenerListaInmuebles(){
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmoServices api = ApiClient.getInmoServices();
        Call<List<Inmueble>> call = api.getInmuebles(token);

        call.enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()){
                    List<Inmueble> lista = response.body();

                    // Ordenar por idInmueble descendente
                    lista.sort((a, b) -> Integer.compare(b.getIdInmueble(), a.getIdInmueble()));

                    mlistaInmuebles.postValue(lista);
                }else {
                    Toast.makeText(getApplication(),"no se obtuvieron Inmuebles",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                Log.d("errorInmueble",throwable.getMessage());

                Toast.makeText(getApplication(),"Error al obtener Inmuebles",Toast.LENGTH_LONG).show();
            }
        });
    }
}