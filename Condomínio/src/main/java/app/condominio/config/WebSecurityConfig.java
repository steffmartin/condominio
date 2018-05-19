package app.condominio.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
        	.usersByUsernameQuery("select username,password,ativo from usuarios where username=?")
        	.authoritiesByUsernameQuery("select username,autorizacao from autorizacoes where username=?");
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	  return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/sindico/**").access("hasRole('ROLE_SINDICO')")
		.antMatchers("/morador/**").access("hasRole('ROLE_MORADOR')")
		.anyRequest().authenticated()
		.and()
		  .formLogin()/*.loginPage("/entrar").failureUrl("/entrar?invalido")
		  .usernameParameter("username").passwordParameter("password")
		.and()
		  .logout().logoutSuccessUrl("/entrar?saiu")
		.and()
		  .exceptionHandling().accessDeniedPage("/erro?403")
		.and()
		  .rememberMe()*/
		.and()
		  .csrf();
    }
	
}
