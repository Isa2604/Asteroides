package com.example.asteroides;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //ANIMACIONES
//        TextView myTextView = findViewById(R.id.miTextView);
//        Animation giroConZoom = AnimationUtils.loadAnimation(this,
//                R.anim.giro_con_zoom);
//        myTextView.startAnimation(giroConZoom);
//        Button Button01 = findViewById(R.id.Button01);
//        Animation aparecer = AnimationUtils.loadAnimation(this,
//                R.anim.aparecer);
//        Button01.startAnimation(aparecer);
//        Button Button02 = findViewById(R.id.Button02);
//        Animation desplazamientoDerecha = AnimationUtils.loadAnimation(this,
//                R.anim.desplazamiento_derecha);
//        Button02.startAnimation(desplazamientoDerecha);
//
//        Button tercerBoton = findViewById(R.id.Button03);
//        Animation rotacion = AnimationUtils.loadAnimation(this,
//                R.anim.rotacion);
//        tercerBoton.startAnimation(rotacion);
//        Animation giroZoom = AnimationUtils.loadAnimation(this, R.anim.giro_con_zoom);
//
//        tercerBoton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.startAnimation(giroZoom);
//                lanzarAcercaDe(v);
//            }
//        });
//
//        Button cuartoBoton = findViewById(R.id.Button04);
//        Animation escalado = AnimationUtils.loadAnimation(this,
//                R.anim.escalado);
//        cuartoBoton.startAnimation(escalado);
//        //---------------------------------
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            lanzarPreferencias(null);
            return true;
        }
        if (id == R.id.acercaDe) {
            lanzarAcercaDe(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mostrarPreferencias(View view) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String s = "música: " + pref.getBoolean("musica", true)
                + ", gráficos: " + pref.getString("graficos", "?");
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    public void salir(View view){
        finish();
    }
    public void lanzarJuego(View view){
        Intent i = new Intent(this, Juego.class);
        startActivity(i);
    }
    public void lanzarAcercaDe(View view){
        Intent i = new Intent(this, AcercaDeActivity.class);
        startActivity(i);
    }
    public void lanzarPreferencias(View view){
        Intent i = new Intent(this, Preferencias.class);
        startActivity(i);
    }
    public void lanzarPuntuaciones(View view) {
        Intent i = new Intent(this, Puntuaciones.class);
        startActivity(i);
    }
    public static AlmacenPuntuaciones almacen= new AlmacenPuntuacionesList();
}