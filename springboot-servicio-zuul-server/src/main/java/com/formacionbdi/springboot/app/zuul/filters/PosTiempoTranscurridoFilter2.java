package com.formacionbdi.springboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


@Component
public class PosTiempoTranscurridoFilter2 extends ZuulFilter{
	
	private static Logger LOGGER =LoggerFactory.getLogger(PosTiempoTranscurridoFilter2.class);

	@Override
	public boolean shouldFilter() {
		// si vamos a ejecutar el run(), para validar
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx= RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		LOGGER.info("entrando a post filter");
		
		Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
		Long tiempoFinal= System.currentTimeMillis();
		Long tiempoTranscurrido = tiempoFinal - tiempoInicio;
		
		
		LOGGER.info(String.format("Tiempo transcurrido en segundos %s segundos.", tiempoTranscurrido.doubleValue()/1000.00));
		LOGGER.info(String.format("Tiempo transcurrido en segundos %s milisegundos", tiempoTranscurrido));
		
		return null;
	}

	@Override
	public String filterType() {
		// POST despues  de la ocmunicacion con el microservicio, pre es palabra clave
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
