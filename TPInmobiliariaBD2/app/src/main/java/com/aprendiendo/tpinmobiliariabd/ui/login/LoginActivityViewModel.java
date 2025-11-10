package com.aprendiendo.tpinmobiliariabd.ui.login;

import static android.content.Context.SENSOR_SERVICE;

import android.Manifest;
import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
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
    private ManejaEventos maneja;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getmMensaje() { return mMensaje; }


    public void registrarSensor() {
        manager = (SensorManager) getApplication().getSystemService(SENSOR_SERVICE);  // Obtengo el servicio de sensores del sistema.
        sensores = manager.getSensorList(Sensor.TYPE_ACCELEROMETER); //Uso acelerómetro porque para detectar agitación.
        if (sensores.size() > 0) {
            maneja = new ManejaEventos(); // Instancio listener personalizado para manejar eventos del sensor
            manager.registerListener(maneja, sensores.get(0), SensorManager.SENSOR_DELAY_NORMAL); //Registro el listener con frecuencia alta.
        }
    }

    public void desregistrarSensor() {
        manager.unregisterListener(maneja);
    }

    private void realizarLlamada() { //Clase 29 de agosto 2025
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("tel:2657000000"));
        getApplication().startActivity(intent);
    }

    private class ManejaEventos implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calcular la magnitud de la aceleración usando la fórmula Math.sqrt(x² + y² + z²).
            float aceleracion = (float) Math.sqrt(x * x + y * y + z * z);

            // Verificar si la magnitud de la aceleración supera un umbral
            //En reposo, el teléfono tiene ~9.8 m/s² por la gravedad.
            if (aceleracion > 20) {


                // Verificar si el permiso de llamada está otorgado.
                // Un poco visto en clase 29 de agosto 2025 otro poco en el foro del aula
                if (ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    realizarLlamada();
                }

            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

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
