/*
 * Classe que defineix una parella de ball. Una parella de ball es defineix per el
 * número d'inscripció, un ballarí, una ballarina i la puntuació obtinguda en la competició.
 */
package model;

/**
 *
 * @author root
 */
public class ParellaBall {

    private int numInscripcio;
    private Ballari ballari;
    private Ballari ballarina;
    private int puntuacio;

    /*
     TODO CONSTRUCTOR
     Paràmetres: Els corresponents als atributs menys la puntuació i número d'inscripció
     Accions:
     - Assignarem 0 al número d'inscripció, ja que quan es crea una parella
     encara no està inscrita a cap modalitat.
     - En el cas de l'atribut puntuacio, s'ha d'inicialitzar a 0, ja que quan es
     crea una parella de ball encara ha de competir i per tant no té puntuació
     */
    public ParellaBall(Ballari ballari, Ballari ballarina) {
        this.numInscripcio = 0;
        this.ballari = ballari;
        this.ballarina = ballarina;
        this.puntuacio = 0;
    }

    /*
    TODO Mètodes accessors    
     */
    public int getNumInscripcio() {
        return numInscripcio;
    }

    public void setNumInscripcio(int numInscripcio) {
        this.numInscripcio = numInscripcio;
    }

    public Ballari getBallari() {
        return ballari;
    }

    public void setBallari(Ballari ballari) {
        this.ballari = ballari;
    }

    public Ballari getBallarina() {
        return ballarina;
    }

    public void setBallarina(Ballari ballarina) {
        this.ballarina = ballarina;
    }

    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }

    public void showParellaBall() {
        System.out.println("\nLes dades de la parella de ball amb número d'inscripció " + numInscripcio + " són:");

        if (ballari != null) {
            ballari.showComponent();
        } else {
            System.out.println("\nEncara no hi ha ballarí assignat");
        }

        if (ballarina != null) {
            ballarina.showComponent();
        } else {
            System.out.println("\nEncara no hi ha ballarina assignada");
        }

        System.out.println("\nPuntuació: " + puntuacio);
    }
}
