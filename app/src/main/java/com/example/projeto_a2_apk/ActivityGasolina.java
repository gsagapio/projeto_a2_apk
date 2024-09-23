package com.example.projeto_a2_apk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ActivityGasolina extends AppCompatActivity {

    private Button btHome, botaoLimpar;
    private EditText inputPrecoGasolina, inputPrecoEtanol;
    private RadioGroup radioGroup;
    private RadioButton rbGasolina, rbEtanol;
    private TextView relacaoPreco, melhorCombustivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasolina);
        configureEdgeToEdge();
        initializeViews();
        configureRadioGroupListener();
        configureClearButton();
        configureHomeButton();
    }

    private void configureEdgeToEdge() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        radioGroup = findViewById(R.id.rGOpcoes);
        rbGasolina = findViewById(R.id.rbGasolina);
        rbEtanol = findViewById(R.id.rbEtanol);
        inputPrecoGasolina = findViewById(R.id.tinputPrecoGasolina);
        inputPrecoEtanol = findViewById(R.id.tinputPrecoEtanol);
        relacaoPreco = findViewById(R.id.tvRelacaoPreco);
        melhorCombustivel = findViewById(R.id.tvMelhorPreco);
        botaoLimpar = findViewById(R.id.btLimpar);
        btHome = findViewById(R.id.btHome);
    }

    private void configureRadioGroupListener() {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> calcularRelacao());
    }

    private void calcularRelacao() {
        String gasolinaString = inputPrecoGasolina.getText().toString();
        String etanolString = inputPrecoEtanol.getText().toString();

        if (gasolinaString.isEmpty() || etanolString.isEmpty()) {
            melhorCombustivel.setText("Preencha os valores corretamente");
            return;
        }

        double gasolina = Double.parseDouble(gasolinaString);
        double etanol = Double.parseDouble(etanolString);
        double relacaoIdeal = 0.7;
        double relacaoAtual = etanol / gasolina;

        if (rbEtanol.isChecked()) {
            if (relacaoAtual <= relacaoIdeal) {
                melhorCombustivel.setText("Abasteça com Etanol");
            } else {
                melhorCombustivel.setText("Abasteça com Gasolina");
            }
            relacaoPreco.setText("Diferença de: " + String.format("%.2f", (relacaoAtual * 100)) + "%");
        } else if (rbGasolina.isChecked()) {
            if (relacaoAtual > relacaoIdeal) {
                melhorCombustivel.setText("Abasteça com Etanol");
            } else {
                melhorCombustivel.setText("Abasteça com Gasolina");
            }
            relacaoPreco.setText("Diferença de: " + String.format("%.2f", (1 / relacaoAtual * 100)) + "%");
        }
    }

    private void configureClearButton() {
        botaoLimpar.setOnClickListener(view -> {
            inputPrecoGasolina.setText("");
            inputPrecoEtanol.setText("");
            relacaoPreco.setText("Relação do Preço");
            melhorCombustivel.setText("Melhor Combustível");
        });
    }

    private void configureHomeButton() {
        btHome.setOnClickListener(view -> startActivity(new Intent(ActivityGasolina.this, MainActivity.class)));
    }
}
