package com.dam.proyecto_parejas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    //MENU PRINCIPAL
    public void menu_principal(View view){
        Intent principal = new Intent(this, Principal.class);
        startActivity(principal);
    }
}