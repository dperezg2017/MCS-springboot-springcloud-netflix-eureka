package com.formacionbdi.springboot.app.productos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.springboot.app.productos.models.entity.Producto;
import com.formacionbdi.springboot.app.productos.models.service.IProductoService;

import net.bytebuddy.implementation.bytecode.Throw;

@RestController
public class ProductoController {
	
	@Autowired
	private IProductoService productoService;
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private Environment env;
	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.findAll().stream().map(p-> {
			//p.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			p.setPort(port);	
				return p;
					}).collect(Collectors.toList());
	}

	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws Exception{
		Producto producto = productoService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setPort(port);
//		try {
//			
//			Thread.sleep(2999l);
//
//		}catch (Exception e) {
//			e.printStackTrace();
//		}
		
		return producto;
	}
}
