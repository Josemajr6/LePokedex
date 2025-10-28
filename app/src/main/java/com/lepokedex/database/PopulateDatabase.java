package com.lepokedex.database;

import com.lepokedex.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PopulateDatabase {

    //Declaramos la lista de los Pokemons
    public static List<Pokemon> listaPokemons = new ArrayList<Pokemon>();

    public static void populate() {
        if (listaPokemons.isEmpty()) {

            listaPokemons.add(new Pokemon(1, "Charmander", 39, 52, 43, 65, "Fuego"));
            listaPokemons.add(new Pokemon(2, "Vulpix", 38, 41, 40, 65, "Fuego"));
            listaPokemons.add(new Pokemon(3, "Growlithe", 55, 70, 45, 60, "Fuego"));
            listaPokemons.add(new Pokemon(4, "Ponyta", 50, 85, 55, 90, "Fuego"));
            listaPokemons.add(new Pokemon(5, "Magmar", 65, 95, 57, 93, "Fuego"));
            listaPokemons.add(new Pokemon(6, "Slugma", 40, 40, 40, 20, "Fuego"));
            listaPokemons.add(new Pokemon(7, "Houndour", 45, 60, 30, 65, "Fuego"));
            listaPokemons.add(new Pokemon(8, "Numel", 60, 60, 40, 35, "Fuego"));
            listaPokemons.add(new Pokemon(9, "Torchic", 45, 60, 40, 45, "Fuego"));
            listaPokemons.add(new Pokemon(10, "Fletchinder", 62, 73, 55, 84, "Fuego"));

            listaPokemons.add(new Pokemon(11, "Squirtle", 44, 48, 65, 43, "Agua"));
            listaPokemons.add(new Pokemon(12, "Psyduck", 50, 52, 48, 55, "Agua"));
            listaPokemons.add(new Pokemon(13, "Poliwag", 40, 50, 40, 90, "Agua"));
            listaPokemons.add(new Pokemon(14, "Tentacool", 40, 40, 35, 70, "Agua"));
            listaPokemons.add(new Pokemon(15, "Seel", 65, 45, 55, 45, "Agua"));
            listaPokemons.add(new Pokemon(16, "Shellder", 30, 65, 100, 40, "Agua"));
            listaPokemons.add(new Pokemon(17, "Krabby", 30, 105, 90, 50, "Agua"));
            listaPokemons.add(new Pokemon(18, "Horsea", 30, 40, 70, 60, "Agua"));
            listaPokemons.add(new Pokemon(19, "Wooper", 55, 45, 45, 15, "Agua"));
            listaPokemons.add(new Pokemon(20, "Totodile", 50, 65, 64, 43, "Agua"));

            listaPokemons.add(new Pokemon(21, "Pikachu", 35, 55, 40, 90, "Electrico"));
            listaPokemons.add(new Pokemon(22, "Magnemite", 25, 35, 70, 45, "Electrico"));
            listaPokemons.add(new Pokemon(23, "Voltorb", 40, 30, 50, 100, "Electrico"));
            listaPokemons.add(new Pokemon(24, "Electabuzz", 65, 83, 57, 105, "Electrico"));
            listaPokemons.add(new Pokemon(25, "Jolteon", 65, 65, 60, 130, "Electrico"));
            listaPokemons.add(new Pokemon(26, "Mareep", 55, 40, 40, 35, "Electrico"));
            listaPokemons.add(new Pokemon(27, "Elekid", 45, 63, 37, 95, "Electrico"));
            listaPokemons.add(new Pokemon(28, "Plusle", 60, 50, 40, 95, "Electrico"));
            listaPokemons.add(new Pokemon(29, "Minun", 60, 40, 50, 95, "Electrico"));
            listaPokemons.add(new Pokemon(30, "Shinx", 45, 65, 34, 45, "Electrico"));

            listaPokemons.add(new Pokemon(31, "Bulbasaur", 45, 49, 49, 45, "Planta"));
            listaPokemons.add(new Pokemon(32, "Oddish", 45, 50, 55, 30, "Planta"));
            listaPokemons.add(new Pokemon(33, "Bellsprout", 50, 75, 35, 40, "Planta"));
            listaPokemons.add(new Pokemon(34, "Exeggcute", 60, 40, 80, 40, "Planta"));
            listaPokemons.add(new Pokemon(35, "Chikorita", 45, 49, 65, 45, "Planta"));
            listaPokemons.add(new Pokemon(36, "Treecko", 40, 45, 35, 70, "Planta"));
            listaPokemons.add(new Pokemon(37, "Seedot", 40, 40, 50, 30, "Planta"));
            listaPokemons.add(new Pokemon(38, "Shroomish", 60, 40, 60, 35, "Planta"));
            listaPokemons.add(new Pokemon(39, "Budew", 40, 30, 35, 55, "Planta"));
            listaPokemons.add(new Pokemon(40, "Turtwig", 55, 68, 64, 31, "Planta"));

            listaPokemons.add(new Pokemon(41, "Pidgey", 40, 45, 40, 56, "Normal"));
            listaPokemons.add(new Pokemon(42, "Rattata", 30, 56, 35, 72, "Normal"));
            listaPokemons.add(new Pokemon(43, "Meowth", 40, 45, 35, 90, "Normal"));
            listaPokemons.add(new Pokemon(44, "Eevee", 55, 55, 50, 55, "Normal"));
            listaPokemons.add(new Pokemon(45, "Snorlax", 160, 110, 65, 30, "Normal"));
            listaPokemons.add(new Pokemon(46, "Sentret", 35, 46, 34, 20, "Normal"));
            listaPokemons.add(new Pokemon(47, "Zigzagoon", 38, 30, 41, 60, "Normal"));
            listaPokemons.add(new Pokemon(48, "Bidoof", 59, 45, 40, 31, "Normal"));
            listaPokemons.add(new Pokemon(49, "Skitty", 50, 45, 45, 50, "Normal"));
            listaPokemons.add(new Pokemon(50, "Lillipup", 45, 60, 45, 55, "Normal"));
        }
    }

    public static Pokemon encontrarPokemon(int id) {
        for (Pokemon p : listaPokemons) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public static void anadirPokemon(String nombre, int hp, int ataque, int defensa, int velocidad, String tipo) {
        int nuevoId = 1;
        boolean idEncontrado = false;

        while (!idEncontrado) {
            idEncontrado = true;
            for (Pokemon p : listaPokemons) {
                if (p.getId() == nuevoId) {
                    nuevoId++;
                    idEncontrado = false;
                    break;
                }
            }
        }

        Pokemon p = new Pokemon(nuevoId, nombre, hp, ataque, defensa, velocidad, tipo);
        listaPokemons.add(p);
    }
}

