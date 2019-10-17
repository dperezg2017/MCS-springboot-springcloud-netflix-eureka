package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


@Component
public class PreTiempoTranscurridoFilter extends ZuulFilter{
	
	private static Logger LOGGER =LoggerFactory.getLogger(PreTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		// si vamos a ejecutar el run(), para validar
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx= RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		LOGGER.info(String.format("%s request enrutado a %s", request.getMethod(),request.getRequestURL().toString()));
		
		Long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio",tiempoInicio);
		return null;
	}

	@Override
	public String filterType() {
		// PRE antes se resuelve la ruta, antes de la ocmunicacion con el microservicio, pre es palabra clave
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
