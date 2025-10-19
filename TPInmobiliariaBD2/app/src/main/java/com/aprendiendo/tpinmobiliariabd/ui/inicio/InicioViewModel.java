package com.aprendiendo.tpinmobiliariabd.ui.inicio;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioViewModel extends AndroidViewModel {

    private MutableLiveData<MapaActual> mMapaActual;

    public InicioViewModel(@NonNull Application application) {
        super(application);

    }

    public LiveData<MapaActual> getMapaActual() {
        if (mMapaActual == null) {
            mMapaActual = new MutableLiveData<>();
        }
        return mMapaActual;
    }


    public void obtenerMapaActual() {
        MapaActual mapaActual = new MapaActual();
        mMapaActual.setValue(mapaActual);
    }


    public class MapaActual implements OnMapReadyCallback {
        LatLng INMOBILIARIA = new LatLng(-41.696808, -71.661658);
        LatLng LAGOESCONDIDO = new LatLng(-41.693313, -71.657565);

        @Override
        public void onMapReady(@NonNull GoogleMap googleMap) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.addMarker(new MarkerOptions().position(LAGOESCONDIDO).title("Lago Escondido"));
            googleMap.addMarker(new MarkerOptions().position(INMOBILIARIA).title("Inmobiliaria ULP"));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(LAGOESCONDIDO)     // Seteamos la posición de la cámara
                    .zoom(14)            // Seteamos el zoom de la cámara
                    .bearing(60)         // Seteamos la orientación de la cámara en grados
                    .tilt(60)            // Seteamos la inclinación de la cámara en grados
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
            googleMap.animateCamera(cameraUpdate);

        }
    }
}