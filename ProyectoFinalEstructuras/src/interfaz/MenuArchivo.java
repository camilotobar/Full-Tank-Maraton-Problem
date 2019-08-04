package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuArchivo extends JMenu implements ActionListener{
	
	private JMenuItem meitCargar;
	private JMenuItem meitGuardar;
	private JMenuItem meitSalir;
	
	public final static String CARGAR  = "CARGAR";
	public final static String GENERAR = "GENERAR";
	public final static String SALIR   = "SALIR";
	
	private Interfaz principal;
	
	public MenuArchivo(Interfaz ventana) {
		super("Archivo");

		principal = ventana;
		
		meitCargar  = new JMenuItem("Cargar caso");
		meitGuardar = new JMenuItem("Generar caso");
		meitSalir   = new JMenuItem("Salir");
		
		meitCargar.addActionListener(this);
		meitGuardar.addActionListener(this);
		meitSalir.addActionListener(this);
		
		meitCargar.setActionCommand(CARGAR);
		meitGuardar.setActionCommand(GENERAR);
		meitSalir.setActionCommand(SALIR);
		
		add(meitCargar);
		add(meitGuardar);
		addSeparator();
		add(meitSalir);
	}
	
	@Override
	public void actionPerformed(ActionEvent evento) {
		String comando = evento.getActionCommand();
		if(comando.equals(CARGAR)){
			principal.cargarDeArchivo();
		}else if(comando.equals(GENERAR)){
			principal.guardarNuevoCaso();
		}else if(comando.equals(SALIR)){
			principal.salir();
		}
	}

}
