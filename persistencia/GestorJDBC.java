package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Competicio;
import model.Jutge;
import principal.Component;
import principal.GestorCompeticioException;

/**
 *
 * @author root
 */
public class GestorJDBC implements ProveedorPersistencia {

    private Competicio competicio;

    private Connection conn; //Connexió a la base de dades

    public Competicio getCompeticio() {
        return competicio;
    }

    public void setCompeticio(Competicio competicio) {
        this.competicio = competicio;
    }

    /*
     PreparedStatement necessaris
     */
 /*
     * TODO
     *
     * Obtenir una competició
     * 
     * Sentència select de la taula competicions
     * Columnes: totes
     * Files: totes les que l'edició de la competició coincideixi amb l'edició passada per paràmetre
     *
     */
    private static String edicioCompeticioSQL = "SELECT * FROM competicions WHERE edicio = ?";

    private PreparedStatement edicioCompeticioSt;

    /*
     * TODO
     *
     * Inserir una competició
     * 
     * Sentència d'inserció de la taula competicions
     * Els valors d'inserció són els que es donaran per paràmetre
     *
     */
    private static String insereixCompeticioSQL = "INSERT INTO competicions(edicio, año, poblacio) VALUES (?,?,?)";

    private PreparedStatement insereixCompeticioSt;

    /*
     * TODO
     *
     * Actualitzar una competició
     * 
     * Sentència d'actualització de la taula competicions
     * Files a actualitzar: les que es corresponguin amb l'edició passada per paràmetre
     * Columnes a actualitzar: any i població amb els valors passat per paràmetre
     *
     */
    private static String actualitzaCompeticioSQL = "UPDATE COMPETICIONS SET año = ?, poblacio = ? WHERE edicio = ?";

    private PreparedStatement actualitzaCompeticioSt;

    /*
     * TODO
     *
     * Eliminar jutges (donada l'edició d'una competició)
     * 
     * Sentència d'eliminació de la taula jutges
     * Files a eliminar: les que es corresponguin amb l'edició de la competició passada per paràmetre
     *
     */
    private static String eliminaJutgeSQL = "DELETE FROM jutges WHERE edicioCompeticio = ?";

    private PreparedStatement eliminaJutgeSt;

    /*
     * TODO
     *
     * Inserir un Jutge
     * 
     * Sentència d'inserció de la taula jutges
     * Els valors d'inserció són els que es donaran per paràmetre
     *
     */
    private static String insereixJutgeSQL = "INSERT INTO jutges (nif, nom, edicioCompeticio) VALUES (?,?,?)";

    private PreparedStatement insereixJutgeSt;

    /*
     * TODO
     *
     * Seleccionar els jutges d'una competició
     * 
     * Sentència select de la taula jutges
     * Columnes: totes
     * Files: totes les que l'edició de la competició coincideixi amb la passada per paràmetre
     *
     */
    private static String selJutgesSQL = "SELECT * FROM jutges WHERE edicioCompeticio = ?";

    private PreparedStatement selJutgesSt;

    /*
     *TODO
     * 
     *Paràmetres: cap
     *
     *Acció:
     *  - Heu d'establir la connexio JDBC amb la base de dades EAC111920S2
     *  - Heu de crear els objectes PrepareStatement declarats com a atributs d'aquesta classe
     *    amb els respectius SQL declarats com a atributs just sobre cadascun d'ells.
     *  - Heu de fer el catch de les possibles excepcions (en aquest mètode no llançarem GestorCompeticioException,
     *    simplement, mostreu el missatge per consola de l'excepció capturada)
     *
     *Retorn: cap
     *
     */
    public void estableixConnexio() throws SQLException {
        try{
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/EAC111920S2", "root", "root123");
            
            edicioCompeticioSt = conn.prepareStatement(edicioCompeticioSQL);
            insereixCompeticioSt = conn.prepareStatement(insereixCompeticioSQL);
            actualitzaCompeticioSt = conn.prepareStatement(actualitzaCompeticioSQL);
            eliminaJutgeSt = conn.prepareStatement(eliminaJutgeSQL);
            insereixJutgeSt = conn.prepareStatement(insereixJutgeSQL);
            selJutgesSt = conn.prepareStatement(selJutgesSQL);    
        }catch(SQLException e){
            System.out.println(e.getMessage());
            conn = null;
        }
        
    }

    public void tancaConnexio() throws SQLException {
        try {
            conn.close();
        } finally {
            conn = null;
        }
    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer i la competició a desar
     *
     *Acció:
     *  - Heu de desar la competició sobre la base de dades:
     *  - La competició s'ha de desar a la taula competicions (nomFitxer és l'edició de la competició)
     *  - Cada jutge de la competició, s'ha de desar com registre de la taula jutges.
     *  - Heu de tenir en compte que si la competició ja existeix, heu de fer el següent:
     *     - Actualitzar el registre competició ja existent
     *     - Eliminar tots els jutges d'aquesta competició de la taula jutges i després inserir els nous com si hagués estat
     *       una competició nova.
     *  - Si al fer qualsevol operació es produeix una excepció, llavors heu de llançar l'excepció GestorCompeticioException amb codi "GestorJDBC.desar"
     *
     *Retorn: cap
     *
     */
    @Override
    public void desarCompeticio(String nomFitxer, Competicio competicio) throws GestorCompeticioException {
        try{
            if(conn == null){
                estableixConnexio();    
            }
            edicioCompeticioSt.setInt(1, competicio.getEdicio());
            ResultSet edicioCompeticio = edicioCompeticioSt.executeQuery();
                if(edicioCompeticio.next()){
                  actualitzaCompeticioSt.setInt(1,competicio.getAny());
                  actualitzaCompeticioSt.setString(2,competicio.getPoblacio());
                  actualitzaCompeticioSt.setInt(3,competicio.getEdicio());
                  actualitzaCompeticioSt.executeUpdate();
                  eliminaJutgeSt.setInt(1,competicio.getEdicio());
                  eliminaJutgeSt.executeUpdate();  
                }else{
                  insereixCompeticioSt.setInt(1,competicio.getEdicio());
                  insereixCompeticioSt.setInt(2,competicio.getAny());
                  insereixCompeticioSt.setString(3,competicio.getPoblacio());
                  insereixCompeticioSt.executeUpdate();    
                }
                for(Component cmp :competicio.getComponents()){
                    if(cmp instanceof Jutge){
                         insereixJutgeSt.setString(1,((Jutge)cmp).getNif());
                         insereixJutgeSt.setString(2,((Jutge)cmp).getNom());
                         insereixJutgeSt.setInt(3,competicio.getEdicio());
                         insereixJutgeSt.executeUpdate();
                    }
                }
                tancaConnexio();
            
            
            
        }catch(SQLException e){
            throw new GestorCompeticioException("GestorJDBC.desar");   
        }
    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer de la competició
     *
     *Acció:
     *  - Heu de carregar la competició des de la base de dades (nomFitxer és l'edició de la competició)
     *  - Per fer això, heu de cercar el registre competició de la taula amb edicio = nomFitxer
     *  - A més, heu d'afegir els jutges a la competició a partir de la taula jutges
     *  - Si al fer qualsevol operació es dona una excepció, llavors heu de llançar l'excepció GestorCompeticioException 
     *    amb codi "GestorJDBC.carregar"
     *  - Si el nomFitxer donat no existeix a la taula competicions (és a dir, la edicio = nomFitxer no existeix), llavors
     *    heu de llançar l'excepció GestorCompeticioException amb codi "GestorJDBC.noexist"
     *
     *Retorn: cap
     *
     */
    @Override
    public void carregarCompeticio(String nomFitxer) throws GestorCompeticioException {
        try{
            if(conn == null){
            estableixConnexio();    
            }
            edicioCompeticioSt.setInt(1,Integer.parseInt(nomFitxer));
            ResultSet edicioCompeticio = edicioCompeticioSt.executeQuery();
                if(edicioCompeticio.next()){
                 
                    competicio = new Competicio (edicioCompeticio.getInt("edicio"),edicioCompeticio.getInt("anyo"),edicioCompeticio.getString("poblacio"));
                    
                    selJutgesSt.setInt(1,competicio.getEdicio());
                    ResultSet jutgesCompeticio = selJutgesSt.executeQuery();
                    
                    while(jutgesCompeticio.next()){
                       
                        Jutge nouJutge = new Jutge(jutgesCompeticio.getString("nif"),jutgesCompeticio.getString("nom"));
                        competicio.addJutge(nouJutge);
                    }
                }else{
                    throw new GestorCompeticioException("GestorJDBC.noexist");
                }
                
            tancaConnexio();
            
        }catch(SQLException e){
            throw new GestorCompeticioException("GestorJDBC.carregar");
        }
    }

}
