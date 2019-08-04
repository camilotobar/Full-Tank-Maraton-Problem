package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Mundo.*;

@SuppressWarnings("serial")
public class Interfaz extends JFrame{
	private PanelGrafo panelGrafo;
	private MenuArchivo menuArchivo;
	private Generador generador;
	private PanelInfo info;
	private PanelOpciones opciones;
	int querryActual;
	private VentanaLista lista;
	
	public Interfaz(){
		setTitle("Full Tank ?");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		panelGrafo = new PanelGrafo(this);
		menuArchivo = new MenuArchivo(this);
		generador = new Generador();
		info = new PanelInfo();
		opciones = new PanelOpciones(this);
		querryActual = -1;
		JMenuBar miMenuBar = new JMenuBar();
		miMenuBar.add(menuArchivo);
		setJMenuBar(miMenuBar);
//		add(panelGrafo, BorderLayout.CENTER);
		
		JPanel aux = new JPanel();
		aux.setLayout(new BorderLayout());
		aux.add(info, BorderLayout.CENTER);
		aux.add(opciones, BorderLayout.SOUTH);
		opciones.deshabilitarBotones();
		add(aux, BorderLayout.SOUTH);
		pack();
		this.setResizable(false);
		centrar();
	}
	
	public boolean cargarDeArchivo(){
		boolean cargoArchivo = false;
		JFileChooser fileCh = new JFileChooser("./Casos");
		int opcion = fileCh.showOpenDialog(this);
		
		switch(opcion){	
			case JFileChooser.APPROVE_OPTION:
				File f = fileCh.getSelectedFile();
				try{
					generador.Cargar(f);
					generador.pintarGrafo();
					panelGrafo.repaint();
					opciones.habilitarBotones();
					cargoArchivo = true;
				}catch(Exception e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(this, "Problemas leyendo el archivo\nEs probable que el formato no sea válido.");
				}
			break;
			case JFileChooser.CANCEL_OPTION:				
			break;
			case JFileChooser.ERROR_OPTION:
			break;
		}
		return cargoArchivo;
	}
	
	private void crearEIniciarHilos(){
//		hilos = new ArrayList<Thread>();
//		panelCancha.cambiarBalones(miCancha.darBalones());
		refrescarCancha();
//		for (int i = 0; i < miCancha.darBalones().size(); i++) {
//			Balon b = miCancha.darBalones().get(i);
//			HiloBalon h = new HiloBalon(b, this);
//			h.start();
//			hilos.add(h);
//		}
	}
	
	public void refrescarCancha() {
		panelGrafo.repaint();
	}
	
	public boolean guardarNuevoCaso(){
		boolean guardoArchivo = false;
		JFileChooser fileCh = new JFileChooser("./Casos");
		fileCh.setSelectedFile(new File("CasoDePrueba_"+(new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss").format(new Date()))+".txt"));
		int opcion = fileCh.showSaveDialog(this);
		
		switch(opcion){	
			case JFileChooser.APPROVE_OPTION:
				File f = fileCh.getSelectedFile();
				try{
					generador.generarCasos(f);
					guardoArchivo = true;
				}catch(Exception ioexc){
					JOptionPane.showMessageDialog(this, "Problemas guardando el archivo\nEs probable que no tenga permisos de escritura o\nel archivo puede estar bloqueado por otro programa.");
				}
			break;
			case JFileChooser.CANCEL_OPTION:				
			break;
			case JFileChooser.ERROR_OPTION:
			break;
		}
		return guardoArchivo;		
	}
	
	public void salir(){
		System.exit(0);
	}
	
	public void seleccionarCaso(){
		lista = new VentanaLista(this, generador.getQueries());
		lista.setVisible(true);
	}
	
	public void mostrarPuntajes(){
		String puntajes;
//		puntajes = miCancha.darPuntajes();
//		JOptionPane.showMessageDialog(this, puntajes);
	}
	
	public void mostrarAcercaDelJuego(){
		JOptionPane.showMessageDialog(this, "Desarrollado por:\nJuan Manuel Reyes\nDepartamento de TIC\nFacultad de Ingeniería\nUniversidad Icesi");
	}
	
	public int darAnchoActual(){
		return getContentPane().getWidth();
	}
	
	public int darAltoActual(){
		return getContentPane().getHeight();
	}
	
	public void dispose(){
		salir();
	}
	
	public void actualizarQuerryActual(int querry){
		if (querry >= generador.queriesNumber){
			querry = 0;
		} else if (querry<0){
			querry = generador.queriesNumber-1;
		}
		querryActual = querry;
		mostrarQuerry(generador.getQueries()[querry]);
	}
	
	public void resolver(){
		try {
			info.resolver(generador.getMinWays()[querryActual]);			
		} catch (Exception e) {
			JOptionPane.showInputDialog("No hay nongun caso seleccionado");
		}
	}
	
	public void mostrarQuerry(int[] querry){
		info.Refrescar(querry[0], querry[1], querry[2]);
		info.limpiarRespuesta();
	}
	
	public void cerrarGrafo(){
		generador.g.clear();
		opciones.deshabilitarBotones();
	}
	
	public static void main(String[] args){
		Interfaz ventana;
		ventana = new Interfaz();
		ventana.setVisible(true);
	}
	
    public void centrar( )
    {
        Dimension screen = Toolkit.getDefaultToolkit( ).getScreenSize( );
        int xEsquina = ( screen.width - getWidth( ) ) / 2;
//        int yEsquina = ( screen.height - getHeight( ) ) / 2;
        setLocation( xEsquina, 10 );
    }
    
}
