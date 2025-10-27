package com.aprendiendo.tpinmobiliariabd.ui.inmuebles;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CrearInmuebleViewModel extends AndroidViewModel {

    private MutableLiveData<Uri> mUri = new MutableLiveData<>();
    private MutableLiveData<String> mBotonNombre = new MutableLiveData<>();
    private MutableLiveData<Integer> mColorBoton = new MutableLiveData<>();
    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<Integer> mFondoEditText = new MutableLiveData<>();
    private MutableLiveData<Integer> mColorTexto = new MutableLiveData<>();
    private MutableLiveData<Integer> mColorBotonImagen = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLimpiarCampos = new MutableLiveData<>();
    private static Inmueble inmueble;


    public CrearInmuebleViewModel(@NonNull Application application) {
        super(application);
        inmueble = new Inmueble();
    }

    public LiveData<Uri> getmUri() { return mUri; }

    public LiveData<String> getmBotonNombre() { return mBotonNombre; }
    public LiveData<Integer> getmColorBoton() { return mColorBoton; }
    public LiveData<Boolean> getmEstado() { return mEstado; }
    public LiveData<Integer> getmFondoEditText() { return mFondoEditText; }
    public LiveData<Integer> getmColorTexto() { return mColorTexto; }
    public LiveData<Integer> getmColorBotonImagen() { return mColorBotonImagen; }
    public LiveData<Boolean> getmLimpiarCampos() { return mLimpiarCampos; }



    public void recuperarImagen(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            Log.d("salada", uri.toString());
            mUri.setValue(uri);
        }
    }


    public void guardarInmueble(String direccion,
                                String uso,
                                String tipo,
                                String ambientes,
                                String superficie,
                                String latitud,
                                String longitud,
                                String valor,
                                String boton) {

        if (boton.equalsIgnoreCase("Nuevo Inmueble")) {
            mBotonNombre.setValue("Guardar Inmueble");
            mColorBoton.setValue(R.color.verde_boton);
            mEstado.setValue(true);
            mFondoEditText.setValue(R.drawable.edittext_background);
            mColorTexto.setValue(R.color.black);
            mColorBotonImagen.setValue(R.color.teal_700);
            Boolean limpiar = mLimpiarCampos.getValue();
            mLimpiarCampos.setValue(limpiar == null ? true : !limpiar);
            return;
        }


        if (direccion.isEmpty() || uso.isEmpty() || tipo.isEmpty() || ambientes.isEmpty() || superficie.isEmpty() || latitud.isEmpty() || longitud.isEmpty() || valor.isEmpty()) {
            Toast.makeText(getApplication(), "Complete Todos Los campos", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            int a = Integer.parseInt(ambientes);
            int s = Integer.parseInt(superficie);
            double l = Double.parseDouble(latitud);
            double lo = Double.parseDouble(longitud);
            double v = Double.parseDouble(valor);

            inmueble.setDireccion(direccion);
            inmueble.setUso(uso);
            inmueble.setTipo(tipo);
            inmueble.setAmbientes(a);
            inmueble.setSuperficie(s);
            inmueble.setLatitud(l);
            inmueble.setLongitud(lo);
            inmueble.setValor(v);
            inmueble.setDisponible(false);

            byte[] imagen = transformarImagen();
            if (imagen.length == 0 || mUri.getValue() == null) {
                Toast.makeText(getApplication(), "Cargue una imagen", Toast.LENGTH_LONG).show();
                return;
            }


            String inmuebleJson = new Gson().toJson(inmueble);
            RequestBody inmuebleBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJson);
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imagen);
            MultipartBody.Part imagenPart = MultipartBody.Part.createFormData("imagen", "imagen.jpg", requestFile);

            ApiClient.InmoServices api = ApiClient.getInmoServices();
            String token = ApiClient.obtenerToken(getApplication());
            Call<Inmueble> call = api.CargarInmueble(token, imagenPart, inmuebleBody);
            call.enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(getApplication(), "Inmueble cargado con exito", Toast.LENGTH_LONG).show();
                        mBotonNombre.setValue("Nuevo Inmueble");
                        mColorBoton.setValue(R.color.purple_500);
                        mEstado.setValue(false);
                        mFondoEditText.setValue(R.drawable.edditext_fondo_bloqueado);
                        mColorTexto.setValue(R.color.grisHint);
                        mColorBotonImagen.setValue(R.color.grisHint);
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable throwable) {
                    Toast.makeText(getApplication(), "Error al cargar el inmueble", Toast.LENGTH_LONG).show();
                }
            });


        } catch (NumberFormatException e) {
            Toast.makeText(getApplication(), "Error en los campos numericos", Toast.LENGTH_LONG).show();
        }

    }

    private byte[] transformarImagen() {

        try {

            InputStream inputStream = getApplication().getContentResolver().openInputStream(mUri.getValue());
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplication(), "No se encontro la imagen", Toast.LENGTH_LONG).show();
            return new byte[]{};
        } catch (NullPointerException e) {
            Toast.makeText(getApplication(), "No se encontro la imagen", Toast.LENGTH_LONG).show();
            return new byte[]{};
        }


    }


}