package com.security1.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.security1.security.entity.UserInfo;
import com.security1.security.repository.UserInfoRepository;

@Component
public class UserInfoUserDetails implements UserDetailsService {

	@Autowired
	private UserInfoRepository infoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<UserInfo> userinfo = infoRepository.findByName(username);
		return userinfo.map(UserDetailsInfo::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " name is not present in Db"));
	}

}
