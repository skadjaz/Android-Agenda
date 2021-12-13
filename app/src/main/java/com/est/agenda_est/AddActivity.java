package com.est.agenda_est;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {
    //Criação de variaveis
    EditText nome_input,sobrenome_input,email_input,morada_input,contacto_input;
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
        email_input = findViewById(R.id.email_input);
        morada_input = findViewById(R.id.morada_input);
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
        // Acção de click , trigger adicionar na base de dados
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicializar base de dados
                Database myDB = new Database(AddActivity.this);
                // Chamar método de adicionar contacto

                boolean digitsOnly = TextUtils.isDigitsOnly(contacto_input.getText());

                if (digitsOnly && contacto_input != null){
                    myDB.addContacto(nome_input.getText().toString().trim(),
                            sobrenome_input.getText().toString().trim(),
                            email_input.getText().toString().trim(),
                            morada_input.getText().toString().trim(),
                            Integer.valueOf(contacto_input.getText().toString().trim()), digitsOnly);
                }else {
                    myDB.addContacto(nome_input.getText().toString().trim(),
                            sobrenome_input.getText().toString().trim(),
                            email_input.getText().toString().trim(),
                            morada_input.getText().toString().trim(),
                            0, digitsOnly);
                }
            }
        });
    }
}