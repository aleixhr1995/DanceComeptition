package persistencia;

import model.Competicio;
import principal.GestorCompeticioException;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Predicate;
import java.util.List;

/**
 *
 * @author root
 */
public class GestorDB4O implements ProveedorPersistencia {

    private ObjectContainer db;
    private Competicio competicio;

    public Competicio getCompeticio() {
        return competicio;
    }

    public void setCompeticio(Competicio competicio) {
        this.competicio = competicio;
    }
    

    /*
     *TODO
     * 
     *Paràmetres: cap
     *
     *Acció:
     *  - Heu de crear / obrir la base de dades "EAC111920S2.db4o"
     *  - Aquesta base de dades ha de permetre que Competicio s'actualitzi en cascada.
     *
     *Retorn: cap
     *
     */
    public void estableixConnexio() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Competicio.class).cascadeOnUpdate(true);
        db = Db4oEmbedded.openFile(config, "EAC111920S2.db4o");

    }

    public void tancaConnexio() {
        db.close();
    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer i la competicó a desar
     *
     *Acció:
     *  - Heu d'establir la connexio i al final tancar-la.
     *  - Heu de desar l'objecte Competicio passat per paràmetre sobre la base de dades 
     *    (heu d'inserir si no existia ja a la base de dades, o actualitzar en altre cas)
     *  - S'ha de fer la consulta de l'existència amb Predicate
     *
     *Retorn: cap
     *
     */
    @Override
    public void desarCompeticio(String nomFitxer, Competicio pCompeticio){
        
        estableixConnexio();
        final int edicio = Integer.parseInt(nomFitxer);

        List<Competicio> competicions = db.query(new Predicate<Competicio>() {
            public boolean match(Competicio comp) {
                return comp.getEdicio()==edicio;
            }
        });

        if (competicions.size() != 1) { //No existeix
            db.store(pCompeticio);
            db.commit();
        } else { //Existeix
            competicio = competicions.iterator().next();
            competicio.setEdicio(pCompeticio.getEdicio());
            competicio.setAny(pCompeticio.getAny());
            competicio.setPoblacio(pCompeticio.getPoblacio());
            db.store(competicio);
            db.commit();
        }

        tancaConnexio();

    }

    /*
     *TODO
     * 
     *Paràmetres: el nom del fitxer on està guardada la competició
     *
     *Acció:
     *  - Heu d'establir la connexio i al final tancar-la.
     *  - Heu de carregar la competició des de la base de dades assignant-la a l'atribut competicio.
     *    Si no existeix, llanceu l'excepció GestorCompeticioException amb codi "GestorDB4O.noExisteix"
     *  - S'ha de fer la consulta amb Predicate
     *
     *Retorn: cap
     *
     */
    @Override
    public void carregarCompeticio(String nomFitxer) throws GestorCompeticioException {
        estableixConnexio();
        final int edicio = Integer.parseInt(nomFitxer);

        List<Competicio> competicions = db.query(
            new Predicate<Competicio>() {
                public boolean match(Competicio comp) {
                    return comp.getEdicio()==edicio;
                }
            }
        );

        if (competicions.size() != 1) { //No existeix
            throw new GestorCompeticioException("GestorDB4O.noExisteix");
        } else {
            competicio = competicions.iterator().next();
        }

        tancaConnexio();
      
    }
}
