package com.est.agenda_est;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddSucess extends AppCompatActivity {

    TextView nomeContacto;
    ImageView homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsucessfully);

        nomeContacto = findViewById(R.id.nomeContacto);
        homeButton = findViewById(R.id.homeButton);
        getAndSetIntentData();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddSucess.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    void getAndSetIntentData() {
        if (getIntent().hasExtra("nome_added") && getIntent().hasExtra("sobrenome_added")) {
            // Getting Data
            nomeContacto.setText(getIntent().getStringExtra("nome_added") + " " + getIntent().getStringExtra("sobrenome_added"));
        }
    }
}