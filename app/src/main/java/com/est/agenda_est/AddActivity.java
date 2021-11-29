package com.est.agenda_est;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText nome_input,sobrenome_input,contacto_input;
    Button add_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nome_input = findViewById(R.id.nome_input);
        sobrenome_input = findViewById(R.id.sobrenome_input);
        contacto_input = findViewById(R.id.contacto_input);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database myDB = new Database(AddActivity.this);
                myDB.addContacto(nome_input.getText().toString().trim(),sobrenome_input.getText().toString().trim(),Integer.valueOf(contacto_input.getText().toString().trim()));
            }
        });
    }
}