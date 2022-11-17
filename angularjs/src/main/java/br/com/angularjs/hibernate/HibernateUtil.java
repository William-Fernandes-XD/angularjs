package br.com.angularjs.hibernate;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil implements Serializable{

	private static final long serialVersionUID = 1L;
	private static SessionFactory factory = buildSessionFactory();
			
	@SuppressWarnings("deprecation")
	private static SessionFactory buildSessionFactory() {
		
		try {
			if(factory == null) {
				factory = (new Configuration()).configure().buildSessionFactory();
			}
		}catch(Exception e) {
			System.err.println("Ocorreu um erro ao tentar criar sessionFactory");
			e.printStackTrace();
		}
		return factory;
	}
	
	public static SessionFactory getFactory() {
		return factory;
	}
}
