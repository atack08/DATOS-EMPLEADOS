package GESTOR_BBDD;

import javax.swing.JOptionPane;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder {
	
	private static  SessionFactory SessionFactory;
	
	static{
		
		try{
			SessionFactory = new Configuration().configure().buildSessionFactory();
			
			JOptionPane.showMessageDialog(null, "SESIÓN HIBERNATE CREADA SATISFACTORIAMENTE");
		}
		catch (Throwable e) {
			JOptionPane.showMessageDialog(null, "Fallo en la creación inicial de la Session Factory","ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
	}

	public static SessionFactory getSessionfactory() {
		return SessionFactory;
	}

}
