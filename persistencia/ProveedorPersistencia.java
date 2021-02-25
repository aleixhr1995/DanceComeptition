package persistencia;

import model.Competicio;
import principal.GestorCompeticioException;

/**
 *
 * @author root
 */
public interface ProveedorPersistencia {
    public void desarCompeticio(String nomFitxer, Competicio competicio)throws GestorCompeticioException;
    public void carregarCompeticio(String nomFitxer)throws GestorCompeticioException; 
}
