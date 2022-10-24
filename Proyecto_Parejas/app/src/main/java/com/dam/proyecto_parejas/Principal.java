package com.dam.proyecto_parejas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import java.util.Timer;
import java.util.TimerTask;

public class Principal extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager);
        myViewPagerAdapter = new MyViewPagerAdapter(this);
        viewPager2.setAdapter(myViewPagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

         viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

             @Override
             public void onPageSelected(int position) {
                 super.onPageSelected(position);
                 tabLayout.getTabAt(position).select();
             }
         });
    }
    public void abcedario(){
        Intent principal = new Intent(this, com.dam.proyecto_parejas.home.abcedario.class);
        startActivity(principal);
    }

    public void Alimentos(){
        Intent principal = new Intent(this, com.dam.proyecto_parejas.home.Alimentos.class);
        startActivity(principal);
    }

    public void Colores(){
        Intent principal = new Intent(this, com.dam.proyecto_parejas.home.Colores.class);
        startActivity(principal);
    }

    public void Familia(){
        Intent principal = new Intent(this, com.dam.proyecto_parejas.home.Familia.class);
        startActivity(principal);
    }

    public void Numero(){
        Intent principal = new Intent(this, com.dam.proyecto_parejas.home.Numero.class);
        startActivity(principal);
    }

    public void Saludos(){
        Intent principal = new Intent(this, com.dam.proyecto_parejas.home.Saludos.class);
        startActivity(principal);
    }
}