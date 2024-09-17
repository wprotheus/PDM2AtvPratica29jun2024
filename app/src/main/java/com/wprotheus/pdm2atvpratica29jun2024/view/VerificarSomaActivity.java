package com.wprotheus.pdm2atvpratica29jun2024.view;

import static com.google.android.material.internal.ViewUtils.hideKeyboard;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.SQLException;
import android.graphics.Color;
import android.graphics.fonts.FontStyle;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wprotheus.pdm2atvpratica29jun2024.R;
import com.wprotheus.pdm2atvpratica29jun2024.databinding.ActivityVerificarSomaBinding;
import com.wprotheus.pdm2atvpratica29jun2024.model.MyContentProvider;
import com.wprotheus.pdm2atvpratica29jun2024.util.MyPrefData;

public class VerificarSomaActivity extends AppCompatActivity {
    private static final String MYPREF = "Registro_Partidas";
    private static final String NOME_KEY = "nome";
    private static final String NUMEROS_KEY = "numeros";
    private static final String SOMA_KEY = "soma";
    private static final String RESULTADO_KEY = "resultado";
    private static final String IMAGEM_KEY = "imagem";
    private ActivityVerificarSomaBinding mBinding;
    private MyPrefData myPrefData;
    private SharedPreferences shPref;
    private int somaUser;

    @SuppressLint({"SourceLockedOrientationActivity", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        mBinding = ActivityVerificarSomaBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        myPrefData = new MyPrefData(this);
        shPref = getSharedPreferences(MYPREF, MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mBinding.btnConferir.setOnClickListener(v -> conferir());
        mBinding.btnHome.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
        mBinding.ettResultado.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) hideKeyboard(v);
        });
    }

    private void conferir() {
        int somaPartida = Integer.parseInt(shPref.getString(SOMA_KEY, "soma"));
        String valor = mBinding.ettResultado.getText().toString();
        if (valor.isEmpty())
            Toast.makeText(this, "Digite um valor para a soma", Toast.LENGTH_SHORT).show();
        else {
            somaUser = Integer.parseInt(valor);
            verificarPartida(somaPartida, somaUser);
        }
    }

    private void verificarPartida(Integer somaPartida, int somaUser) {
        mBinding.btnConferir.setEnabled(false);
        mBinding.ettResultado.setEnabled(false);
        if (somaPartida == somaUser) {
            mBinding.ivResultado.setImageResource(R.drawable.feliz);
            salvarPref("Acertou", "1");
        } else {
            mBinding.ivResultado.setImageResource(R.drawable.errou);
            salvarPref("Errou", "0");
        }
    }

    private void salvarPref(String resultado, String imagem) {
        if (myPrefData != null) {
            myPrefData.salvaResultadoImagem(resultado, imagem);
            inserirPartida();
        } else
            Log.e("VerificarSomaActivity", "myPrefData está vazio.");
    }

    private void inserirPartida() {
        String imagem = shPref.getString(IMAGEM_KEY, "imagem");

        ContentValues values = new ContentValues();
        values.put("NOME", shPref.getString(NOME_KEY, "nome"));
        values.put("NUMEROS", shPref.getString(NUMEROS_KEY, "numeros"));
        values.put("SOMA", shPref.getString(SOMA_KEY, "soma"));
        values.put("RESULTADO", shPref.getString(RESULTADO_KEY, "resultado"));
        values.put("IMAGEM", imagem);

        Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
        if (uri != null)
            mensagem(imagem);
        else {
            Toast.makeText(this, "Erro ao salvar a partida.", Toast.LENGTH_SHORT).show();
            throw new SQLException("Erro ao gravar dados." + MyContentProvider.CONTENT_URI);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void mensagem(String imagem) {
        String resultado = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
        if (imagem.equals("0"))
            resultado = "ERROU!\nVocê perdeu.";
        else
            resultado = "ACERTOU!\nVocê ganhou.";
        builder.setTitle("Resultado da partida:\n")
                .setMessage(resultado)
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        TextView textView = dialog.findViewById(android.R.id.message);
        assert textView != null;
        textView.setTextSize(26);
        textView.setFontFeatureSettings(String.valueOf(FontStyle.FONT_WEIGHT_MAX));
        if (imagem.equals("0"))
            textView.setTextColor(Color.rgb(191, 54, 12));
        else
            textView.setTextColor(Color.BLUE);
    }
}