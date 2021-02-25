/*
 * Interfície que implementarà qualsevol component de la competició.
 */
package principal;

import java.util.Scanner;

/**
 *
 * @author root
 */
public interface Component {  
    
    public final static Scanner DADES = new Scanner(System.in);
    
    public void updateComponent(); 
    public void showComponent();     
    
}
