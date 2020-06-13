package com.company;

public class TradeOffs {
    public static double poly_x2(double min, double x) {
        return min + (1 - min) * Math.pow(x, 2);
    }

    public static double size_speed_tradeoff(double size) {
        return 1 / (0.5 * Math.pow(size - 2, 4) + 1);
    }

    public static double antiproportional(double x) {
        return 1 / x;
    }
}