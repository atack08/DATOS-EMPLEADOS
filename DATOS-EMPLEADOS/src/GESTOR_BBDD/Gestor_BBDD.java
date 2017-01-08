package GESTOR_BBDD;


import java.math.BigDecimal;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import BEANS.Empleados;
import BEANS.Departamentos;

public class Gestor_BBDD {
	
	private SessionFactory sesion;

	public Gestor_BBDD(){
		sesion = SessionFactoryBuilder.getSessionfactory();
	}

	//MÉTODO QUE DEVUELVE LA LISTA DE DEPARTAMENTOS PRESENTES EN LA BBDD
	public List pedirListaDepartamentos(){

		List listaD =  null;
		
		try{
			//CREAMOS LA SESIÓN Y DEVOLVEMOS LA LISTA
			Session session = sesion.openSession();
		
			listaD = session.createQuery("From Departamentos").getResultList();
	
			session.close();
		}
		catch (HibernateException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaD;
	}
	
	//MÉTODO QUE DEVUELVE LA LISA DE EMPLEADOS PRESENTES EN LA BBDD
	public List pedirListaEmpleados(){

		List listaE =  null;
		
		try{
			//CREAMOS LA SESIÓN Y DEVOLVEMOS LA LISTA
			Session session = sesion.openSession();
		
			listaE = session.createQuery("From Empleados").getResultList();

			session.close();
		}
		catch (HibernateException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return listaE;
	}
	
	//MÉTODO PARA INSERTAR UN NUEVO EMPLEADO
	public boolean insertarEmpleado(Empleados emp){
		
		try{
			//CREAMOS LA SESIÓN 
			Session session = sesion.openSession();
			Transaction tx =session.beginTransaction();
			
			//COMPROBAMOS SI EL EMPLEADO EXISTE
			// SI NO EXISTE INSERTAMOS Y HACEMOS COMMIT
			if(!existeEmpleado(session, emp.getEmpNo())){
				session.save(emp);
				tx.commit();	
				session.close();	
				return true;
			}
			
			session.close();
		}
		catch (HibernateException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		

		return false;
	}
	
	//MÉTODO PARA MODIFICAR UN EMPLEADO
	public boolean modificarEmpleado(Empleados empNuevo, Empleados empAntiguo){
		
		try{
			//CREAMOS LA SESIÓN 
			Session session = sesion.openSession();
			Transaction tx =session.beginTransaction();
			
			//COMPROBAMOS SI EL EMPLEADO EXISTE
			// SI NO EXISTE INSERTAMOS Y HACEMOS COMMIT
			if(existeEmpleado(session, empNuevo.getEmpNo())){
				
				empAntiguo = (Empleados)session.load(Empleados.class, empNuevo.getEmpNo());
				empAntiguo.setApellido(empNuevo.getApellido());
				empAntiguo.setOficio(empNuevo.getOficio());
				empAntiguo.setDir(empNuevo.getDir());
				empAntiguo.setFechaAlt(empNuevo.getFechaAlt());
				empAntiguo.setSalario(empNuevo.getSalario());
				empAntiguo.setComision(empNuevo.getComision());
				empAntiguo.setDepartamentos(empNuevo.getDepartamentos());
				
				session.update(empAntiguo);
				tx.commit();

				session.close();	
				return true;
			}
			
			session.close();
		}
		catch (HibernateException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		

		return false;
	}
	
	public String pedirDepartamento(Empleados emp){

		String dep = "";
		
		try{
			//CREAMOS LA SESIÓN 
			Session session = sesion.openSession();
	
			//OBTENEMOS EL NOMBRE DEL DEPARTAMENTO
			dep = ((Departamentos)session.load(Departamentos.class,emp.getDepartamentos().getDeptNo())).getDnombre();
			
			session.close();
		}
		catch (HibernateException e) {
			JOptionPane.showMessageDialog(null, e.getLocalizedMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		
		return dep;
		
	}
	
	
	public boolean existeEmpleado(Session session, BigDecimal emp_no){

		Empleados emp = (Empleados) session.get (Empleados.class, emp_no);
		if (emp != null)
			return true;
					
		return false;
	}
	
}
