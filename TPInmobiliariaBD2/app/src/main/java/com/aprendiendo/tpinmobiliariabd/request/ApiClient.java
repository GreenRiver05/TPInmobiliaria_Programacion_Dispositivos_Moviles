package com.aprendiendo.tpinmobiliariabd.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.aprendiendo.tpinmobiliariabd.modelos.Contrato;
import com.aprendiendo.tpinmobiliariabd.modelos.Inmueble;
import com.aprendiendo.tpinmobiliariabd.modelos.Pago;
import com.aprendiendo.tpinmobiliariabd.modelos.Propietario;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

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

        @GET("api/inmuebles")
        Call<List<Inmueble>> getInmuebles(@Header("Authorization") String token);

        @PUT("api/inmuebles/actualizar")
        Call<Inmueble> actualizarInmueble(@Header("Authorization") String token, @Body Inmueble inmueble);

        @Multipart
        @POST("api/Inmuebles/cargar")
        Call<Inmueble> CargarInmueble(@Header("Authorization") String token,
                                      @Part MultipartBody.Part imagen,
                                      @Part("inmueble") RequestBody inmuebleBody);



        @GET("api/inmuebles/GetContratoVigente")
        Call<List<Inmueble>> getInmueblesConContrato(@Header("Authorization") String token);

        @GET("api/contratos/inmueble/{id}")
        Call<Contrato> getContrato(@Header("Authorization") String token, @Path("id") int id);

        @GET("api/pagos/contrato/{id}")
        Call<List<Pago>> getPagos(@Header("Authorization") String token, @Path("id") int id);

        @FormUrlEncoded
        @POST("api/Propietarios/email")
        Call<String> resetPassword(@Field("email") String email);

        @FormUrlEncoded
        @PUT("api/Propietarios/changePassword")
        Call<Void> cambiarClave(
                @Header("Authorization") String token,
                @Field("currentPassword") String currentPassword,
                @Field("newPassword") String newPassword
        );



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
