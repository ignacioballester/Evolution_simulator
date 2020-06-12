package com.company;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] available_units_range = new int[] {0, 5};
        double[] fertility_rate_range = new double[] {0, 1};
        double[] visibility_range = new double[] {0, 1};

        Map map = new Map(available_units_range, fertility_rate_range, visibility_range, 50);

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
        if(visibility[0]>1 || visibility[1] <=0){
            throw new IllegalArgumentException();
        }
        Random random = new Random();
        this.available_units = available_units[0] + random.nextInt(available_units[1]-available_units[0]+1);

        this.fertility_rate = fertility_rate[0] + (fertility_rate[1] - fertility_rate[0]) * random.nextDouble();
        this.fertility_rate = Math.round(this.fertility_rate*100)/100.0;

        this.visibility = visibility[0] + (visibility[1] - visibility[0]) * random.nextDouble();
        this.visibility = Math.round(this.visibility *100)/100.0;
        this.speed_loss = TradeOffs.poly_x2(0.4, this.visibility);
    }
}

class Map {
    final int[] available_units_range;
    final double[] fertility_rate_range;
    final double[] visibility_range;
    final Tile[][] tiles;
    final int MAP_SIZE;
    public Map (int[] available_units_range, double[] fertility_rate_range, double[] visibility_range, int MAP_SIZE){
        this.available_units_range = available_units_range;
        this.fertility_rate_range = fertility_rate_range;
        this.visibility_range = visibility_range;
        this.MAP_SIZE = MAP_SIZE;
        this.tiles = new Tile[MAP_SIZE][MAP_SIZE];
    }

    // Populate the map with the tiles. The "climate" of the map can be adjusted depending on the given ranges.
    public void populate(){
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                tiles[i][j] = new Tile(available_units_range, fertility_rate_range, visibility_range);
                System.out.print(tiles[i][j].fertility_rate + " ");
            }
            System.out.println();
        }
    }
}

class Specie{
    int id;
    Charac.Diet diet;
    Charac.Reproduction reproduction;

    double speed_average;
    double intelligence_average;
    int days_gestation_average;
    double size_average; //bigger than 0
    double aggressivity_average;
    double life_loss_average;
    double experience_gain_average;
    double energy_loss_average;
    double energy_gain_per_food_unit;

    public Specie(int id, Charac.Diet diet, Charac.Reproduction reproduction, double speed_average, double intelligence_average, int days_gestation_average, double size_average, double aggressivity_average) {
        this.id = id;
        this.diet = diet;
        this.reproduction = reproduction;
        this.intelligence_average = intelligence_average;
        this.days_gestation_average = days_gestation_average;
        this.size_average = size_average;
        this.aggressivity_average = aggressivity_average;

        if (this.diet.val == Charac.Diet.OMNIVORE.val){
            energy_gain_per_food_unit = 5;
        } else{
            energy_gain_per_food_unit = 10;
        }
        this.speed_average = speed_average* TradeOffs.size_speed_tradeoff(size_average);
        this.life_loss_average = TradeOffs.antiproportional(size_average);
        this.experience_gain_average = intelligence_average;
        this.energy_loss_average = Math.pow(size_average, 2)*intelligence_average*speed_average;
    }
}

/*lass Indivudual extends Specie{

}*/

class TradeOffs{
    public static double poly_x2(double min, double x){
        return min + (1-min)*Math.pow(x,2);
    }

    public static double size_speed_tradeoff(double size){
        return  1/(0.5*Math.pow(size-2,4)+1);
    }

    public static double antiproportional(double x){
        return  1/x;
    }

}

class Charac {
    enum Diet {
        OMNIVORE(0),
        HERVIVORE(1),
        CARNIVORE(2);

        int val;
        Diet(int i) {
            val = i;
        }
    }

    enum Reproduction {
        ASEXUAL(0),
        SEXUAL(1);

        int val;
        Reproduction(int i) {
            val = i;
        }
    }
}
