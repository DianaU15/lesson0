package ru.stq.pft.sandbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Collections {

    public static void main(String[] args) {
        String[] langs = {"Java", "Java","Java","Java"};

        List<String> languages = Arrays.asList("Java", "java");

        for (String l : languages) {
            System.out.println(l);
        }
    }
}
