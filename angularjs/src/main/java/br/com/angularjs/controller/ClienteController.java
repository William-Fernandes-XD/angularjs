package br.com.angularjs.controller;

import org.springframework.stereotype.Controller;

import br.com.angularjs.dao.DaoInterface;
import br.com.angularjs.dao.DaoInterfaceImpl;
import br.com.angularjs.model.Cliente;

@Controller
public abstract class ClienteController extends DaoInterfaceImpl<Cliente> implements DaoInterface<Cliente>{

	public ClienteController(Class<Cliente> persistenceClass) {
		super(persistenceClass);
	}
}
