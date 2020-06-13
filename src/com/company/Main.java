package com.company;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int[] available_units_range = new int[]{0, 5};
        double[] fertility_rate_range = new double[]{0, 1};
        double[] visibility_range = new double[]{0, 1};

        Map map = new Map(available_units_range, fertility_rate_range, visibility_range, 50);
        map.populate();
        map.print();
    }
}

class Specie {
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

    public Specie(int id, Charac.Diet diet, Charac.Reproduction reproduction, double speed_average, double intelligence_average,
                  int days_gestation_average, double size_average, double aggressivity_average) {
        this.id = id;
        this.diet = diet;
        this.reproduction = reproduction;
        this.intelligence_average = intelligence_average;
        this.days_gestation_average = days_gestation_average;
        this.size_average = size_average;
        this.aggressivity_average = aggressivity_average;

        if (this.diet.val == Charac.Diet.OMNIVORE.val) {
            energy_gain_per_food_unit = 5;
        } else {
            energy_gain_per_food_unit = 10;
        }
        this.speed_average = speed_average * TradeOffs.size_speed_tradeoff(size_average);
        this.life_loss_average = TradeOffs.antiproportional(size_average);
        this.experience_gain_average = intelligence_average;
        this.energy_loss_average = Math.pow(size_average, 2) * intelligence_average * speed_average;
    }
}

class Individual {
    Specie specie;

    double life_bar;
    double experience_bar;
    double energy_bar;

    public Individual(Specie specie){
        this.specie = specie;
        life_bar= 100;
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
