package com.est.agenda_est;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText nome_input,sobrenome_input,contacto_input;
    Button update_button,delete_button;
    String id,nome,sobrenome,contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        nome_input = findViewById(R.id.nome_input2);
        sobrenome_input = findViewById(R.id.sobrenome_input2);
        contacto_input = findViewById(R.id.contacto_input2);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        //Getting Data
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();


        if (ab != null){
            ab.setTitle(Html.fromHtml("<font color=\"#2699FB\">" + nome + " " + sobrenome + "</font>"));
        }


        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Updating Data
                Database myDB = new Database(UpdateActivity.this);
                nome = nome_input.getText().toString().trim();
                sobrenome = sobrenome_input.getText().toString().trim();
                contacto = contacto_input.getText().toString().trim();
                myDB.updateData(id,nome,sobrenome,contacto);
                finish();
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });
    }

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
        }else {
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
                Database myDB = new Database(UpdateActivity.this);
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