package com.est.agenda_est;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdateUnsucessfully extends AppCompatActivity {
    TextView nomeContacto;
    ImageView homeButton,goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editadosemsucesso);

        nomeContacto = findViewById(R.id.nomeContacto);
        homeButton = findViewById(R.id.homeButton);
        goBack = findViewById(R.id.goBack);
        getAndSetIntentData();


        goBack.setOnClickListener(v -> onBackPressed());
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(UpdateUnsucessfully.this, MainActivity.class);
            startActivity(intent);
        });
    }
    void getAndSetIntentData() {
        if (getIntent().hasExtra("nome_updated") && getIntent().hasExtra("sobrenome_updated")) {
            // Getting Data
            String nome = getIntent().getStringExtra("nome_updated");
            String sobrenome = getIntent().getStringExtra("sobrenome_updated");
            String titulo = nome + " " + sobrenome;

            nomeContacto.setText(titulo);
        }
    }
}