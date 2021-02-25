/*
 * Classe que defineix un jutge. Un/a jutge es defineix pel seu NIF i nom.
 */
package model;

/**
 *
 * @author root
 */
public class Jutge extends Integrant {

    /*
     TODO CONSTRUCTOR
     Paràmetres: valors per tots els atributs de la classe
     Accions:
     - Assignar als atributs els valors passats com a paràmetres.
     */
    public Jutge(String nif, String nom) {
        super(nif,nom);
    }
   

    /*
     TODO
     Paràmetres: cap
     Accions:
     - Demanar a l'usuari les dades per consola per crear un nou o nova jutge.
     Les dades a demanar són les que passem per paràmetre en el constructor.
     - També heu de tenir en compte que el nom pot ser una frase, per exemple, 
     Francesc Xavier.
     Retorn: El nou o nova jutge creat/da.
     */
    public static Jutge addJutge() {

        String nif;
        String nom;

        System.out.println("NIF del o de la jutge:");
        nif = DADES.next();
        DADES.nextLine(); //Neteja buffer
        System.out.println("Nom del o de la jutge:");
        nom = DADES.nextLine();

        return new Jutge(nif, nom);
    }

}