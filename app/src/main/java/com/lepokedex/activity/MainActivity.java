package com.lepokedex.activity;

import static com.lepokedex.database.PopulateDatabase.listaPokemons;
import static com.lepokedex.database.PopulateDatabase.populate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lepokedex.R;
import com.lepokedex.adapter.PokemonAdapter;
import com.lepokedex.model.Pokemon;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<Pokemon> adapterDefaultPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Llamamos a populate() para añadir los Pokémons a la lista
        populate();

        // De momento importamos la listView para que aparezcan los Pokémon y el botón de añadir Pokémon
        ListView lvPokemons = findViewById(R.id.lvPokemons);
        Button botonAnadirPokemon = findViewById(R.id.botonAnadirPokemon);

        // Creamos el adapter para esta pantalla con la lista de Pokémons y la establecemos a lvPokemons
        adapterDefaultPokemon = new PokemonAdapter(MainActivity.this, 0 , listaPokemons);
        lvPokemons.setAdapter(adapterDefaultPokemon);

        // En el setOnClickListener del boton de añadir pokemon creamos un intent que vaya la vista de CreateActivity
        botonAnadirPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCrearPokemon = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(iCrearPokemon);
            }
        });
    }

    // Como hemos puesto finish en activity_create tenemos que refrescar la lista al volver a esta pantalla desde CreateActivity
    @Override
    protected void onResume() {
        super.onResume();
        if (adapterDefaultPokemon != null) {
            adapterDefaultPokemon.notifyDataSetChanged();
        }
    }


}