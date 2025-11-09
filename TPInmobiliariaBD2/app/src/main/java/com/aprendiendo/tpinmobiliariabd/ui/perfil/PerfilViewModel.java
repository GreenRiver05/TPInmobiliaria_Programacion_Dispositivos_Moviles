package com.aprendiendo.tpinmobiliariabd.ui.perfil;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.aprendiendo.tpinmobiliariabd.R;
import com.aprendiendo.tpinmobiliariabd.modelos.Propietario;
import com.aprendiendo.tpinmobiliariabd.request.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PerfilViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> mEstado = new MutableLiveData<>();
    private MutableLiveData<String> mNombre = new MutableLiveData<>();
    private MutableLiveData<Propietario> mPropietario = new MutableLiveData<>();
    private MutableLiveData<Integer> mFondoEditText = new MutableLiveData<>();
    private MutableLiveData<Integer> mColorBoton = new MutableLiveData<>();
    private MutableLiveData<Integer> mColorTexto =  new MutableLiveData<>();





    public PerfilViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getmEstado() {return mEstado;}
    public LiveData<String> getmNombre() {return mNombre;}
    public LiveData<Propietario> getmPropietario() {return mPropietario;}
    public LiveData<Integer> getFondoEditText() {return mFondoEditText;}
    public LiveData<Integer> getColorBoton() {return mColorBoton;}
    public LiveData<Integer> getColorTexto() {return mColorTexto;}


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
            mFondoEditText.setValue(R.drawable.edittext_background);
            mColorBoton.setValue(R.color.verde_boton);
            mColorTexto.setValue(R.color.black);
        } else {
            mEstado.setValue(false);
            mNombre.setValue("Editar");
            mColorTexto.setValue(R.color.grisHint);
            mFondoEditText.setValue(R.drawable.edditext_fondo_bloqueado);
            mColorBoton.setValue(R.color.purple_500);

            if(nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || telefono.isEmpty() || email.isEmpty()){
                Toast.makeText(getApplication(), "Debe completar todos los campos", Toast.LENGTH_LONG).show();
                return;
            }
            if(!email.contains("@")){
                Toast.makeText(getApplication(), "El email no es válido", Toast.LENGTH_LONG).show();
                return;
            }

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


    public void resetearClave(String email) {
        if(email.isEmpty()){
            Toast.makeText(getApplication(), "Debe completar el campo", Toast.LENGTH_LONG).show();
            return;
        }
        if(!email.contains("@")){
            Toast.makeText(getApplication(), "El email no es válido", Toast.LENGTH_LONG).show();
            return;
        }
        ApiClient.InmoServices api = ApiClient.getInmoServices();
        Call<String> call = api.resetPassword(email);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getApplication(), response.body(), Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplication(), "Error al enviar el email", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Toast.makeText(getApplication(), "Error en la API", Toast.LENGTH_LONG);
            }
        });
    }


    public void reiniciarPerfil() {
        mEstado.setValue(false);
        mNombre.setValue("Editar");
        mFondoEditText.setValue(R.drawable.edditext_fondo_bloqueado);
        mColorBoton.setValue(R.color.purple_500);
        mColorTexto.setValue(R.color.grisHint);
        obtenerPefil();
    }
}