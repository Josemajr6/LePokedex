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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<Pokemon> adapterDefaultPokemon;
    private ListView lvPokemons;
    private Spinner spinnerFiltrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Llamamos a populate() para añadir los Pokémons a la lista
        populate();

        // De momento importamos la listView para que aparezcan los Pokémon y el botón de añadir Pokémon
        lvPokemons = findViewById(R.id.lvPokemons);
        spinnerFiltrar = findViewById(R.id.spinnerFiltrar);
        Button botonAnadirPokemon = findViewById(R.id.botonAnadirPokemon);
        Button botonFiltrar = findViewById(R.id.botonFiltrar);

        // Creamos el adapter para esta pantalla con la lista de Pokémons y la establecemos a lvPokemons
        adapterDefaultPokemon = new PokemonAdapter(MainActivity.this, 0 , listaPokemons);
        lvPokemons.setAdapter(adapterDefaultPokemon);

        // Configuramos el spinner
        String[] tiposPokemon = { "Todos", "Fuego", "Agua", "Electrico", "Planta", "Normal" };
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposPokemon);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltrar.setAdapter(adapterSpinner);

        // Listener del botón de filtrar
        botonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Recogemos el valor seleccionado en el Spinner
                String tipoSeleccionado = spinnerFiltrar.getSelectedItem().toString();

                // Comprobamos la seleccion del usuario
                if (tipoSeleccionado.equalsIgnoreCase("Todos")) {
                    lvPokemons.setAdapter(adapterDefaultPokemon);
                } else {
                    ArrayList<Pokemon> listaFiltrada = new ArrayList<>();

                    for (Pokemon p : listaPokemons) {
                        if (p.getTipo().equalsIgnoreCase(tipoSeleccionado)) {
                            listaFiltrada.add(p);
                        }
                    }

                    // Creación de un Adapter con los pokemons filtrados para la lista
                    ArrayAdapter<Pokemon> adapterFiltrado = new PokemonAdapter(MainActivity.this, 0, listaFiltrada);

                    // Le asignamos el adapter nuevo a la lista de Pokemons
                    lvPokemons.setAdapter(adapterFiltrado);
                }
            }
        });

        // En el setOnClickListener del boton de añadir pokemon creamos un intent que vaya la vista de CreateActivity
        botonAnadirPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCrearPokemon = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(iCrearPokemon);
            }
        });

        // Listener de la lista (lo que pasa cuando hacemos clic en un pokémon)
        lvPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pokemon p = (Pokemon) parent.getItemAtPosition(position);

                Intent iDetalles = new Intent(MainActivity.this, DetailsActivity.class);
                iDetalles.putExtra("pokemon", p);
                startActivity(iDetalles);
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

        // Volvemos a aplicar el filtro para recargar
        filtrar();
    }

    private void filtrar() {
        // Si no hubiera ninguno cargado hace un return para evitar errores
        if (spinnerFiltrar == null || spinnerFiltrar.getSelectedItem() == null) return;

        String tipoSeleccionado = spinnerFiltrar.getSelectedItem().toString();

        if (tipoSeleccionado.equalsIgnoreCase("Todos")) {
            // Si es está Todos marcado, ponemos el adaptador original (el que tiene la lista completa)
            lvPokemons.setAdapter(adapterDefaultPokemon);
        } else {
            // Si es un tipo concreto, creamos una lista temporal solo con esos
            ArrayList<Pokemon> listaFiltrada = new ArrayList<>();

            for (Pokemon p : listaPokemons) {
                if (p.getTipo().equalsIgnoreCase(tipoSeleccionado)) {
                    listaFiltrada.add(p);
                }
            }

            // Creamos un adapter temporal solo para mostrar los filtrados
            PokemonAdapter adapterFiltrado = new PokemonAdapter(MainActivity.this, 0, listaFiltrada);
            lvPokemons.setAdapter(adapterFiltrado);
        }
    }
}