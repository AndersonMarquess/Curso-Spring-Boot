package com.andersonmarques.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment environment;
	
	//Informa quais endpoint's são públicos
	public static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
	
	public static final String[] PUBLIC_MATCHERS_GET = {"/produtos/**", "/categorias/**" };

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Corrige o problema ao tentar acessar o banco de dados h2
		if(Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
		http.cors().and().csrf().disable();
		
		//Vai permitir qualquer requisição dos caminhos especificados no array de Public_Matchers
		//Os caminhos do Public_Matchers_get apenas possibilitará realizar o get como requisição, os outros ele autentica
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
		.antMatchers(PUBLIC_MATCHERS).permitAll()
		.anyRequest()
		.authenticated();
		//Anotação para informar que o back-end não criará estados
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	//Libera conexões de múltiplas fontes
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource urlBased = new UrlBasedCorsConfigurationSource();
		urlBased.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return urlBased;
	}

	//Realiza a criptografia da senha
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder () {
		return new BCryptPasswordEncoder();
	}
}
