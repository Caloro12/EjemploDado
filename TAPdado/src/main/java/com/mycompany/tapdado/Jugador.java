
package com.mycompany.tapdado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.Semaphore;

class Jugador implements Runnable {
    private String nombre;
    private Semaphore semaforo;
    private Random random;
    private JTextArea textArea;

    public Jugador(String nombre, Semaphore semaforo, JTextArea textArea) {
        this.nombre = nombre;
        this.semaforo = semaforo;
        this.random = new Random();
        this.textArea = textArea;
    }

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) { 
            jugar();
            if (i < 1) { 
                pasarTurno();
            }
        }
    }

    private void jugar() {
        try {
            semaforo.acquire(); 
            textArea.append(nombre + " está jugando.\n");
            // Simula el lanzamiento de dos dados
            int dado1 = random.nextInt(6) + 1;
            int dado2 = random.nextInt(6) + 1;
            textArea.append(nombre + " tiró: Dado1=" + dado1 + ", Dado2=" + dado2 + "\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release(); 
        }
    }

    private void pasarTurno() {
        textArea.append(nombre + " ha terminado su turno. Pasando al siguiente jugador.\n");
        semaforo.release();
    }
}
