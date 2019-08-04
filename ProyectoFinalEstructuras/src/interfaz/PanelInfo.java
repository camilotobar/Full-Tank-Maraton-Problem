package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelInfo extends JPanel{

	private JLabel labInicio;
	private JLabel labFinal;
	private JLabel labCapacidadTanque;
	private JLabel labCosto;
	
	private JTextField txtInicio;
	private JTextField txtFinal;
	private JTextField txtCapacidadTanque;
	private JTextField txtCosto;
	


	public PanelInfo() {
		setLayout(new BorderLayout());
//		setPreferredSize(new Dimension(600, 80));
		labInicio = new JLabel("Punto inicial", SwingConstants.CENTER);
		labFinal = new JLabel("Punto final", SwingConstants.CENTER);
		labCapacidadTanque = new JLabel("Capacidad tanque", SwingConstants.CENTER);
		labCosto = new JLabel("Costo minimo", SwingConstants.CENTER);
		
		txtInicio = new JTextField();
		txtFinal = new JTextField();
		txtCapacidadTanque = new JTextField();
		txtCosto = new JTextField();
		txtCosto.setPreferredSize(new Dimension(200, 15));
		
		txtInicio.setEditable(false);
		txtFinal.setEditable(false);
		txtCapacidadTanque.setEditable(false);
		txtCosto.setEditable(false);
		
		txtInicio.setBackground(Color.WHITE);
		txtFinal.setBackground(Color.WHITE);
		txtCapacidadTanque.setBackground(Color.WHITE);
		txtCosto.setBackground(Color.WHITE);
		
		txtInicio.setHorizontalAlignment(SwingConstants.RIGHT);
		txtFinal.setHorizontalAlignment(SwingConstants.RIGHT);
		txtCapacidadTanque.setHorizontalAlignment(SwingConstants.RIGHT);
		txtCosto.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel aux = new JPanel();
		aux.setLayout(new GridLayout(2, 4));
		aux.add(labInicio);
		aux.add(txtInicio);
		aux.add(labFinal);
		aux.add(txtFinal);
		aux.add(labCapacidadTanque);
		aux.add(txtCapacidadTanque);
		aux.add(labCosto);
		aux.add(txtCosto);
		add(aux, BorderLayout.CENTER);
		
	}
	
	public void Refrescar(int capacity, int inicial, int destino){
		txtInicio.setText(""+inicial);
		txtFinal.setText(""+destino);
		txtCapacidadTanque.setText(""+capacity);
	}
	
	public void limpiarRespuesta(){
		txtCosto.setText("");
	}
	
	public void limpiar(){
		txtInicio.setText("");
		txtFinal.setText("");
		txtCapacidadTanque.setText("");
	}
	
	public void resolver(long costo){
		if (costo == Integer.MAX_VALUE){
			txtCosto.setText("impossible");
		} else{
			txtCosto.setText("" + costo);						
		}
	}
	
}
