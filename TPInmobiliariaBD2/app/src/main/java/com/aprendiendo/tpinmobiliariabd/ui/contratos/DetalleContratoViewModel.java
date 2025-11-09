package com.aprendiendo.tpinmobiliariabd.ui.contratos;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aprendiendo.tpinmobiliariabd.modelos.Contrato;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleContratoViewModel extends AndroidViewModel {

    private MutableLiveData<Contrato> mContrato = new MutableLiveData<>();
    private MutableLiveData<String> mEstado = new MutableLiveData<>();

    public DetalleContratoViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Contrato> getmContrato() {
        return mContrato;
    }

    public LiveData<String> getmEstado() {
        return mEstado;
    }


    public void recuperarContrato(Bundle bundle) {
        ApiClient.InmoServices api = ApiClient.getInmoServices();
        String token = ApiClient.obtenerToken(getApplication());
        Inmueble inmueble = (Inmueble) bundle.get("inmueble");
        Call<Contrato> call = api.getContrato(token, inmueble.getIdInmueble());
        call.enqueue(new Callback<Contrato>() {

            @Override
            public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                if (response.isSuccessful()) {
                    Contrato contrato = response.body();
                    if (contrato.isEstado()) {
                        mEstado.setValue("Activo");
                    } else {
                        mEstado.setValue("Inactivo");
                    }

                    // Las fechas vienen como string desde la API
                    // Se definen dos formatos: uno para leer ese texto y otro para mostrarlo como "dd/MM/yyyy".

                    SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                    try {

                        // Convertir las fechas de texto a tipo Date y luego las pasamos al formato que queremos mostrar.

                        Date fechaInicio = formatoEntrada.parse(contrato.getFechaInicio());
                        Date fechaFin = formatoEntrada.parse(contrato.getFechaFinalizacion());

                        String fechaInicioFormateada = formatoSalida.format(fechaInicio);
                        String fechaFinFormateada = formatoSalida.format(fechaFin);


                        contrato.setFechaInicio(fechaInicioFormateada);
                        contrato.setFechaFinalizacion(fechaFinFormateada);
                        mContrato.setValue(contrato);


                    } catch (ParseException e) {
                        Toast.makeText(getApplication(), "Error al parsear la fecha", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Contrato> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error al recuperar el contrato", Toast.LENGTH_LONG).show();
            }
        });

    }

}