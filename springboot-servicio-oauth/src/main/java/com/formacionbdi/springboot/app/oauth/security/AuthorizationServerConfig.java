package com.formacionbdi.springboot.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.checkTokenAccess("permitAll()") // validan token
		.checkTokenAccess("isAuthenticated()"); // metodo de spring secuity, permite valida lo autenticado, se envia lciente ID con su secret
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient("frontedapp")  // identificado del app fronted
		.secret(passwordEncoder.encode("12345"))
		.scopes("read","write") // se puede leer y escribir
		.authorizedGrantTypes("password","refresh_token")  // usuario que inicia sesion en backend, autencacion login y la concecion, refresh_token, permite tener nuevo token antes que caduque el actual
		.accessTokenValiditySeconds(3600) // tiempo para refresh_token
		.refreshTokenValiditySeconds(3600);
// si en caso se trabaja con mas clientes fronted
//		.and()
//		.withClient("frontedapp2") 
//		.secret(passwordEncoder.encode("12345"))
//		.scopes("read","write") 
//		.authorizedGrantTypes("password","refresh_token")  
//		.accessTokenValiditySeconds(3600) 
//		.refreshTokenValiditySeconds(3600);
	}	

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		endpoints.authenticationManager(authenticationManager)
		.tokenStore(tokenStore())
		.accessTokenConverter(accessTokenConverter());
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter()); // crear tokey  almacenar username,roles, pass
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey("algun_codigo_secreto_aeiou"); // para que sea unico
		return null;
	}
	
	
}
 