package br.com.angularjs.dao;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface DaoInterface<T>{

	void salvar(T objeto) throws Exception;
	void deletar(T objeto) throws Exception;
	void atualizar(T objeto) throws Exception;
	void salvarOuAtualizar(T objeto) throws Exception;
	
	T merge(T objeto) throws Exception;
	
	List<T> lista() throws Exception;
}
