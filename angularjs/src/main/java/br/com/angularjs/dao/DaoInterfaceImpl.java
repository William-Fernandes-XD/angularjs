package br.com.angularjs.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import br.com.angularjs.hibernate.HibernateUtil;

@Service
public abstract class DaoInterfaceImpl<T> implements DaoInterface<T>{

	private Class<T> persistenceClass;
	private SessionFactory factory = HibernateUtil.getFactory();
	
	public DaoInterfaceImpl(Class<T> persistenceClass){
		this.persistenceClass = persistenceClass;
	}
	
	@Override
	public void salvar(T objeto) throws Exception {
		factory.getCurrentSession().save(objeto);
	}
	
	@Override
	public void deletar(T objeto) throws Exception {
		factory.getCurrentSession().delete(objeto);
	}
	
	@Override
	public void atualizar(T objeto) throws Exception {
		factory.getCurrentSession().update(objeto);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T objeto) throws Exception {
		T objetoAtualizado = (T) factory.getCurrentSession().merge(objeto);
		return objetoAtualizado;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> lista() throws Exception {
		return factory.getCurrentSession().createCriteria(persistenceClass).list();
	}
}
