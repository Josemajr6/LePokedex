package com.lepokedex.activity;

import static com.lepokedex.database.PopulateDatabase.listaPokemons;
import static com.lepokedex.database.PopulateDatabase.populate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lepokedex.R;
import com.lepokedex.adapter.PokemonAdapter;
import com.lepokedex.model.Pokemon;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Declaramos las variables AQUÍ ARRIBA (Variables de Clase/Atributos)
    // Esto es necesario para que el método 'filtrar' y 'onResume' puedan acceder a ellas.
    ArrayAdapter<Pokemon> adapterDefaultPokemon;
    ListView lvPokemons;
    Spinner spinnerFiltrar;
    Button botonFiltrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populate();

        // Enlazamos las variables con los IDs del XML
        lvPokemons = findViewById(R.id.lvPokemons);
        botonFiltrar = findViewById(R.id.botonFiltrar);
        spinnerFiltrar = findViewById(R.id.spinnerFiltrar);

        Button botonAnadirPokemon = findViewById(R.id.botonAnadirPokemon);

        // Inicializamos el adaptador principal con toda la lista
        adapterDefaultPokemon = new PokemonAdapter(MainActivity.this, 0 , listaPokemons);
        lvPokemons.setAdapter(adapterDefaultPokemon);

        // Configuramos el Spinner
        String[] tiposPokemon = { "Todos", "Fuego", "Agua", "Electrico", "Planta", "Normal" };
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tiposPokemon) {

            // Creamos el método 'getDropDownView' para cambiar el color de las filas del spinner
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // Obtenemos la vista por defecto (TextView)
                View view = super.getDropDownView(position, convertView, parent);

                String tipo = getItem(position);

                // Asignamos un color por defecto (ej. para "Todos")
                int color = ContextCompat.getColor(getContext(), R.color.white);

                if (tipo != null) {
                    // Elegimos el color basado en el texto
                    switch (tipo.toLowerCase()) {
                        case "fuego":
                            color = ContextCompat.getColor(getContext(), R.color.fuego);
                            break;
                        case "agua":
                            color = ContextCompat.getColor(getContext(), R.color.agua);
                            break;
                        case "electrico":
                            color = ContextCompat.getColor(getContext(), R.color.electrico);
                            break;
                        case "planta":
                            color = ContextCompat.getColor(getContext(), R.color.planta);
                            break;
                        case "normal":
                            color = ContextCompat.getColor(getContext(), R.color.normal);
                            break;
                    }
                }

                // Aplicamos el color de fondo
                view.setBackgroundColor(color);

                return view;
            }
        };

        // Configuramos el spinner
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltrar.setAdapter(adapterSpinner);

        // Listener del Botón Filtrar
        botonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipoSeleccionado = spinnerFiltrar.getSelectedItem().toString();

                // Si es 'Todos', ponemos el adaptador original (el que tiene la lista completa)
                if (tipoSeleccionado.equalsIgnoreCase("Todos")) {
                    lvPokemons.setAdapter(adapterDefaultPokemon);
                } else { // Si es un tipo concreto, creamos una lista temporal solo con esos
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
        });

        // Listener del Botón Añadir
        botonAnadirPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iCrearPokemon = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(iCrearPokemon);
            }
        });

        // Listener de la Lista (Click en un pokemon)
        lvPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Usamos parent.getItemAtPosition para coger el Pokémon correcto
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

        // 1. Avisamos al adaptados principal de que puede haber datos nuevos
        if (adapterDefaultPokemon != null) {
            adapterDefaultPokemon.notifyDataSetChanged();
        }

        // 2. Volvemos a aplicar el filtro automáticamente
        if (botonFiltrar != null) {
            botonFiltrar.performClick();
        }
    }
}