package com.aapeliltd.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.aapeliltd.demo.auth.ApplicationUserService;
import com.aapeliltd.demo.jwt.JwtConfig;
import com.aapeliltd.demo.jwt.JwtTokenVerifier;
import com.aapeliltd.demo.jwt.JwtUsernameAndPasswordFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	public final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	
	private final JwtConfig JwtConfig;
	
	@Autowired
	public ApplicationSecurityConfig(
			PasswordEncoder passwordEncoder,
			JwtConfig jwtConfig,
			ApplicationUserService applicationUserService) {

		this.passwordEncoder = passwordEncoder;
		this.JwtConfig = jwtConfig;
		this.applicationUserService = applicationUserService;
	}
	
	

	
	
	
	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		
//		http
//			.csrf().disable()
//			.authorizeRequests()
//			.antMatchers("/", "/index", "/error", "/css/*", "/jss/*")
//			.permitAll()
//			.antMatchers("/api/**").hasAnyRole(ApplicationUserRole.STUDENT.name())
//				/*
//				 * .antMatchers(HttpMethod.DELETE,
//				 * "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.
//				 * getPermission()) .antMatchers(HttpMethod.POST,
//				 * "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_WRITE.
//				 * getPermission()) .antMatchers(HttpMethod.PUT,
//				 * "/management/api/**").hasAuthority(ApplicationUserPermission.COURSE_READ.
//				 * permission) .antMatchers(HttpMethod.GET,
//				 * "/management/api/**").hasAnyRole(ApplicationUserRole.ADMIN.name(),
//				 * ApplicationUserRole.TRANEE.name())
//				 */
//			.anyRequest()
//			.authenticated()
//			.and()
////			.httpBasic(); // basic authentication
//			.formLogin()
//				.loginPage("/login").permitAll().defaultSuccessUrl("/courses", true)
//				.passwordParameter("password")
//				.usernameParameter("username")
//			
//			.and()
//			.rememberMe() //this is default two weeks.
//				.rememberMeParameter("remember-me")
//			.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)).key("verysecured") //increase the validity 
//			// of the cookies for 21 days.
//			// add key like MD5 to make it secured.
//			.and()
//			.logout()
//				.logoutUrl("/logout")
//				.clearAuthentication(true)
//				.invalidateHttpSession(true)
//				.deleteCookies("JSESSIONID", "remember-me")
//				.logoutSuccessUrl("/login");
//				
//		
//			
//	}
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.addFilter(new JwtUsernameAndPasswordFilter(authenticationManager()))
			.addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordFilter.class)
			.authorizeRequests()
			.antMatchers("/", "/index", "/css/*", "/js/*").permitAll()
			.antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
			.anyRequest()
			.authenticated();
			
			
	}





	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticataionProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticataionProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
		
	}

	//to create user, you will need override this.
	//ideally you should retrieve the user from the database
	//but for the example sake, lets build the user and use it.
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		
//		//creation of user 1
//		UserDetails jamesUser = User.builder()
//				.username("james")
//				.password(passwordEncoder.encode("123456"))
//				.roles(ApplicationUserRole.STUDENT.name()) // ROLE_STUDENT
//				.authorities(ApplicationUserRole.STUDENT.getGrandtedAuthorities())
//				.build();
//		
//		//creation of user 2
//		UserDetails max = User.builder()
//				.username("max")
//				.password(passwordEncoder.encode("123456"))
//				.roles(ApplicationUserRole.ADMIN.name())
//				.authorities(ApplicationUserRole.ADMIN.getGrandtedAuthorities())
//				.build();
//		
//		//creation of user 3.
//		UserDetails tom = User.builder()
//				.username("tom")
//				.password(passwordEncoder.encode("123456"))
//				.roles(ApplicationUserRole.TRANEE.name())
//				.authorities(ApplicationUserRole.TRANEE.getGrandtedAuthorities())
//				.build();
//				
//		
//		return new InMemoryUserDetailsManager(
//				jamesUser,
//				max,
//				tom
//		);
//	  
//	
//		
//	}
	
	

}
