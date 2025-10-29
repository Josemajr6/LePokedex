package com.lepokedex.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.lepokedex.R;
import com.lepokedex.model.Pokemon;

import java.util.List;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {
    List<Pokemon> listaPokemons;

    // Creación del constructor
    public PokemonAdapter(@NonNull Context context, int resource, @NonNull List<Pokemon> pokemons){
        super(context, resource, pokemons);
        this.listaPokemons = pokemons;

    }

    @Override
    public View getView(int posicion, @Nullable View convertView, ViewGroup parent){
        //La posición será el numero de filas que vamos a generar
        Pokemon p = listaPokemons.get(posicion);

        //Comprobación para descartar una nueva fila
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            /* Campos del Inflate
            1º Context: xml usado de plantilla
            2º Parent: padre del elemento ListView
            3º False: para crear el padre antes que el hijo
             */
            convertView = layoutInflater.inflate(R.layout.list_pokemon, parent, false);
        }

        // Establecer información de los pokemons
        TextView tvNombre = convertView.findViewById(R.id.tvNombre);
        TextView valorHP = convertView.findViewById(R.id.valorHp);
        TextView valorAtaque = convertView.findViewById(R.id.valorAtaque);
        TextView valorDefensa = convertView.findViewById(R.id.valorDefensa);
        TextView valorVelocidad = convertView.findViewById(R.id.valorVelocidad);

        tvNombre.setText(p.getNombre());
        valorHP.setText(String.valueOf(p.getHp()));
        valorAtaque.setText(String.valueOf(p.getAtaque()));
        valorDefensa.setText(String.valueOf(p.getDefensa()));
        valorVelocidad.setText(String.valueOf(p.getVelocidad()));

        int color = ContextCompat.getColor(getContext(), R.color.white);

        switch (p.getTipo().toLowerCase()){
            case "fuego" :
                color =  ContextCompat.getColor(getContext(), R.color.fuego);
                break;
            case "agua" :
                color =  ContextCompat.getColor(getContext(), R.color.agua);
                break;
            case "electrico" :
                color =  ContextCompat.getColor(getContext(), R.color.electrico);
                break;
            case "planta" :
                color =  ContextCompat.getColor(getContext(), R.color.planta);
                break;
            case "normal" :
                color =  ContextCompat.getColor(getContext(), R.color.normal);
                break;

        }

        convertView.setBackgroundColor(color);

        return convertView;
    }
}
