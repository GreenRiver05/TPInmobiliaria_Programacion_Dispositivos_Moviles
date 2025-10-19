package com.aprendiendo.tpinmobiliariabd.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.aprendiendo.tpinmobiliariabd.modelos.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public class ApiClient {
    public final static String BASE_URL = "https://inmobiliariaulp-amb5hwfqaraweyga.canadacentral-01.azurewebsites.net/";

    public static InmoServices getInmoServices() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(InmoServices.class);

    }

    public interface InmoServices {

        @FormUrlEncoded
        @POST("api/Propietarios/login")
        Call<String> login(@Field("Usuario") String usuario, @Field("clave") String clave);

        @GET("api/Propietarios")
        Call<Propietario> getPropietarios(@Header("Authorization") String token);

        @PUT("api/Propietarios/actualizar")
        Call<Propietario> actualizarPropietario(@Header("Authorization") String token, @Body Propietario propietario);


    }

    public static void guardarToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", "Bearer " + token);
        editor.commit();
    }

    public static String obtenerToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("token.xml", Context.MODE_PRIVATE);
        return sp.getString("token", null);

    }

}
