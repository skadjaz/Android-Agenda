package com.est.agenda_est;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    //Declaração de Variáveis
    EditText nome_input,sobrenome_input,contacto_input;
    Button update_button,back_button;
    String id,nome,sobrenome,contacto;
    TextView titulo,titulo_user;
    // Inicialização da atividade
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Get elements
        nome_input = findViewById(R.id.nome_input2);
        sobrenome_input = findViewById(R.id.sobrenome_input2);
        contacto_input = findViewById(R.id.contacto_input2);
        update_button = findViewById(R.id.update_button);
        back_button = findViewById(R.id.back_arrow);
        titulo = findViewById(R.id.titulo);
        titulo_user = findViewById(R.id.titulo_user);

        // Set elements data
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
                contacto = contacto_input.getText().toString().trim();
                myDB.updateData(id,nome,sobrenome,contacto);

                //Preparing data for previous activity

                Intent returned = new Intent(UpdateActivity.this,contacto.class);
                returned.putExtra("id_updated",id);
                returned.putExtra("nome_updated",nome);
                returned.putExtra("sobrenome_updated",sobrenome);
                returned.putExtra("contacto_updated",contacto);
                startActivity(returned);
            }
        });
    }

    // Method for getting and setting intent data
    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("nome") && getIntent().hasExtra("sobrenome") && getIntent().hasExtra("contacto")){
            // Getting Data
            id = getIntent().getStringExtra("id");
            nome = getIntent().getStringExtra("nome");
            sobrenome = getIntent().getStringExtra("sobrenome");
            contacto = getIntent().getStringExtra("contacto");

            //Setting Data
            nome_input.setText(nome);
            sobrenome_input.setText(sobrenome);
            contacto_input.setText(contacto);
        }else if (getIntent().hasExtra("id_updated") &&
                getIntent().hasExtra("nome_updated") &&
                getIntent().hasExtra("sobrenome_updated") &&
                getIntent().hasExtra("contacto_updated")){
            // Getting Data
            id = getIntent().getStringExtra("id_updated");
            nome = getIntent().getStringExtra("nome_updated");
            sobrenome = getIntent().getStringExtra("sobrenome_updated");
            contacto = getIntent().getStringExtra("contacto_updated");

            //Setting Data
            nome_input.setText(nome +' '+ sobrenome);
            sobrenome_input.setText(sobrenome);
            contacto_input.setText(contacto);

        }else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}