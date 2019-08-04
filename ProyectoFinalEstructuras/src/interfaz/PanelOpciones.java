package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelOpciones extends JPanel implements ActionListener{

	public static final String ELEGIR = "Elegir caso de prueba";
	public static final String SIGUIENTE = ">";
	public static final String ANTERIOR = "<";
	public static final String RESOLVER = "Calcular ruta mas barata";
	public static final String INFORME = "Informe";
	public static final String CERRAR_GRAFO = "Cerrar Caso";
	

	private JButton elegirCaso;
	private JButton siguiente;
	private JButton anterior;
	private JButton resolver;
	private JButton informe;
	private JButton cerrar;
	
	private Interfaz ventana;
	
	public PanelOpciones(Interfaz v) {
		setLayout(new BorderLayout());
		ventana = v;
		elegirCaso = new JButton(ELEGIR);
		siguiente = new JButton(SIGUIENTE);
		anterior = new JButton(ANTERIOR);
		informe = new JButton(INFORME);
		resolver = new JButton(RESOLVER);
		cerrar = new JButton(CERRAR_GRAFO);
		
		elegirCaso.addActionListener(this);
		elegirCaso.setActionCommand(ELEGIR);
		siguiente.addActionListener(this);
		siguiente.setActionCommand(SIGUIENTE);
		anterior.addActionListener(this);
		anterior.setActionCommand(ANTERIOR);
		informe.addActionListener(this);
		informe.setActionCommand(INFORME);
		resolver.addActionListener(this);
		resolver.setActionCommand(RESOLVER);
		cerrar.addActionListener(this);
		cerrar.setActionCommand(CERRAR_GRAFO);
		
		add(anterior, BorderLayout.WEST);
		add(siguiente, BorderLayout.EAST);
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(2, 2));
		aux.add(elegirCaso);
		aux.add(resolver);
		aux.add(informe);
		aux.add(cerrar);
		
		
		add(aux, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comand = e.getActionCommand();
		if (comand.equals(ELEGIR)){
			ventana.seleccionarCaso();
		} else if (comand.equals(INFORME)){
			
		} else if (comand.equals(SIGUIENTE)){
			ventana.actualizarQuerryActual(ventana.querryActual+1);
		} else if (comand.equals(ANTERIOR)){
			ventana.actualizarQuerryActual(ventana.querryActual-1);
		} else if (comand.equals(RESOLVER)){
			ventana.resolver();
		} else if (comand.equals(CERRAR_GRAFO)){
			ventana.cerrarGrafo();
		}
		
	}
	
	public void deshabilitarBotones(){
		elegirCaso.setEnabled(false);
		siguiente.setEnabled(false);
		anterior.setEnabled(false);
		informe.setEnabled(false);
		resolver.setEnabled(false);
		cerrar.setEnabled(false);
	}
	
	public void habilitarBotones(){
		elegirCaso.setEnabled(true);
		siguiente.setEnabled(true);
		anterior.setEnabled(true);
		informe.setEnabled(true);
		resolver.setEnabled(true);
		cerrar.setEnabled(true);
	}
	
}
