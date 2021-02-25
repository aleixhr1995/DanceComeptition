/*
 * Classe que defineix una modalitat.Una modalitat es defineix per un codi i un nom. A més, contindrà 
 * un vector de jutges i un altre de parelles de ball
 */

package model;

import model.Jutge;
import model.ParellaBall;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import principal.Component;
import principal.GestorCompeticioException;

/**
 *
 * @author root
 */
public class Modalitat implements Component {

    private String codi;
    private int numInscripcioParella;
    private static int proximNumInscripcioParella = 1; //El número d'inscripció de la següent parella inscrita
    private String nom;
    private Map<String,ArrayList> components = new HashMap();


    public Modalitat(String codi, String nom) {
        this.codi = codi;
        this.nom = nom;
        components.put("parellesBall", new ArrayList<ParellaBall>());
        components.put("jutges", new ArrayList<Jutge>());
    }

    /*
     TODO Mètodes accessors    
     */
    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public int getNumInscripcioParella() {
        return numInscripcioParella;
    }

    public void setNumInscripcioParella(int numInscripcioParella) {
        this.numInscripcioParella = numInscripcioParella;
    }

    public static int getProximNumInscripcioParella() {
        return proximNumInscripcioParella;
    }

    public static void setProximNumInscripcioParella(int proximNumInscripcioParella) {
        Modalitat.proximNumInscripcioParella = proximNumInscripcioParella;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Map<String, ArrayList> getComponents() {
        return components;
    }

    public void setComponents(Map<String, ArrayList> components) {
        this.components = components;
    }


    /*
     TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari les dades per consola per crear una nova modalitat. Les 
     dades a demanar són les que passem per paràmetre en el constructor.
     - També heu de tenir en compte que el nom pot ser una frase, per exemple, 
     Vals Vienès.
     Retorn: La nova modalitat creada.
     */
    public static Modalitat addModalitat() {

        String codi;
        String nom;

        System.out.println("Codi de la modalitat:");
        codi = DADES.next();
        DADES.nextLine();
        System.out.println("Nom de la modalitat:");
        nom = DADES.nextLine();

        return new Modalitat(codi, nom);
    }

    /*
    TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari que introdueixi les noves dades de l'objecte actual
     i modificar els atributs corresponents d'aquest objecte. En aquest cas
     només es pot modificar el codi i nom de la modalitat.
     Retorn: cap
     */
    public void updateComponent() {

        System.out.println("\nCodi de la modalitat: " + codi);
        System.out.println("\nEntra el nou codi:");
        codi = DADES.next();
        DADES.nextLine();
        System.out.println("\nNom de la modalitat: " + nom);
        System.out.println("\nEntra el nou nom:");
        nom = DADES.nextLine();
    }


    public void addParella(ParellaBall parella) {
        numInscripcioParella = proximNumInscripcioParella;
        parella.setNumInscripcio(numInscripcioParella);
        proximNumInscripcioParella++;
        components.get("parellesBall").add(parella);
    }


    public void addJutge(Jutge jutge) throws GestorCompeticioException {

        if (components.get("jutges").size() < 3) {
            components.get("jutges").add(jutge);
            System.out.println("\n" + components.get("jutges").size() + " jutges assignats");
        } else if (components.get("jutges").size() > 7) {
            throw new GestorCompeticioException("2");
        } else {
            components.get("jutges").add(jutge);
        }

    }

    public void showComponent() {
        System.out.println("\nLes dades de la modalitat amb codi " + codi + " són:");
        System.out.println("\nNom: " + nom);

        if (!components.get("parellesBall").isEmpty()) {
            for (int i = 0; i < components.get("parellesBall").size(); i++) {
                ((ParellaBall)components.get("parellesBall").get(i)).showParellaBall();
            }
        } else {
            System.out.println("\nNo hi ha cap parella inscrita.");
        }

        if (!components.get("jutges").isEmpty()) {
            for (int i = 0; i < components.get("jutges").size(); i++) {
                ((Jutge)components.get("jutges").get(i)).showComponent();
            }
        } else {
            System.out.println("\nNo hi ha cap jutge assignat.");
        }

    }

}
