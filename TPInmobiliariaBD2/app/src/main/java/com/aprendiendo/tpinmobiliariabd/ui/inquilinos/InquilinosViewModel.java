package com.aprendiendo.tpinmobiliariabd.ui.inquilinos;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.aprendiendo.tpinmobiliariabd.modelos.Inquilino;


public class InquilinosViewModel extends AndroidViewModel {

    private MutableLiveData<Inquilino> mInquilino = new MutableLiveData<>();

    public InquilinosViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Inquilino> getmInquilino() { return mInquilino; }


    public void recuperarInquilino(Bundle bundle) {
        Inquilino inquilino = (Inquilino) bundle.get("inquilino");
        if (inquilino == null) {
            Toast.makeText(getApplication(), "Error al recuperar el Inquilino", Toast.LENGTH_LONG).show();
        } else {
            mInquilino.setValue(inquilino);
        }
    }

}