package com.example.projeto_a2_apk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityIMC extends AppCompatActivity {

    private Button btHome, botaoEnviar, botaoLimpar;
    private EditText inputPeso, inputAltura;
    private TextView saidaResultado, classificacaoImc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        configureEdgeToEdge();
        initializeViews();
        configureHomeButton();
        configureCalculateButton();
        configureClearButton();
    }

    private void configureEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        btHome = findViewById(R.id.btHome);
        botaoEnviar = findViewById(R.id.btEnviar);
        botaoLimpar = findViewById(R.id.btLimpar);
        inputPeso = findViewById(R.id.tinputPeso);
        inputAltura = findViewById(R.id.tinputAltura);
        saidaResultado = findViewById(R.id.tvIMCResultado);
        classificacaoImc = findViewById(R.id.tvClassificacao);
    }

    private void configureHomeButton() {
        btHome.setOnClickListener(view -> {
            Intent intent = new Intent(ActivityIMC.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void configureCalculateButton() {
        botaoEnviar.setOnClickListener(view -> {
            String pesoString = inputPeso.getText().toString();
            String alturaString = inputAltura.getText().toString();

            if (pesoString.isEmpty() || alturaString.isEmpty()) {
                saidaResultado.setText("Favor inserir informações");
                classificacaoImc.setText("");
                return;
            }

            float peso = Float.parseFloat(pesoString);
            float altura = Float.parseFloat(alturaString);

            float imc = calcularIMC(peso, altura);
            saidaResultado.setText(String.format("%.1f", imc));
            classificacaoImc.setText(classificarIMC(imc));
        });
    }

    private float calcularIMC(float peso, float altura) {
        return (peso * 10000) / (altura * altura);
    }

    private String classificarIMC(float imc) {
        if (imc <= 18.4) {
            return "Magreza";
        } else if (imc <= 24.9) {
            return "Normal";
        } else if (imc <= 29.9) {
            return "Sobrepeso";
        } else {
            return "Obeso";
        }
    }

    private void configureClearButton() {
        botaoLimpar.setOnClickListener(view -> {
            inputPeso.setText("");
            inputAltura.setText("");
            saidaResultado.setText("Resultado IMC");
            classificacaoImc.setText("Classificação");
        });
    }
}
