package com.est.agenda_est;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class contacto extends AppCompatActivity {
    //Criação de variaveis
    TextView nome_input,email_input,morada_input,contacto_input;
    Button edit_button,delete_button,back_button;
    String id, nome, sobrenome,email,morada, contacto;

    //Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Atribuição de layout
        setContentView(R.layout.contacto);

        // Atribuição de elementos do layout ás respectivas variáveis
        nome_input = findViewById(R.id.nome_contacto);
        email_input = findViewById(R.id.email_contacto);
        morada_input = findViewById(R.id.morada_contacto);
        contacto_input = findViewById(R.id.contacto_telemovel);
        edit_button = findViewById(R.id.edit_button);
        delete_button = findViewById(R.id.delete_button);
        back_button = findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goback = new Intent(contacto.this,MainActivity.class);
                startActivity(goback);
            }
        });
        getAndSetIntentData();
        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contacto.this,UpdateActivity.class);
                intent.putExtra("id",String.valueOf(id));
                intent.putExtra("nome",String.valueOf(nome));
                intent.putExtra("sobrenome",String.valueOf(sobrenome));
                intent.putExtra("email",String.valueOf(email));
                intent.putExtra("morada",String.valueOf(morada));
                intent.putExtra("contacto",String.valueOf(contacto));
                startActivity(intent);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }
    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") &&
                getIntent().hasExtra("nome") &&
                getIntent().hasExtra("sobrenome") &&
                getIntent().hasExtra("email") &&
                getIntent().hasExtra("morada")&&
                getIntent().hasExtra("contacto")) {

            // Getting Data
            id = getIntent().getStringExtra("id");
            nome = getIntent().getStringExtra("nome");
            sobrenome = getIntent().getStringExtra("sobrenome");
            email = getIntent().getStringExtra("email");
            morada = getIntent().getStringExtra("morada");
            contacto = getIntent().getStringExtra("contacto");

            //Setting Data
            nome_input.setText(nome +' '+ sobrenome);
            email_input.setText(email);
            morada_input.setText(morada);
            contacto_input.setText(contacto);

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover " + nome + " ?");
        builder.setMessage("Tem a certeza que deseja remover " + nome + "da sua lista de contactos?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Database myDB = new Database(contacto.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}