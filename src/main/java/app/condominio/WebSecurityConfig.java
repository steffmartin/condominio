package app.condominio;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.authorizeRequests()
			//.antMatchers("/","/js/**","/css/**","/imagens/**","/webfonts/**").permitAll()
			.antMatchers("/sindico/**").hasAuthority("SINDICO")// .access("hasRole('ROLE_SINDICO')")
			.antMatchers("/condomino/**").hasAuthority("CONDOMINO")// .access("hasRole('ROLE_MORADOR')")
			.antMatchers("/admin/**").hasAuthority("ADMIN")// .access("hasRole('ROLE_MORADOR')")
			.antMatchers("/autenticado/**","/conta/cadastro/**").authenticated()
			//.antMatchers("/conta/cadastrar/**","/entrar/**").anonymous()
			//.anyRequest().authenticated()
		.and().formLogin()
		  	.loginPage("/entrar")
		  	.failureUrl("/entrar?erro")
		  	.defaultSuccessUrl("/autenticado")
		  	.usernameParameter("username").passwordParameter("password")
		.and().logout()
			.logoutSuccessUrl("/entrar?sair")
			.logoutUrl("/sair")
			.invalidateHttpSession(true)
			.clearAuthentication(true)
		//.and().exceptionHandling()
			//.accessDeniedPage("/erro")
		.and().rememberMe()
		  	.tokenRepository(persistentTokenRepository())
		  	.tokenValiditySeconds(120960)
		.and().csrf();
		// @formatter:on
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password,ativo from usuarios where username=?")
				.authoritiesByUsernameQuery(
						"select username,autorizacao from usuarios join autorizacoes on id = id_usuario where username=?");
	}
	// LATER implementar meu próprio UserDetailsService para mostrar primeiro nome
	// do usuário no site
	// https://stackoverflow.com/questions/17297322/in-spring-how-to-print-user-first-name-and-last-name-from-secauthentication-p

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}
}
