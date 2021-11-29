package com.est.agenda_est;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView sem_resultado;
    TextView sem_resultado_texto;

    Database db;
    ArrayList<String> id,nome,sobrenome,contacto;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        ab.setTitle(Html.fromHtml("<font color=\"#2699FB\">" + getString(R.string.app_name) + "</font>"));

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        sem_resultado = findViewById(R.id.sem_resultado);
        sem_resultado_texto = findViewById(R.id.sem_resultado_texto);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });

        db = new Database(MainActivity.this);
        id = new ArrayList<>();
        nome = new ArrayList<>();
        sobrenome = new ArrayList<>();
        contacto = new ArrayList<>();

        populateArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, id, nome, sobrenome, contacto);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

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
                contacto.add(cursor.getString(3));
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