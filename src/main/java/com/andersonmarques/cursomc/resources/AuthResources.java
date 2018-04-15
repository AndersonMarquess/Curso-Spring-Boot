package com.andersonmarques.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andersonmarques.cursomc.dto.EmailDTO;
import com.andersonmarques.cursomc.security.JWTUtil;
import com.andersonmarques.cursomc.security.UserSS;
import com.andersonmarques.cursomc.services.AuthService;
import com.andersonmarques.cursomc.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResources {
	
	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");

		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot (@Valid @RequestBody EmailDTO objDTO) {
		authService.sandNewPassword(objDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
}
