package com.aapeliltd.demo.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenVerifier extends OncePerRequestFilter {
	
	@Autowired
	private JwtConfig JwtConfig;
	
	
	



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {

		String authorizationHeader = request.getHeader(JwtConfig.getAuthorizationHeader());
		
		if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(JwtConfig.getPrefixToken())) {
			
			filterChain.doFilter(request, response);
			
			return ;
			
		}
		String token = authorizationHeader.replace(JwtConfig.getPrefixToken(), "");
		try {
			
						
			Jws<Claims> claimsJws = Jwts.parser()
				.setSigningKey(JwtConfig.getSecretKeyForSigning()).parseClaimsJws(token);
			
			Claims body = claimsJws.getBody();
			
			String username = body.getSubject();
			
			var authorities =  (List<Map<String, String>>)body.get("authorities");
			
			
			Set<SimpleGrantedAuthority> simpleAuthority = 
				 authorities.stream().map(m->new SimpleGrantedAuthority(m.get("authority")))
				 .collect(Collectors.toSet());
		 
		 	UsernamePasswordAuthenticationToken authentication 
							= new UsernamePasswordAuthenticationToken(username, null, simpleAuthority);
			
			
		 	SecurityContextHolder.getContext().setAuthentication(authentication);
			
			
			
		} catch (JwtException e) {
			throw new IllegalStateException(String.format("Token %s cannot be trusted.", token));
		}
		
		
		
		filterChain.doFilter(request, response);

	}

}
