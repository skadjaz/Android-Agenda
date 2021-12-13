package com.est.agenda_est;

import static android.graphics.Color.*;
import static android.graphics.Color.parseColor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Criação de variaveis
    SearchView search_bar;
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    Button back;
    View ab;
    ImageView sem_resultado;
    TextView sem_resultado_texto;
    Database db;
    ArrayList<String> id,nome,sobrenome,email,morada,contacto;
    CustomAdapter customAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Definição de variaveis
        ab = findViewById(R.id.include2);
        back = findViewById(R.id.back_arrow);
        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        sem_resultado = findViewById(R.id.sem_resultado);
        sem_resultado_texto = findViewById(R.id.sem_resultado_texto);
        search_bar = findViewById(R.id.search_bar);
        //Adapting action bar to main activity
        ab.setBackgroundColor(WHITE);
        back.setVisibility(View.GONE);

        //set listener
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        //Initialize db
        db = new Database(MainActivity.this);
        id = new ArrayList<>();
        nome = new ArrayList<>();
        sobrenome = new ArrayList<>();
        email = new ArrayList<>();
        morada = new ArrayList<>();
        contacto = new ArrayList<>();

        populateArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, id, nome, sobrenome,email,morada, contacto);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapter.getFilter().filter(newText);
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }
    void populateArrays(){
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0){
            sem_resultado.setVisibility(View.VISIBLE);
            sem_resultado_texto.setVisibility(View.VISIBLE);
        }else {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                nome.add(cursor.getString(1));
                sobrenome.add(cursor.getString(2));
                email.add(cursor.getString(3));
                morada.add(cursor.getString(4));
                contacto.add(cursor.getString(5));
            }
            sem_resultado.setVisibility(View.GONE);
            sem_resultado_texto.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.meu_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.delete_all){
            confirmDialog();
            Toast.makeText(this, "Dados Removidos com Sucesso", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remover todos os Contactos ?");
        builder.setMessage("Tem a certeza que deseja remover todos os contactos?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Database myDB = new Database(MainActivity.this);
                myDB.deleteAllData();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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