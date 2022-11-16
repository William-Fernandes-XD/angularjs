package br.com.angularjs.filter;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.filter.DelegatingFilterProxy;

import br.com.angularjs.context.ContextLoaderListenerAngularjs;
import br.com.angularjs.hibernate.HibernateUtil;

@WebFilter(filterName = "conexaoFilter")
public class FilterOpenSessionInView extends DelegatingFilterProxy implements Serializable{

	private static final long serialVersionUID = 1L;
	private static SessionFactory factory;
	
	@Override
	protected void initFilterBean() throws ServletException {
		factory = HibernateUtil.getFactory();
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		// Pega o banco de dados do spring, cria um padrão, depois cria uma plataforma de transações com o padrão criado
		
		BasicDataSource springDataSource = (BasicDataSource) ContextLoaderListenerAngularjs.getBean("springDataSource");
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(springDataSource);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try {
			
			request.setCharacterEncoding("UTF-8");
			factory.getCurrentSession().getTransaction().commit();
			
			if(factory.getCurrentSession().getTransaction().isActive()) {
				
				factory.getCurrentSession().flush();
				factory.getCurrentSession().getTransaction().commit();
			}
			
			if(factory.getCurrentSession().isOpen()) {
				factory.getCurrentSession().close();
			}
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
		}catch (Exception e) {
			
			transactionManager.rollback(status);
			
			if(factory.getCurrentSession().getTransaction().isActive()) {
				
				factory.getCurrentSession().flush();
				factory.getCurrentSession().getTransaction().commit();
			}
			
			if(factory.getCurrentSession().isOpen()) {
				factory.getCurrentSession().close();
			}
			
			System.err.println("Erro ao tentar filtrar transações");
			e.printStackTrace();
		}finally {
			
			if(factory.getCurrentSession().getTransaction().isActive()) {
				if(factory.getCurrentSession().beginTransaction().isActive()) {
					factory.getCurrentSession().flush();
					factory.getCurrentSession().clear();
				}
				
				if(factory.getCurrentSession().isOpen()) {
					factory.getCurrentSession().close();
				}
			}
		}
	}
}
