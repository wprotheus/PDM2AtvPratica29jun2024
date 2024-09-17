package com.wprotheus.pdm2atvpratica29jun2024.view;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.wprotheus.pdm2atvpratica29jun2024.databinding.ActivityMainBinding;
import com.wprotheus.pdm2atvpratica29jun2024.util.MyPrefData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding mBinding;
    private MyPrefData myPrefData;

    @SuppressLint({"SourceLockedOrientationActivity", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        myPrefData = new MyPrefData(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mBinding.btnJogar.setOnClickListener(this);
        mBinding.tietNome.setOnFocusChangeListener(((v, hasFocus) -> {
            if (!hasFocus) hideKeyboard(v);
        }));
        mBinding.tietQuantidade.setOnFocusChangeListener(((v, hasFocus) -> {
            if (!hasFocus) hideKeyboard(v);
        }));
    }

    private void jogar() {
        String nome = mBinding.tietNome.getText().toString();
        String quantidade = mBinding.tietQuantidade.getText().toString();
        if (nome.isEmpty() || quantidade.isEmpty()) {
            Toast.makeText(this.getApplicationContext(), "Preencher todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }
        if (myPrefData != null)
            myPrefData.salvaNomeQuantidade(nome, quantidade);
        else {
            Log.e("MainActivity", "myPrefData está vazio.");
            return;
        }
        startActivity(new Intent(getApplicationContext(), MostrarNumerosActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        jogar();
    }
}