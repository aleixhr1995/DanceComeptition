package persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import model.Competicio;
import principal.GestorCompeticioException;

/**
 *
 * @author root
 */
public class GestorSerial implements ProveedorPersistencia {

    private Competicio competicio;

    public Competicio getCompeticio() {
        return competicio;
    }

    public void setCompeticio(Competicio competicio) {
        this.competicio = competicio;
    }

    @Override
    public void desarCompeticio(String nomFitxer, Competicio competicio) throws GestorCompeticioException {
        /*
         *TODO
         *
         *Paràmetres: nom del fitxer i desti
         *
         *Acció:
         * - Ha de desar l'objecte competició serialitzat sobre un fitxer del sistema 
         *   operatiu amb nom nomFitxer i extensió ".ser".
         * - Heu de controlar excepcions d'entrada/sortida i en cas de produïrse alguna, 
         *   llavors llançar GestorCompeticioException amb codi GestorSerial.desar 
         *
         *Nota: podeu comprovar que la classe Competicio implementa Serializable  
         *
         *Retorn: cap
         */

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(nomFitxer + ".ser")))) {
            oos.writeObject(competicio);
        } catch (IOException ex) {
            throw new GestorCompeticioException("GestorSerial.desar");
        }

    }

    @Override
    public void carregarCompeticio(String nomFitxer) throws GestorCompeticioException {
        /*
         *TODO
         *
         *Paràmetres: nom del fitxer
         *
         *Acció:
         * - Ha de carregar el fitxer del sistema operatiu amb nom nomFitxer i extensió 
         *   ".ser" sobre un nou objecte Competicio que es retornarà com a resultat.               
         * - Heu de controlar excepcions d'entrada/sortida i en cas de produïrse alguna, 
         *   llavors llançar GestorCompeticioException amb codi GestorSerial.carrega 
         *
         *Retorn: cap
         */

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(nomFitxer + ".ser")))) {
            competicio = (Competicio) ois.readObject();
        } catch (IOException ex) {
            throw new GestorCompeticioException("GestorSerial.carregar");
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error de classe: " + ex.getMessage());
        }

    }
}
