package ru.stq.pft.sandbox;

public class prog {
    public static void main(String [] args) {
        hello("Diana");

        Square s = new Square(5.0);
        Rectangle r = new Rectangle(5.0, 6.0);

        System.out.println("Площадь квадрата: " + s.area());
        System.out.println("Площадь прямоугольника: " + r.area());

    }

    public static void hello(String somebody) {
        System.out.println("Hello, " + somebody + "!");
    }

    public static double square(double a, double b) {
        return a*b;
    }
}
