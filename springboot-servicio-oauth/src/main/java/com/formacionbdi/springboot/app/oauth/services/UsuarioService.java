package com.formacionbdi.springboot.app.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.formacionbdi.springboot.app.commons.models.entity.Usuario;
import com.formacionbdi.springboot.app.oauth.clients.UsuarioFeignClient;

@Service
public class UsuarioService implements IUsuarioService,UserDetailsService {
	//UserDetailsService: para la autenticacion
	// IUsuarioService: para obtener al usuario objeto con todos los datos

	private Logger log = LoggerFactory.getLogger(UsuarioService.class);

	@Autowired
	private UsuarioFeignClient client;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = client.findByUsername(username);
		
		if(usuario==null) {
			throw new UsernameNotFoundException("Error en el login, no existe el usuario'"+username+"' en el sistema");
		}
		// los roles se encuentran GrantedAuthority: de interfaz , SimpleGrantedAuthority pertenece a GrantedAuthority, pero este es la concreta. 
		List<GrantedAuthority> authorities = usuario.getRole()
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> log.info("Role: "+authority.getAuthority()))
				.collect(Collectors.toList());
				log.info("Usuario autenticado"+username);
		
	return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

}
