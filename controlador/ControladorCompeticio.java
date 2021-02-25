package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import model.Competicio;
import persistencia.GestorPersistencia;
import persistencia.GestorSerial;
import persistencia.GestorXML;
import principal.GestorCompeticioException;
import vista.CompeticioForm;
import vista.CompeticioLlista;
import vista.MenuCompeticio;

/**
 *
 * @author root
 */
public class ControladorCompeticio implements ActionListener {
    
    private MenuCompeticio menuCompeticio;
    private CompeticioForm competicioForm = null;
    private CompeticioLlista competicioLlista = null;
    private int opcioSelec = 0;

    public ControladorCompeticio() {

        /*
        TODO
        
        S'inicialitza l'atribut menuCompeticio (això mostrarà el menú de competicions)
        Es crida a afegirListenersMenu
        
         */
        
        menuCompeticio = new MenuCompeticio();
        afegirListenersMenu();

    }

    private void afegirListenersMenu() {
        /*
        TODO
        
        A cada botó del menú competicions, s'afegeix aquest mateix objecte (ControladorCompeticio) com a listener
        
        */
        
        for (JButton unBoto : menuCompeticio.getMenuButtons()) {
            unBoto.addActionListener(this);
        }

    }

    private void afegirListenersForm() {
        /*
        TODO
        
        A cada botó del formulari de la competició, s'afegeix aquest mateix objecte (ControladorCompeticio) com a listener
        
         */
        
        competicioForm.getbDesar().addActionListener(this);
        competicioForm.getbSortir().addActionListener(this);

    }

    private void afegirListenersLlista() {
        /*
        TODO
        
        Al botó de sortir de la llista de competicions, s'afegeix aquest mateix objecte (ControladorCompeticio) com a listener
        */
        
        competicioLlista.getbSortir().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        TODO
        
        Nota:
            Com ControladorCompeticio és listener del menú de competicions, del formulari i de la llista, llavors en aquest mètode
            actionPerformed heu de controlar si l'usuari ha premut algun botó de qualsevol dels esmentats frames.
            Ull! En el cas del formulari i de la llista, com provenen del menú (els llança el menú de competicions), heu de verificar
            primer que els objectes competicioForm o competicioLlista no són nulls, per tal de saber si podeu comparar-los amb
            alguns dels botons d'aquests frames.
        
        Accions per al menú:
            S'ha de cridar a seleccionarOpcio segons l'opció premuda. Penseu que l'opció es correspon amb
            la posició que el botó ocupa a l'array de botons de menuCompeticio
            També, heu d'actualitzar la propietat opcioSelec (amb l'opció que ha premut l'usuari)
        
        Accions per al formulari de competició:
            
            ---- DESAR ----
            Si el botó premut per l'usuari és el botó de desar del formulari de la competició, llavors
        
                Si l'opció seleccionada (al menú de competicions) és 1 (alta), llavors
        
                    Es crea un nou objecte Competicio amb les dades del formulari
                    S'afegeix la competicio creada a la llista de ControladorPrincipal
                    Es desactiva el camp de l'edició del formulari perquè l'usuari no pugui modificar-lo.
                    Es posa aquesta competició com competicioActual (de ControladorPrincipal) i es canvia l'atribut
                    opcioSelec a 2
                        
                Si l'opció seleccionada (al menú de competicions) és 3 (modificació), llavors
                    
                    Es modifica l'objecte competició amb les dades del formulari (penseu que és la competicioActual de ControladorPrincipal)    
        
            ---- SORTIR ----
            Si el botó premut per l'usuari és el botó de sortir del formulari de competicions, llavors
                
                Heu de tornar al menú de competicions (i amagar el formulari)
        
        Accions per a la llista de competicions:
            
            ---- SORTIR ----
            Si el botó premut per l'usuari és el botó de sortir de la llista de competicions, llavors
                
                Heu de tornar al menú de competicions (i amagar la llista)
         
         */
        
         //Accions per al menú
        JButton[] botons = menuCompeticio.getMenuButtons();

        for (int i = 0; i < botons.length; i++) {
            if (e.getSource() == botons[i]) {
                menuCompeticio.getFrame().setVisible(false);
                opcioSelec = i;
                seleccionarOpcio(i);
            }
        }

        //Accions per al formulari de competicions
        if (competicioForm != null) {

            if (e.getSource() == competicioForm.getbDesar()) {

                if (opcioSelec == 1) {//Nova competició

                        Competicio competicio = new Competicio(Integer.parseInt(competicioForm.gettAny().getText()), competicioForm.gettPoblacio().getText());
                        ControladorPrincipal.getCompeticions()[ControladorPrincipal.getPosicioCompeticions()] = competicio;
                        ControladorPrincipal.setPosicioCompeticions();
                        competicioForm.gettEdicio().setText(String.valueOf(competicio.getEdicio()));
                        competicioForm.gettEdicio().setEnabled(false);
                        ControladorPrincipal.setCompeticioActual(competicio);
                        opcioSelec = 2;

                } else if (opcioSelec == 3) {//Modificar competició

                    ControladorPrincipal.getCompeticioActual().setAny(Integer.parseInt(competicioForm.gettAny().getText()));
                    ControladorPrincipal.getCompeticioActual().setPoblacio(competicioForm.gettPoblacio().getText());

                }

            } else if (e.getSource() == competicioForm.getbSortir()) { //Sortir

                competicioForm.getFrame().setVisible(false);
                menuCompeticio.getFrame().setVisible(true);

            }

        }

        if (competicioLlista != null) {

            if (e.getSource() == competicioLlista.getbSortir()) {

                competicioLlista.getFrame().setVisible(false);
                menuCompeticio.getFrame().setVisible(true);

            }

        }

    }

    private void seleccionarOpcio(Integer opcio) {

        switch (opcio) {

            case 0: //sortir
                ControladorPrincipal.getMenuPrincipal().getFrame().setVisible(true);
                break;

            case 1: // alta
                if (ControladorPrincipal.getPosicioCompeticions()< ControladorPrincipal.getMAXCOMPETICIONS()) {
                    competicioForm = new CompeticioForm();
                    competicioForm.gettEdicio().setEnabled(false);
                    afegirListenersForm();
                } else {
                    menuCompeticio.getFrame().setVisible(true);
                    JOptionPane.showMessageDialog(menuCompeticio.getFrame(), "Màxim nombre de competicions assolit.");
                }
                break;

            case 2: //seleccionar
                menuCompeticio.getFrame().setVisible(true);
                if (ControladorPrincipal.getCompeticions()[0] != null) {
                    seleccionarCompeticio();
                } else {
                    JOptionPane.showMessageDialog(menuCompeticio.getFrame(), "Abans s'ha de crear al menys una competició");
                }
                break;

            case 3: //modificar
                if (ControladorPrincipal.getCompeticions()[0] != null) {
                    seleccionarCompeticio();
                    competicioForm = new CompeticioForm(ControladorPrincipal.getCompeticioActual().getEdicio(), ControladorPrincipal.getCompeticioActual().getAny(), ControladorPrincipal.getCompeticioActual().getPoblacio());
                    competicioForm.gettEdicio().setEnabled(false);
                    afegirListenersForm();
                } else {
                    menuCompeticio.getFrame().setVisible(true);
                    JOptionPane.showMessageDialog(menuCompeticio.getFrame(), "Abans s'ha de crear al menys una competició");
                }
                break;

            case 4: // llistar
                if (ControladorPrincipal.getCompeticions()[0] != null) {
                    competicioLlista = new CompeticioLlista();
                    afegirListenersLlista();
                } else {
                    menuCompeticio.getFrame().setVisible(true);
                    JOptionPane.showMessageDialog(menuCompeticio.getFrame(), "Abans s'ha de crear al menys una competició");
                }
                break;

            case 5: //carregar
            /*
            TODO
                
            Es mostra un dialog (JOptionPane.showOptionDialog) amb botons, on cadascun d'ells és un mètode de càrrega 
            (propietat a Controlador Principal: ara XML i Serial)
            Un cop seleccionat el mètode, amb un altre dialog, es demana l'edició de la competició a carregar 
            (recordeu que el nom del fitxer és l'edició de la competició més l'extensió)
            Un cop l'usuari ha entrat el codi i ha premut OK,
                Es crea un objecte competició (novaCompeticio) mitjançant el mètode carregarCompetició del gestor de persistència. Penseu que la
                carrega pots ser d'un fitxer XML o bé d'un fitxer serial.
                Es comprova si l'edidició de la nova competició (novaCompeticio) ja existeix al vector de competicions (això donarà la posició on s'ha trobat a la llista). Penseu
                que en aquesta classe teniu un mètode per fer la comprovació.
                Si existeix,
                    es mostra un dialog notificant a l'usuari si vol substituir la competició del vector pel que es carregarà des de el fitxer (JOptionPane.showOptionDialog)
                    Si l'usuari cancela, no es fa res
                    Si l'usuari accepta, llavors es posa la nova competició al vector a la mateixa posició on s'havia trobat aquest codi
                Si no existeix,
                    S'afegeix la nova competició al vector de competicions a la darrera posició
                    Es mostra un missatge confirmant l'addició (JOptionPane.showMessageDialog)
            
            */
                
                menuCompeticio.getFrame().setVisible(true);
                
                int codi = JOptionPane.showOptionDialog(null, "Selecciona un mètode", "Carregar competició", 0, JOptionPane.QUESTION_MESSAGE, null, ControladorPrincipal.getMETODESPERSISTENCIA(), "XML");
                
                if (codi != JOptionPane.CLOSED_OPTION) {
                    
                    GestorPersistencia gestor = new GestorPersistencia();
                    
                    Competicio competicio;
                    
                    try {
                        
                        String edicio = JOptionPane.showInputDialog("Quina és l'edició de la competició que vols carregar?");
                        
                        gestor.carregarCompeticio(ControladorPrincipal.getMETODESPERSISTENCIA()[codi], edicio);
                        
                        if(ControladorPrincipal.getMETODESPERSISTENCIA()[codi].equals("XML")){
                            competicio=((GestorXML)gestor.getGestor()).getCompeticio();
                        }else{
                            competicio=((GestorSerial)gestor.getGestor()).getCompeticio();
                        }
                        
                        int pos = comprovarCompeticio(competicio.getEdicio());
                        
                        if (pos >= 0) {
                            
                            Object[] options = {"OK", "Cancel·lar"};                            
                            int i = JOptionPane.showOptionDialog(null, "Premeu OK per substituir-lo.", "Competició ja existent",
                                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                                    null, options, options[0]);
                            
                            if (i == 0) {
                                ControladorPrincipal.getCompeticions()[pos] = competicio;
                            }
                            
                        } else {
                            
                            ControladorPrincipal.getCompeticions()[ControladorPrincipal.getPosicioCompeticions()] = competicio;
                            ControladorPrincipal.setPosicioCompeticions();
                            JOptionPane.showMessageDialog(menuCompeticio.getFrame(), "Competició afegida correctament");
                        
                        }

                    } catch (GestorCompeticioException e) {
                        JOptionPane.showMessageDialog(menuCompeticio.getFrame(), e.getMessage());
                    }
                }
                
                break;

            case 6: //desar
                /*
                TODO
                
                Es comprova si s'ha seleccionat la competició, mostrant, si correspon, missatges d'error (JOptionPane.showMessageDialog)
                Si s'ha seleccionat la competició, 
                    Es mostra un dialog (JOptionPane.showOptionDialog) amb botons, on cadascun d'ells és un mètode de càrrega
                    (propietat a Controlador Principal: ara XML i Serial)
                    Un cop escollit el mètode, es desa la competició cridant a desarCompeticio del gestor de persistència
                 */
                
                menuCompeticio.getFrame().setVisible(true);
                
                if (ControladorPrincipal.getCompeticioActual() != null) {
                    
                    int messageTyped = JOptionPane.QUESTION_MESSAGE;
                    int code = JOptionPane.showOptionDialog(null, "Selecciona un mètode", "Desar competició", 0, messageTyped, null, ControladorPrincipal.getMETODESPERSISTENCIA(), "XML");
                    
                    if (code != JOptionPane.CLOSED_OPTION) {
                        
                        GestorPersistencia gestor = new GestorPersistencia();
                        
                        try {
                            gestor.desarCompeticio(ControladorPrincipal.getMETODESPERSISTENCIA()[code], String.valueOf(ControladorPrincipal.getCompeticioActual().getEdicio()), ControladorPrincipal.getCompeticioActual());
                        } catch (GestorCompeticioException e) {
                            JOptionPane.showMessageDialog(menuCompeticio.getFrame(), e.getMessage());                           
                        }
                        
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(menuCompeticio.getFrame(), "Abans s'ha de seleccionar una competició");
                }

                break;

        }

    }

    private void seleccionarCompeticio() {

        String[] edicioCompeticio = new String[ControladorPrincipal.getPosicioCompeticions()];

        int i = 0;

        for (Competicio competicio : ControladorPrincipal.getCompeticions()) {

            if (competicio != null) {
                edicioCompeticio[i] = String.valueOf(competicio.getEdicio());
            }

            i++;

        }

        int messageType = JOptionPane.QUESTION_MESSAGE;
        int codi = JOptionPane.showOptionDialog(null, "Selecciona una competició", "Selecció de competicions", 0, messageType, null, edicioCompeticio, "A");
        
        if (codi != JOptionPane.CLOSED_OPTION) {
            ControladorPrincipal.setCompeticioActual(ControladorPrincipal.getCompeticions()[codi]);
        }

    }

    private Integer comprovarCompeticio(int codi) {

        int pos = -1;

        for (int i = 0; i < ControladorPrincipal.getCompeticions().length; i++) {

            if (ControladorPrincipal.getCompeticions()[i] != null) {
                if (ControladorPrincipal.getCompeticions()[i].getEdicio() == codi) {
                    return pos=i;
                }
            }
        }

        return pos;
    }
    
}
