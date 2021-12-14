package com.est.agenda_est;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    //Declaração de Variáveis
    EditText nome_input,sobrenome_input,email_input,morada_input,contacto_input;
    Button update_button,back_button;
    String id,nome,sobrenome,email,morada,contacto;
    TextView titulo,titulo_user;
    // Inicialização da atividade
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Get elements and Set elements data
        nome_input = findViewById(R.id.nome_input2);
        sobrenome_input = findViewById(R.id.sobrenome_input2);
        email_input = findViewById(R.id.email_input2);
        morada_input = findViewById(R.id.morada_input2);
        contacto_input = findViewById(R.id.contacto_input2);
        update_button = findViewById(R.id.update_button);
        back_button = findViewById(R.id.back_arrow);
        titulo = findViewById(R.id.titulo);
        titulo_user = findViewById(R.id.titulo_user);

        //
        // Get and set Intent Data
        getAndSetIntentData();

        // Set title Data
        if (titulo != null){
            titulo.setText("Editar Contacto");
            titulo_user.setText(nome+" "+sobrenome);
        }

        //Go back to previous activity listener
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Update Contact listener
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Updating Data
                Database myDB = new Database(UpdateActivity.this);
                nome = nome_input.getText().toString().trim();
                sobrenome = sobrenome_input.getText().toString().trim();
                email = email_input.getText().toString().trim();
                morada = morada_input.getText().toString().trim();
                contacto = contacto_input.getText().toString().trim();


                myDB.updateData(id,nome,sobrenome,email,morada,contacto);
            }
        });
    }

    // Method for getting and setting intent data
    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") &&
                getIntent().hasExtra("nome") &&
                getIntent().hasExtra("sobrenome") &&
                getIntent().hasExtra("email") &&
                getIntent().hasExtra("morada") &&
                getIntent().hasExtra("contacto")){

            // Getting Data
            id = getIntent().getStringExtra("id");
            nome = getIntent().getStringExtra("nome");
            sobrenome = getIntent().getStringExtra("sobrenome");
            email = getIntent().getStringExtra("email");
            morada = getIntent().getStringExtra("morada");
            contacto = getIntent().getStringExtra("contacto");

            //Setting Data
            nome_input.setText(nome);
            sobrenome_input.setText(sobrenome);
            email_input.setText(email);
            morada_input.setText(morada);
            contacto_input.setText(String.valueOf(contacto));

        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}