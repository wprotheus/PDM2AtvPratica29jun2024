package com.wprotheus.pdm2atvpratica29jun2024.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.wprotheus.pdm2atvpratica29jun2024.databinding.ActivityMostrarNumerosBinding;
import com.wprotheus.pdm2atvpratica29jun2024.model.Partida;
import com.wprotheus.pdm2atvpratica29jun2024.util.MyPrefData;
import com.wprotheus.pdm2atvpratica29jun2024.util.TocarMusica;

import java.util.Arrays;

public class MostrarNumerosActivity extends AppCompatActivity {
    private static final String QUANTIDADE_KEY = "quantidade";
    private static final String MYPREF = "Registro_Partidas";
    private ActivityMostrarNumerosBinding mBinding;
    private SharedPreferences shPref;
    private Handler handler;
    private int somaApp = 0;
    private Partida p = new Partida();
    private MyPrefData myPrefData;

    private static void pausa() {
        try {
            Thread.sleep(1700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mBinding = ActivityMostrarNumerosBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        myPrefData = new MyPrefData(this);
        shPref = getSharedPreferences(MYPREF, MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        int qtd = Integer.parseInt(shPref.getString(QUANTIDADE_KEY, "quantidade"));
        int[] numbers = p.numeros(qtd);
        somaApp = p.soma(numbers);
        if (myPrefData != null)
            myPrefData.salvaNomeNumerosSoma(Arrays.toString(numbers), somaApp);
        else {
            Log.e("MostrarNumerosActivity", "myPrefData está vazio.");
            return;
        }
        iniciarMusicaMostrarNumeros(numbers);
    }

    private void iniciarMusicaMostrarNumeros(int[] numbers) {
        handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            Intent intent = new Intent(MostrarNumerosActivity.this, TocarMusica.class);
            startService(intent);
            for (int n : numbers) {
                pausa();
                handler.post(() -> {
                    mBinding.tvNrAleatorios.setText(String.valueOf(n));
                });
            }
            pausa();
            stopService(intent);
            conferirPartida();
        }).start();
    }

    private void conferirPartida() {
        startActivity(new Intent(getApplicationContext(), VerificarSomaActivity.class));
        finish();
    }
}