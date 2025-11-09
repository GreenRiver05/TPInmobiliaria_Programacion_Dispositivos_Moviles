package com.aprendiendo.tpinmobiliariabd.ui.login;

import android.app.Application;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.aprendiendo.tpinmobiliariabd.MainActivity;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private MutableLiveData<String> mMensaje = new MutableLiveData<>();
private SensorManager manager;
private List<Sensor> sensores;

    public LoginActivityViewModel(@NonNull Application application) { super(application); }


    public MutableLiveData<String> getmMensaje() {return mMensaje;}



    public void logueo(String usuario, String clave) {
        if (usuario.isEmpty() || clave.isEmpty()) {
            mMensaje.setValue("Debe ingresar usuario y clave");
            return;
        }

        ApiClient.InmoServices inmoServices = ApiClient.getInmoServices();
        Call<String> call = inmoServices.login(usuario, clave);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String token = response.body();
                    Log.d("token", token);
                    ApiClient.guardarToken(getApplication(), token);
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("token", token);
                    getApplication().startActivity(intent);


                } else {
                    mMensaje.setValue("Credenciales inválidas");
                    Log.d("error", response.message());
                    Log.d("error", response.errorBody().toString());
                    Log.d("error", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.d("error", throwable.getMessage());
                mMensaje.setValue("Error de conexión");
            }
        });
    }
}
