package com.formacionbdi.springboot.app.usuarios.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.formacionbdi.springboot.app.commons.models.entity.Usuario;

// hereda de crudrepository, sirve para paginacion

@RepositoryRestResource(path = "usuarios") // donde se va exportar el crudrepository
public interface UsuarioDao extends PagingAndSortingRepository<Usuario, Long>{

	// lo que hara es JPQL: select u from Usuario u where u.username=?1 and u.email=?2
	//public Usuario findByUsernameAndEmail(String usernamer, String email);
	
	@RestResource(path = "buscar-username")
	public Usuario findByUsername(@Param("username") String username);
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario obtenerPorUsername(String username);
}
