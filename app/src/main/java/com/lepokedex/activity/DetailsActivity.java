package com.lepokedex.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lepokedex.R;
import com.lepokedex.database.PopulateDatabase;
import com.lepokedex.model.Pokemon;

public class DetailsActivity extends AppCompatActivity {
    // Declaro los elementos del XMl (activity_details.xml)
    TextView tvNombrePokemon, tvTipo, tvHp, tvAtaque, tvDefensa, tvVelocidad;
    ImageView imgTipo;
    Button btnEditar, btnEliminar, btnVolver;
    Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Recogo el pokemon seleccionado por el usuario
        pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon");

        // Si el pokemon nos da null, entonces cerramos la actividad
        if (pokemon == null) {
            Toast.makeText(this, "Error: No se pudo cargar el Pokemón", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Enlazo las variables con los Ids del XML
        tvNombrePokemon = findViewById(R.id.tvNombrePokemon);
        tvTipo = findViewById(R.id.tvTipo);
        tvHp = findViewById(R.id.tvHp);
        tvAtaque = findViewById(R.id.tvAtaque);
        tvDefensa = findViewById(R.id.tvDefensa);
        tvVelocidad = findViewById(R.id.tvVelocidad);
        imgTipo = findViewById(R.id.imgTipo);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnVolver = findViewById(R.id.btnVolver);

        // Relleno los campos con los datos del pokemon
        tvNombrePokemon.setText(pokemon.getNombre());
        tvTipo.setText(pokemon.getTipo());
        tvHp.setText(pokemon.getHp());
        tvAtaque.setText(pokemon.getAtaque());
        tvDefensa.setText(pokemon.getDefensa());
        tvVelocidad.setText(pokemon.getVelocidad());

        // Dependiendo del tipo ponemos una img o otra
        switch (pokemon.getTipo().toLowerCase()) {
            case "fuego":
                imgTipo.setImageResource(R.drawable.fuego);
                break;
            case "agua":
                imgTipo.setImageResource(R.drawable.agua);
                break;
            case "electrico":
                imgTipo.setImageResource(R.drawable.electricidad);
                break;
            case "planta":
                imgTipo.setImageResource(R.drawable.planta);
                break;
            case "normal":
                imgTipo.setImageResource(R.drawable.normal);
                break;
            default:
                imgTipo.setImageResource(R.drawable.pokeball);
        }

        // Configuro los Listeners de los botones
        // Botón Volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Con esto lo que hago es cerrar la actividad y vuelvo a la anterior
            }
        });

        // Botón Eliminar
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopulateDatabase.listaPokemons.remove(pokemon);

                Toast.makeText(DetailsActivity.this, "Pokemón eliminado", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        // Botón Editar
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEditar = new Intent(DetailsActivity.this, CreateActivity.class);
                intentEditar.putExtra("pokemon", pokemon);
                startActivity(intentEditar);

                // Cierro la pantalla de detalles
                finish();
            }
        });
    }
}
