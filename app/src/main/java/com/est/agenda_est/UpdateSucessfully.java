package com.est.agenda_est;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UpdateSucessfully extends AppCompatActivity {
    TextView nomeContacto;
    ImageView homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editadocomsucesso);

        nomeContacto = findViewById(R.id.nomeContacto);
        homeButton = findViewById(R.id.homeButton);
        getAndSetIntentData();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateSucessfully.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    void getAndSetIntentData() {
        if (getIntent().hasExtra("nome_updated") && getIntent().hasExtra("sobrenome_updated")) {
            // Getting Data
            nomeContacto.setText(getIntent().getStringExtra("nome_updated") + " " + getIntent().getStringExtra("sobrenome_updated"));
        }
    }
}