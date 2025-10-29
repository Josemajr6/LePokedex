package com.lepokedex.activity;

import static com.lepokedex.database.PopulateDatabase.listaPokemons;
import static com.lepokedex.database.PopulateDatabase.populate;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lepokedex.R;
import com.lepokedex.adapter.PokemonAdapter;
import com.lepokedex.model.Pokemon;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populate();

        ListView lvPokemons = findViewById(R.id.lvPokemons);

        ArrayAdapter<Pokemon> adapterDefaultPokemon = new PokemonAdapter(MainActivity.this, 0 , listaPokemons);
        lvPokemons.setAdapter(adapterDefaultPokemon);

    }
}