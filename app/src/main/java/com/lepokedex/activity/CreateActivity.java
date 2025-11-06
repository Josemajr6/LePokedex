package com.lepokedex.activity;


import static com.lepokedex.database.PopulateDatabase.anadirPokemon;
import static com.lepokedex.database.PopulateDatabase.encontrarPokemon;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lepokedex.R;
import com.lepokedex.model.Pokemon;

public class CreateActivity extends AppCompatActivity {

    // Creamos las instancias de EditText, el Spinner, los botones de guardar y volver y el título
    EditText etNombre, etHP, etAtaque, etDefensa, etVelocidad;
    Spinner spnTipo;
    Button btnGuardar, btnVolver;
    TextView tvTitulo;

    // Definimos en un Array los tipos de Pokémon y creamos la instancia del pokémon a editar
    String[] tiposPokemon = { "Fuego", "Agua", "Electrico", "Planta", "Normal" };
    Pokemon pokemonAEditar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Enlazamos los elementos de activity_create
        etNombre = findViewById(R.id.etNombre);
        etHP = findViewById(R.id.etHP);
        etAtaque = findViewById(R.id.etAtaque);
        etDefensa = findViewById(R.id.etDefensa);
        etVelocidad = findViewById(R.id.etVelocidad);
        spnTipo = findViewById(R.id.spnTipo);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnVolver = findViewById(R.id.btnVolver);
        tvTitulo = findViewById(R.id.tvNuevoPokemon);

        // Creamos el adapter con los tipos de pokémon y lo establecemos al spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposPokemon);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipo.setAdapter(adapter);

        // Asignamos el pokémon a editar del intent de DetailActivity (si venimos de ahí)
        pokemonAEditar = (Pokemon) getIntent().getSerializableExtra("pokemon");

        if (pokemonAEditar != null) {
            tvTitulo.setText(R.string.editarPokemon);
            tvTitulo.setBackgroundResource(R.color.fondo_editar);

            // Cargamos la función de abajo
            cargarDatosDelPokemon(pokemonAEditar);
        } else {
            tvTitulo.setText(R.string.nuevoPokemon);
            tvTitulo.setBackgroundResource(R.color.fondo_nuevo);
        }

        // En btnGuardar llamamos a la función de guardarCambios()
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });

        // Si pulsamos el botón Volver se cierra la pantalla y volvemos al MainActivity
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // En la función cargarDatosDelPokemon establecemos en los campos los atributos
    private void cargarDatosDelPokemon(Pokemon p) {
        etNombre.setText(p.getNombre());
        etHP.setText(String.valueOf(p.getHp()));
        etAtaque.setText(String.valueOf(p.getAtaque()));
        etDefensa.setText(String.valueOf(p.getDefensa()));
        etVelocidad.setText(String.valueOf(p.getVelocidad()));

        // Buscamos el tipo de Pokémon que es para asignarlo al spinner
        for (int i = 0; i < tiposPokemon.length; i++) {
            if (tiposPokemon[i].equalsIgnoreCase(p.getTipo())) {
                spnTipo.setSelection(i);
                break;
            }
        }
    }

    // Método guardar cambios
    private void guardarCambios() {
        // Cogemos todos los atributos de los EditText
        String strNombre = etNombre.getText().toString().trim();
        String strTipo = spnTipo.getSelectedItem().toString();
        String strHP = etHP.getText().toString().trim();
        String strAtaque = etAtaque.getText().toString().trim();
        String strDefensa = etDefensa.getText().toString().trim();
        String strVelocidad = etVelocidad.getText().toString().trim();

        // Si hay alguno vacío lo indicamos y volvemos
        if (strNombre.isEmpty() || strTipo.isEmpty() || strHP.isEmpty() || strAtaque.isEmpty() || strDefensa.isEmpty() || strVelocidad.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }


        try {
            // Convertimos los stats a int
            int hp = Integer.parseInt(strHP);
            int ataque = Integer.parseInt(strAtaque);
            int defensa = Integer.parseInt(strDefensa);
            int velocidad = Integer.parseInt(strVelocidad);

            // Si guardamos desde Nuevo Pokemon, se añade el pokemon a la lista con la función de añadirPokemon
            if (pokemonAEditar == null) {
                anadirPokemon(strNombre, hp, ataque, defensa, velocidad, strTipo);
                // También hacemos un toast indicando que se ha creado el Pokémon
                Toast.makeText(CreateActivity.this, "Se ha creado el Pokémon", Toast.LENGTH_SHORT).show();
            } else {

                // Creamos una instancia del Pokémon original
                Pokemon pokemonOriginal = encontrarPokemon(pokemonAEditar.getId());

                // Si no es null, le establecemos los atributos que hemos modificado
                if (pokemonOriginal != null) {
                    pokemonOriginal.setNombre(strNombre);
                    pokemonOriginal.setHp(hp);
                    pokemonOriginal.setAtaque(ataque);
                    pokemonOriginal.setDefensa(defensa);
                    pokemonOriginal.setVelocidad(velocidad);
                    pokemonOriginal.setTipo(strTipo);

                    // Si se ha modificado correctamente, lo indicamos con un Toast
                    Toast.makeText(CreateActivity.this, "Pokémon actualizado", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        }

        // Controlamos con try_catch si se cuela algún carácter que no sea un número
        catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Los stats deben ser sólo números", Toast.LENGTH_SHORT).show();
        }
    }
}