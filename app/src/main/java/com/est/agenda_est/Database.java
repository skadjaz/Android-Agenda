package com.est.agenda_est;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "ListaContactos.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "contactos";
    private static final String COL_ID = "_id";
    private static final String COL_NAME = "nome";
    private static final String COL_SOBRENOME = "sobrenome";
    private static final String COL_EMAIL = "email";
    private static final String COL_MORADA = "morada";
    private static final String COL_TELEFONE = "telemovel";


    Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " +
                TABLE_NAME + "" + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_SOBRENOME + " TEXT, " +
                COL_EMAIL + " TEXT, " +
                COL_MORADA + " TEXT, " +
                COL_TELEFONE + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    void addContacto(String nome, String sobrenome, String email, String morada, String telemovel){
        boolean digitsOnly = TextUtils.isDigitsOnly(telemovel);
        if (digitsOnly && telemovel != null){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COL_NAME,nome);
            cv.put(COL_SOBRENOME,sobrenome);
            cv.put(COL_EMAIL,email);
            cv.put(COL_MORADA,morada);
            cv.put(COL_TELEFONE,telemovel);

            long result = db.insert(TABLE_NAME,null,cv);
            if (result == -1){
                Intent intent = new Intent(context, AddNoSucess.class);
                intent.putExtra("nome_added",nome);
                intent.putExtra("sobrenome_added",sobrenome);
                context.startActivity(intent);
            }else {
                Intent intent = new Intent(context, AddSucess.class);
                intent.putExtra("nome_added",nome);
                intent.putExtra("sobrenome_added",sobrenome);
                context.startActivity(intent);
            }
        }else{
            Intent intent = new Intent(context, AddNoSucess.class);
            intent.putExtra("nome_added",nome);
            intent.putExtra("sobrenome_added",sobrenome);
            context.startActivity(intent);
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_NAME + " COLLATE NOCASE ASC ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if (db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id,String nome, String sobrenome, String email, String morada, String telemovel){

        boolean digitsOnly = TextUtils.isDigitsOnly(telemovel);

        if (digitsOnly && telemovel != null){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(COL_NAME,nome);
            cv.put(COL_SOBRENOME,sobrenome);
            cv.put(COL_EMAIL,email);
            cv.put(COL_MORADA,morada);
            cv.put(COL_TELEFONE,telemovel);

            long result = db.update(TABLE_NAME,cv,"_id=?", new String[]{row_id});

            if (result == -1){
                Intent intent = new Intent(context, UpdateUnsucessfully.class);
                intent.putExtra("nome_updated",nome);
                intent.putExtra("sobrenome_updated",sobrenome);
                context.startActivity(intent);
            }else{
                Intent intent = new Intent(context, UpdateSucessfully.class);
                intent.putExtra("nome_updated",nome);
                intent.putExtra("sobrenome_updated",sobrenome);
                context.startActivity(intent);
            }
        }else {
            Intent intent = new Intent(context, UpdateUnsucessfully.class);
            intent.putExtra("nome_updated",nome);
            intent.putExtra("sobrenome_updated",sobrenome);
            context.startActivity(intent);
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1){
            Toast.makeText(context, "Erro ao remover Contacto", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Contacto Removido com Sucesso!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
