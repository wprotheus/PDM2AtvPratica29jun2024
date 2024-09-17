package com.wprotheus.pdm2atvpratica29jun2024.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Partida implements Serializable {
    public int[] numeros(int quantidade) {
        return new Random().ints(1, 101)
                .distinct()
                .limit(quantidade).toArray();
    }

    public int soma(int[] numeros) {
        return Arrays.stream(numeros).sum();
    }
}