package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class VentanaLista extends JDialog implements ActionListener, ListSelectionListener{

	//Constantes-------------------------------------------------------------------------------------------------
	public static final String SELECCIONAR = "Seleccionar";

	
	public final static String EDITAR = "Editar";

	//Atributos-------------------------------------------------------------------------------------------------
	private JButton butSeleccionar;
	private JList querrys;
	private Interfaz inicio;
	
	//ListaEntrenadores
	public VentanaLista(Interfaz v, int[][] querrys) {
		
		inicio = v;
		setTitle("Entrenadores");
		
		butSeleccionar = new JButton(SELECCIONAR);
		butSeleccionar.addActionListener(this);
		butSeleccionar.setActionCommand(SELECCIONAR);
		
		inicializarListaEntrenadores( querrys );
		JScrollPane scroll = new JScrollPane( );
		scroll.setPreferredSize(new Dimension(300, 300));
	    scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
	    scroll.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS );
	    scroll.setBorder( new CompoundBorder( new EmptyBorder( 3, 3, 3, 3 ), new LineBorder( Color.BLACK, 1 ) ) );
	    scroll.getViewport( ).add( this.querrys );
		
		this.querrys.addListSelectionListener(this);
		
	    
	    JPanel aux = new JPanel();
	    aux.add(butSeleccionar);
	    add(scroll, BorderLayout.CENTER);
	    add(aux, BorderLayout.SOUTH);
	    
	    pack();
	    centrar();
	}
	@SuppressWarnings("unchecked")
	public void inicializarListaEntrenadores(int[][] querrys){
		this.querrys = new JList();
		String[] data = new String[querrys.length];
		for (int i = 0; i < querrys.length; i++) {
			data[i] = "Tank: "+querrys[i][0]+" ";
			data[i] += "Inicio: "+querrys[i][1]+" ";
			data[i] += "Final: "+querrys[i][2]; 
		}
		this.querrys.setListData(data);
		this.querrys.setSelectedIndex(0);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String comando = e.getActionCommand();
		if(comando.equals(SELECCIONAR)){
			try {
				inicio.actualizarQuerryActual(querrys.getSelectedIndex());
				this.dispose();
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(inicio, "No hay entrenadores para seleccionar");
			}			
		}
	}

    public void centrar( ){
        Dimension screen = Toolkit.getDefaultToolkit( ).getScreenSize( );
        int xEsquina = ( screen.width - getWidth( ) ) / 2;
        int yEsquina = ( screen.height - getHeight( ) ) / 2;
        setLocation( xEsquina, yEsquina );
    }

	@Override
	public void valueChanged(ListSelectionEvent e) {	
	}
	
}
