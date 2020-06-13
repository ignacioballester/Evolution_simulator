package com.company;

import java.util.Random;

public class Map {
    final int[] available_units_range;
    final double[] fertility_rate_range;
    final double[] visibility_range;
    final Tile[][] tiles;
    final int MAP_SIZE;

    public Map(int[] available_units_range, double[] fertility_rate_range, double[] visibility_range, int MAP_SIZE) {
        this.available_units_range = available_units_range;
        this.fertility_rate_range = fertility_rate_range;
        this.visibility_range = visibility_range;
        this.MAP_SIZE = MAP_SIZE;
        this.tiles = new Tile[MAP_SIZE][MAP_SIZE];
    }

    // Populate the map with the tiles. The "climate" of the map can be adjusted depending on the given ranges.
    public void populate() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                tiles[i][j] = new Tile(available_units_range, fertility_rate_range, visibility_range);
            }
        }
    }

    public void print(){
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                System.out.print(tiles[i][j].fertility_rate + " ");
            }
            System.out.println();
        }
    }
}

class Tile {
    final int max_food_units = 5;
    int available_units;
    double fertility_rate;
    double visibility; // ]0,1] implicitly hiding capability of the tile and affects speed limit
    double speed_loss; // in percentage

    // Generate Tile with random characteristcs between the ranges given as parameters.
    public Tile(int available_units[], double[] fertility_rate, double visibility[]) throws IllegalArgumentException {
        if (visibility[0] > 1 || visibility[1] <= 0) {
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        this.available_units = available_units[0] + random.nextInt(available_units[1] - available_units[0] + 1);

        this.fertility_rate = fertility_rate[0] + (fertility_rate[1] - fertility_rate[0]) * random.nextDouble();
        this.fertility_rate = Math.round(this.fertility_rate * 100) / 100.0;

        this.visibility = visibility[0] + (visibility[1] - visibility[0]) * random.nextDouble();
        this.visibility = Math.round(this.visibility * 100) / 100.0;
        this.speed_loss = TradeOffs.poly_x2(0.4, this.visibility);
    }
}