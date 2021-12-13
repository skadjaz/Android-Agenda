package com.est.agenda_est;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {
    //Criação de variaveis
    EditText nome_input,sobrenome_input,contacto_input;
    Button add_button,back_button;
    ImageView menu_item;
    View ab;
    TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Atribuição de layout
        setContentView(R.layout.activity_add);

        // Atribuição de elementos do layout ás respectivas variáveis
        title = findViewById(R.id.titulo);
        menu_item = findViewById(R.id.menu_item);
        nome_input = findViewById(R.id.nome_input);
        sobrenome_input = findViewById(R.id.sobrenome_input);
        contacto_input = findViewById(R.id.contacto_input);
        add_button = findViewById(R.id.add_button);
        back_button = findViewById(R.id.back_arrow);

        // Ajuste da action bar
        title.setText("Adicionar Contacto");
        menu_item.setVisibility(View.GONE);

        //go back Listener
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // add contact Listener
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicializar base de dados
                Database myDB = new Database(AddActivity.this);
                // Chamar método de adicionar contacto
                myDB.addContacto(nome_input.getText().toString().trim(),
                        sobrenome_input.getText().toString().trim(),
                        Integer.valueOf(contacto_input.getText().toString().trim()));
                Intent intent = new Intent(AddActivity.this, AddSucess.class);
                intent.putExtra("nome_added",nome_input.getText().toString().trim());
                intent.putExtra("sobrenome_added",sobrenome_input.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}