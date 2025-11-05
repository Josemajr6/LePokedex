package com.lepokedex.activity;

import static com.lepokedex.database.PopulateDatabase.anadirPokemon;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lepokedex.R;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // Creamos las instancias de todos los elementos del XML
        EditText etNombre = findViewById(R.id.etNombre);
        EditText etHP = findViewById(R.id.etHP);
        EditText etAtaque = findViewById(R.id.etAtaque);
        EditText etDefensa = findViewById(R.id.etDefensa);
        EditText etVelocidad = findViewById(R.id.etVelocidad);
        Spinner spnTipo = findViewById(R.id.spnTipo);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnVolver = findViewById(R.id.btnVolver);

        // Creamos un array con los tipos de pokémon
        String[] tiposPokemon = { "Fuego", "Agua", "Electrico", "Planta", "Normal" };

        // Creamos el ArrayAdapter con el array de los tipos de Pokémon que tendrá el spinner de tipos
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tiposPokemon);

        // Definimos la plantilla para dibujar el spinner y que se vea bien y asignamos el ArrayAdapter al spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTipo.setAdapter(adapter);

        // En el setOnClickListener de btnGuardar empezamos cogiendo en String todos los atributos
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNombre = etNombre.getText().toString().trim();
                String strTipo = spnTipo.getSelectedItem().toString();
                String strHP = etHP.getText().toString().trim();
                String strAtaque = etAtaque.getText().toString().trim();
                String strDefensa = etDefensa.getText().toString().trim();
                String strVelocidad = etVelocidad.getText().toString().trim();

                // Si hay campos vacíos, creamos un toast que nos diga que tenemos que rellenar todos los campos
                if (strNombre.isEmpty() || strTipo.isEmpty() || strHP.isEmpty() || strAtaque.isEmpty() || strDefensa.isEmpty() || strVelocidad.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Convertimos en int los atributos de las stats, metiéndolo en un try_catch para controlar si se crea bien o hay carácteres que no son números en las stats
                try {
                    int hp = Integer.parseInt(strHP);
                    int ataque = Integer.parseInt(strAtaque);
                    int defensa = Integer.parseInt(strDefensa);
                    int velocidad = Integer.parseInt(strVelocidad);

                    anadirPokemon(strNombre, hp, ataque, defensa, velocidad, strTipo);

                    Toast.makeText(CreateActivity.this, "Se ha creado el Pokémon", Toast.LENGTH_SHORT).show();

                    finish(); // en lugar de crear un intent, para no crear tantas vistas usamos finish() para cerrar la vista
                }
                catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Los stats deben ser sólo números", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // En el setOnClickListerner también cerramos la vista
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}