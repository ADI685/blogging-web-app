package com.adi685.blogging_web_app.security;

import com.adi685.blogging_web_app.entity.User;
import com.adi685.blogging_web_app.exception.ResourceNotFoundException;
import com.adi685.blogging_web_app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		//loading user from username

		return userRepository.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","UserName",username));
	}
}
