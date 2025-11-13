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

import androidx.appcompat.app.AppCompatActivity;

import com.lepokedex.R;
import com.lepokedex.adapter.PokemonAdapter;
import com.lepokedex.model.Pokemon;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declaramos las variables AQUÍ ARRIBA (Variables de Clase/Atributos)
    // Esto es necesario para que el método 'filtrar' y 'onResume' puedan acceder a ellas.
    private ArrayAdapter<Pokemon> adapterDefaultPokemon;
    private ListView lvPokemons;
    private Spinner spinnerFiltrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populate();

        // Enlazamos las variables con los IDs del XML
        lvPokemons = findViewById(R.id.lvPokemons);
        spinnerFiltrar = findViewById(R.id.spinnerFiltrar);
        Button botonFiltrar = findViewById(R.id.botonFiltrar);
        Button botonAnadirPokemon = findViewById(R.id.botonAnadirPokemon);

        // Inicializamos el adaptador principal con toda la lista
        adapterDefaultPokemon = new PokemonAdapter(MainActivity.this, 0 , listaPokemons);
        lvPokemons.setAdapter(adapterDefaultPokemon);

        // Configuramos el Spinner
        String[] tiposPokemon = { "Todos", "Fuego", "Agua", "Electrico", "Planta", "Normal" };
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposPokemon);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltrar.setAdapter(adapterSpinner);

        // Listener del Botón Filtrar
        botonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamamos a nuestro método propio, mucho más limpio
                filtrar();
            }
        });

        // Listener del Botón Añadir
        botonAnadirPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCrearPokemon = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(iCrearPokemon);
            }
        });

        // Listener de la Lista (Click en un Pokémon)
        lvPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // IMPORTANTE: Usamos parent.getItemAtPosition para coger el Pokémon correcto
                // incluso si la lista está filtrada.
                Pokemon p = (Pokemon) parent.getItemAtPosition(position);

                Intent iDetalles = new Intent(MainActivity.this, DetailsActivity.class);
                iDetalles.putExtra("pokemon", p);
                startActivity(iDetalles);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 1. Avisamos al adaptador principal de que puede haber datos nuevos
        if (adapterDefaultPokemon != null) {
            adapterDefaultPokemon.notifyDataSetChanged();
        }

        // 2. Volvemos a aplicar el filtro automáticamente
        // Esto hace que si añadiste un Pokémon de tipo "Fuego" y el filtro estaba en "Fuego",
        // aparezca inmediatamente al volver.
        filtrar();
    }

    /**
     * Método privado que contiene la lógica de filtrado.
     * Lo sacamos aquí para poder usarlo desde el botón Y desde onResume.
     */
    private void filtrar() {
        // Comprobación de seguridad por si el spinner aún no cargó
        if (spinnerFiltrar == null || spinnerFiltrar.getSelectedItem() == null) return;

        String tipoSeleccionado = spinnerFiltrar.getSelectedItem().toString();

        if (tipoSeleccionado.equalsIgnoreCase("Todos")) {
            // Si es "Todos", ponemos el adaptador original (el que tiene la lista completa)
            lvPokemons.setAdapter(adapterDefaultPokemon);
        } else {
            // Si es un tipo concreto, creamos una lista temporal solo con esos
            ArrayList<Pokemon> listaFiltrada = new ArrayList<>();

            for (Pokemon p : listaPokemons) {
                if (p.getTipo().equalsIgnoreCase(tipoSeleccionado)) {
                    listaFiltrada.add(p);
                }
            }

            // Creamos un adaptador temporal solo para mostrar los filtrados
            PokemonAdapter adapterFiltrado = new PokemonAdapter(MainActivity.this, 0, listaFiltrada);
            lvPokemons.setAdapter(adapterFiltrado);
        }
    }
}