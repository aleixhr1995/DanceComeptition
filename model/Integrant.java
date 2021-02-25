/*
 * Classe abstracta que defineix un integrant de la competició, és a dir, jutges, 
 * ballarins i ballarines. Un integrant es defineix pel seu nom i NIF.
 */
package model;

import principal.Component;

/**
 *
 * @author root
 */
public abstract class Integrant implements Component{
    private String nif;
    private String nom;


    //Constructor
    public Integrant(String nif, String nom) {
        this.nif = nif;
        this.nom = nom;
    }

    //Mètodes accessors
    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void updateComponent() {
        System.out.println("\nNIF de l'integrant: " + nif);
        System.out.println("\nEntra el nou nif:");
        nif = DADES.next();
        DADES.nextLine(); //Neteja buffer
        System.out.println("\nNom de l'integrant: " + nom);
        System.out.println("\nEntra el nou nom:");
        nom = DADES.nextLine();
    }

    public void showComponent() {
        System.out.println("\nLes dades de l'integrant amb nif " + nif + " són:");
        System.out.println("\nNom: " + nom);
    }
}
