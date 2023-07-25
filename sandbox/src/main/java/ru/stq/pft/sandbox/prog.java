package ru.stq.pft.sandbox;

public class prog {
    public static void main(String [] args) {
        hello("Diana");

        double a = 4;
        double b = 6;
        System.out.println("Площадь: " + square(a, b));
    }

    public static void hello(String somebody) {
        System.out.println("Hello, " + somebody + "!");
    }

    public static double square(double a, double b) {
        return a*b;
    }
}
