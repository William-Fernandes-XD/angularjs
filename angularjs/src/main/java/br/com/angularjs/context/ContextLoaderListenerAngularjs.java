package br.com.angularjs.context;

import java.io.Serializable;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ContextLoaderListenerAngularjs extends ContextLoaderListener implements Serializable{

	private static final long serialVersionUID = 1L;

	private static WebApplicationContext getWac() {
		return WebApplicationContextUtils.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
	}
	
	public static Object getBean(String idNomeBean) {
		return getWac().getBean(idNomeBean);
	}
	
	public static Object getBean(Class<?> classe) {
		return getWac().getBean(classe);
	}
}
