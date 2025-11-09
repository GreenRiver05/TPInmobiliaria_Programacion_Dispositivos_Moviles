package com.aprendiendo.tpinmobiliariabd.ui.pagos;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aprendiendo.tpinmobiliariabd.modelos.Contrato;
import com.aprendiendo.tpinmobiliariabd.modelos.Pago;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pago>> mlistaPagos = new MutableLiveData<>();

    public PagosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pago>> getmListaPagos() {
        return mlistaPagos;
    }

    public void obtenerListaPagos(Bundle bundle) {
        Contrato contrato = (Contrato) bundle.get("contrato");
        String token = ApiClient.obtenerToken(getApplication());
        ApiClient.InmoServices api = ApiClient.getInmoServices();
        Call<List<Pago>> call = api.getPagos(token, contrato.getIdContrato());
        call.enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    List<Pago> pagos = response.body();

                    // Las fechas vienen como string desde la API
                    // Se definen dos formatos: uno para leer ese texto y otro para mostrarlo como "dd/MM/yyyy".
                    SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    SimpleDateFormat formatoSalida = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

                    for (Pago pago : pagos) {
                        try {
                            // Convertir las fechas de texto a tipo Date y luego las pasamos al formato que queremos mostrar.
                            Date fecha = formatoEntrada.parse(pago.getFechaPago());
                            String fechaFormateada = formatoSalida.format(fecha);
                            pago.setFechaPago(fechaFormateada);

                        } catch (ParseException e) {
                            pago.setFechaPago("Fecha inválida");
                        }
                    }
                    mlistaPagos.postValue(pagos);

                } else {
                    Toast.makeText(getApplication(), "no se obtuvieron Pagos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error al obtener Pagos", Toast.LENGTH_LONG).show();
            }
        });
    }
}