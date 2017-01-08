package GRAFICA;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import BEANS.Departamentos;
import BEANS.Empleados;
import GESTOR_BBDD.Gestor_BBDD;

import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.awt.Color;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Principal extends JFrame {

	private JPanel contentPane;
	private JTextField textID_Empleado;
	private JTextField textApellido;
	private JTextField textOficio;
	private JTextField textSalario;
	private JTextField textComision;
	private Gestor_BBDD g1;
	private JComboBox<Departamentos> comboDepartamentos;
	
	//MODELO PARA EL COMBO DE DEPARTAMENTOS
	private DefaultComboBoxModel<Departamentos> modeloDep;
	private DefaultComboBoxModel<Empleados> modeloEmp;
	private DefaultComboBoxModel<Empleados> modeloEmpMod;
	private JSpinner spinner_Year;
	private JSpinner spinner_Mes;
	private JSpinner spinner_Dia;
	private JComboBox<Empleados> comboDirector;
	private JComboBox<Empleados> comboEmpleadoMod;
	private Empleados empleadoSeleccionado;
	private JTextArea textAreaInfo;
	
	//ARRAYLIST CON LOS IDS DE LOS EMPLEADOS
	private ArrayList<Integer> idsEmpleados;
	private ArrayList<Empleados> listaEmpleados;
	private int idSeleccionado;
	private JButton btnSig;
	private JButton btnAnterior;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		
		//INICIAMOS EL GESTOR BBDD
		g1 = new Gestor_BBDD();
		
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 723, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new TitledBorder(null, "DATOS EMPLEADO", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(383, 76, 313, 346);
		contentPane.add(scrollPane);
		
		textAreaInfo = new JTextArea();
		textAreaInfo.setEditable(false);
		textAreaInfo.setBackground(new Color(0, 153, 51));
		scrollPane.setViewportView(textAreaInfo);
		
		JButton btnPrimero = new JButton("Primero");
		//LISTENER BOTON PRIMERO
		btnPrimero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idPrimero = idsEmpleados.get(0);
				System.out.println(idPrimero);

				for(Empleados emp: listaEmpleados){
					if(emp.getEmpNo().intValue() == idPrimero){
						System.out.println("ES IGUAL");
						mostrarInfoEmpleados(emp);
						break;
					}
						
				}
				btnSig.setEnabled(true);
				btnAnterior.setEnabled(false);
			}
		});
		
		
		btnPrimero.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnPrimero.setBounds(383, 31, 79, 29);
		contentPane.add(btnPrimero);
		
		btnSig = new JButton("Siguiente");
		//LISTENER PARA MOSTRAR LA INFO DEL SIGUIENTE ID
				btnSig.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						int idSig = idsEmpleados.get(idsEmpleados.indexOf(idSeleccionado)+1);
						System.out.println(idSig);
						
						for(Empleados emp: listaEmpleados){
							
							if(emp.getEmpNo().intValue() == idSig){
								System.out.println("ES IGUAL");
								mostrarInfoEmpleados(emp);
								
								if(idSig == idsEmpleados.get(listaEmpleados.size()-1))
									btnSig.setEnabled(false);
								break;
							}								
						}
						btnAnterior.setEnabled(true);
					}
				});
		
		btnSig.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnSig.setBounds(456, 31, 79, 29);
		contentPane.add(btnSig);
		
		btnAnterior = new JButton("Anterior");
		//LISTENER PARA MOSTRAR LA INFO DEL SIGUIENTE ID
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int idAnt = idsEmpleados.get(idsEmpleados.indexOf(idSeleccionado)-1);

				for(Empleados emp: listaEmpleados){
					
					if(emp.getEmpNo().intValue() == idAnt){
						System.out.println("ES IGUAL");
						mostrarInfoEmpleados(emp);
						
						if(idAnt == idsEmpleados.get(0))
							btnAnterior.setEnabled(false);
						break;
					}								
				}
				btnSig.setEnabled(true);
			}
		});
		
		btnAnterior.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		btnAnterior.setBounds(529, 31, 79, 29);
		contentPane.add(btnAnterior);
		
		JButton btnltimo = new JButton("Último");
		//LISTENER PARA MOSTRAR LA INFO DEL ÚLTIMO ID
		btnltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int idUltimo = idsEmpleados.get(idsEmpleados.size()-1);
				System.out.println(idUltimo);
				
				for(Empleados emp: listaEmpleados){
					if(emp.getEmpNo().intValue() == idUltimo){
						System.out.println("ES IGUAL");
						mostrarInfoEmpleados(emp);
						break;
					}
						
				}
				btnSig.setEnabled(false);
				btnAnterior.setEnabled(true);

			}
		});
		
		
		btnltimo.setBounds(602, 31, 79, 29);
		contentPane.add(btnltimo);
		
		
		SpinnerNumberModel modelD = new SpinnerNumberModel(1, 1, 31, 1);
		
		SpinnerNumberModel modelM = new SpinnerNumberModel(1, 1, 12, 1);
		
		SpinnerNumberModel modelA = new SpinnerNumberModel(2016, 2001, 2017, 1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 346, 429);
		contentPane.add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("INSERTAR", null, panel, null);
		panel.setBorder(new TitledBorder(null, "INSERTAR EMPLEADO", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID Empleado");
		lblNewLabel.setBounds(23, 43, 86, 16);
		panel.add(lblNewLabel);
		
		textID_Empleado = new JTextField();
		textID_Empleado.setBounds(121, 38, 182, 26);
		panel.add(textID_Empleado);
		textID_Empleado.setColumns(10);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(23, 76, 86, 16);
		panel.add(lblApellido);
		
		textApellido = new JTextField();
		textApellido.setColumns(10);
		textApellido.setBounds(121, 71, 182, 26);
		panel.add(textApellido);
		
		JLabel lblOficio = new JLabel("Oficio");
		lblOficio.setBounds(23, 109, 86, 16);
		panel.add(lblOficio);
		
		textOficio = new JTextField();
		textOficio.setColumns(10);
		textOficio.setBounds(121, 104, 182, 26);
		panel.add(textOficio);
		
		JLabel lblIdSuperior = new JLabel("Superior");
		lblIdSuperior.setBounds(23, 142, 86, 16);
		panel.add(lblIdSuperior);
		
		JLabel lblFechaDeAlta = new JLabel("Fecha de Alta");
		lblFechaDeAlta.setBounds(23, 175, 86, 16);
		panel.add(lblFechaDeAlta);
		
		JLabel lblSalario = new JLabel("Salario");
		lblSalario.setBounds(23, 208, 86, 16);
		panel.add(lblSalario);
		
		textSalario = new JTextField();
		textSalario.setColumns(10);
		textSalario.setBounds(121, 203, 182, 26);
		panel.add(textSalario);
		
		JLabel lblComisin = new JLabel("Comisión");
		lblComisin.setBounds(23, 241, 86, 16);
		panel.add(lblComisin);
		
		textComision = new JTextField();
		textComision.setColumns(10);
		textComision.setBounds(121, 236, 182, 26);
		panel.add(textComision);
		
		JLabel lblIdDepartamento = new JLabel("Departamento");
		lblIdDepartamento.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
		lblIdDepartamento.setBounds(23, 274, 86, 16);
		panel.add(lblIdDepartamento);
		
		JButton btnNewButton = new JButton("INSERTAR");
		
		//LISTENER PARA EL BOTON INSERTAR
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertarModificarEmpleado(0);
			}
		});
		btnNewButton.setBounds(70, 322, 182, 29);
		panel.add(btnNewButton);
		spinner_Dia = new JSpinner();
		spinner_Dia.setBounds(121, 170, 50, 26);
		spinner_Dia.setModel(modelD);
		panel.add(spinner_Dia);
		spinner_Mes = new JSpinner();
		spinner_Mes.setBounds(172, 170, 50, 26);
		spinner_Mes.setModel(modelM);
		panel.add(spinner_Mes);
		spinner_Year = new JSpinner();
		spinner_Year.setModel(modelA);
		spinner_Year.setBounds(223, 170, 80, 26);
		panel.add(spinner_Year);
		
		comboDepartamentos = new JComboBox<Departamentos>();
		comboDepartamentos.setBounds(121, 274, 182, 27);
		panel.add(comboDepartamentos);
		
		comboDirector = new JComboBox();
		comboDirector.setBounds(121, 138, 182, 27);
		panel.add(comboDirector);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("MODIFICAR", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Seleccione el empleado que desea modificar:");
		lblNewLabel_1.setBounds(21, 27, 298, 31);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblseUsarnLos = new JLabel("(Se usarán los datos del formulario de la pestañaI NSERTAR)");
		lblseUsarnLos.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		lblseUsarnLos.setBounds(21, 53, 298, 18);
		panel_1.add(lblseUsarnLos);
		
		comboEmpleadoMod = new JComboBox();
		
		//LISTENER PARA LA SELECCION DE EMPLEADOS- COMBO BOX
		comboEmpleadoMod.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
				if(e.getStateChange() == ItemEvent.SELECTED) {
                   empleadoSeleccionado = (Empleados)comboEmpleadoMod.getSelectedItem();
                   mostrarInfoEmpleados(empleadoSeleccionado);
                }
				
				
			}
		});
		
		comboEmpleadoMod.setBounds(21, 82, 282, 27);
		panel_1.add(comboEmpleadoMod);
		
		//LISTENER PARA EL BOTÓN DE MODIFICAR EMPLEADO
		JButton btnNewButton_1 = new JButton("MODIFICAR");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertarModificarEmpleado(1);
			}
		});
		btnNewButton_1.setBounds(70, 260, 184, 29);
		panel_1.add(btnNewButton_1);
		
		//RELLENAMOS EL COMBO
		rellenarComboDepartamentos();
		rellenarComboEmpleados();
		
		//FORMATEAMOS EL TEXT AREA
		mostrarInfoEmpleados(empleadoSeleccionado);
		
	}
	
	//MÉTODO QUE INSERTA UN EMPLEADO EN LA BBDD
	public boolean insertarModificarEmpleado(int accion){
		
		//ASIGNAMOS VALORES DE LAS CAJAS DE TEXTO
		String idE = textID_Empleado.getText();
		String ape = textApellido.getText();
		String ofi = textOficio.getText();
		String sal = textSalario.getText();
		String com = textComision.getText();
		
		Departamentos dep = (Departamentos)comboDepartamentos.getSelectedItem();
		Empleados empDirector = (Empleados)comboDirector.getSelectedItem();
		
		//FORMATEAMOS LA FECHA INTRODUCIDA
		int dia = (int)spinner_Dia.getValue();
		int mes = (int)spinner_Mes.getValue();
		int year = (int)spinner_Year.getValue();
		
		
		String fechaA = dia + "/" + mes  + "/" + year;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		Date fechaAlta =  null;
		
		try {
			java.util.Date f = sdf.parse(fechaA);
			fechaAlta = new Date(f.getTime());
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//COMPROBAMOS LOS VALORES DE LOS CAMPOS OBLIGATORIOS
		if(idE.equals("") || dep == null)
			mostrarPanelError("FALTAN CAMPOS OBLIGATORIOS!");
		else{
			try {
				int id = Integer.parseInt(idE);
				BigDecimal emp_no = BigDecimal.valueOf(id);
				
				
				Double salario;
				if(sal.equals(""))
					salario = 0.0;
				else
					salario = Double.parseDouble(sal);
				
				Double comision;
				if(com.equals(""))
					comision = 0.0;
				else
					comision = Double.parseDouble(com);
				
				if(ape.length() > 10)
					ape = ape.substring(0, 9);
				
				if(ofi.length()> 10)
					ofi = ofi.substring(0, 9);
				
				Empleados nuevoEmple = new Empleados(emp_no,dep,ape,ofi,empDirector.getEmpNo(),fechaAlta,salario,comision);
				
				//INSERTAMOS  O MODIFICAMOS Y MOSTRAMOS RESULTADO DE LA INSERCIÓN
				//0 PARA INSERTAR, 1 PARA MODIFICAR
				if(accion == 0){
					if(g1.insertarEmpleado(nuevoEmple))
						mostrarPanelInfo("Insertado corectamente el empleado: " + nuevoEmple.toString());
					else
						mostrarPanelError("No se pudo insertar, el empleado ya existe");
				}
				else{
					
					Empleados empAntiguo = (Empleados)comboEmpleadoMod.getSelectedItem();
					nuevoEmple.setEmpNo(empAntiguo.getEmpNo());
					if(g1.modificarEmpleado(nuevoEmple, empAntiguo))
						mostrarPanelInfo("Empleado modificado corectamente: " + nuevoEmple.toString());
					else
						mostrarPanelError("No se pudo modificar, el empleado no existe");
				}
					
				rellenarComboEmpleados();
				
			} catch (NumberFormatException e) {
				mostrarPanelError(e.getLocalizedMessage());
			}
		
		}
		
		return false;
	}
	
	
	//MÉTODO PARA RELLENAR EL COMBO BOX CON LOS DEPARTAMENTOS
	public void rellenarComboDepartamentos(){
		
		modeloDep = new DefaultComboBoxModel<>();
		
		//PEDIMOS LA LISTA A LA BBDD
		List listaD = g1.pedirListaDepartamentos();
		
		if(listaD != null){
			//SI LA LISTA NO ES NULA
			for(Object d: listaD){
				modeloDep.addElement((Departamentos)d);
			}
		}
		
		
		comboDepartamentos.setModel(modeloDep);
	}
	
	//MÉTODO PARA RELLENAR EL COMBO BOX CON LOS EMPLEADOS
	//PARA ELEGIR AL DIRECTOR
	public void rellenarComboEmpleados(){

		//INSTANCIAMOS EL ARRAYLIST CON LOS IDS
		//INSTANCIAMOS LOS MODELOS PARA LOS COMBOS
		idsEmpleados = new ArrayList<>();
		listaEmpleados = new ArrayList<>();
		modeloEmp =  new DefaultComboBoxModel<>();
		modeloEmpMod =  new DefaultComboBoxModel<>();
		modeloEmp.addElement(null);
		//PEDIMOS LA LISTA A LA BBDD
		List listaE = g1.pedirListaEmpleados();
		
		if(listaE != null){
			//SI LA LISTA NO ES NULA
			for(Object e: listaE){
				BigDecimal bg = ((Empleados)e).getEmpNo();
				idsEmpleados.add(Integer.valueOf(bg.intValue()));
				modeloEmp.addElement((Empleados)e);
				modeloEmpMod.addElement((Empleados)e);
				listaEmpleados.add((Empleados)e);
			}
			//ORDENAMOS LA LISTA DE IDS DE MENOR A MAYOR
			Collections.sort(idsEmpleados);
			for(int x:idsEmpleados){
				System.out.println(x);
			}
		}

		comboDirector.setModel(modeloEmp);
		comboEmpleadoMod.setModel(modeloEmpMod);
		
		//SELECCIONAMOS EL PRIMER EMPLEADO
		empleadoSeleccionado = (Empleados)comboEmpleadoMod.getSelectedItem();
		
	}
	
	//MÉTODO QUE FORMATEA LA SALIDA EN EL TEXT AREA
	// CON LA INFO DE CADA EMNPLEADO
	public void mostrarInfoEmpleados(Empleados emple){
			
		String dep = g1.pedirDepartamento(emple);

		String cadenaInfo="\nEMP_NO:\t"
				+ emple.getEmpNo() 
				+ "\nAPELLIDO:\t"
				+ emple.getApellido()
				+ "\nOFICIO:\t"
				+ emple.getOficio()
				+ "\nDIR:\t"
				+ emple.getDir()
				+"\nFECHA_ALT:\t"
				+ emple.getFechaAlt()
				+ "\nSALARIO:\t"
				+ emple.getSalario()
				+ "\nCOMISIÓN:\t"
				+ emple.getComision()
				+ "\nDEP:\t"
				+ dep
				+ "\n\n------------------------------------";
		
		textAreaInfo.setText(cadenaInfo);
		idSeleccionado = emple.getEmpNo().intValue();
		System.out.println("Seleccionado:" + idSeleccionado);
	}
	
	
	//METODOS PARA MOSTRAR LOS DIFERENTES MENSAJES
	public void mostrarPanelError(String msg){
		JOptionPane.showMessageDialog(this, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
	
	public void mostrarPanelInfo(String msg){
		JOptionPane.showMessageDialog(this, msg, "INFO", JOptionPane.INFORMATION_MESSAGE);
	}
}
