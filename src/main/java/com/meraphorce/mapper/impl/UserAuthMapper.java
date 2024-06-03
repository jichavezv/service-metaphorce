package com.meraphorce.mapper.impl;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.meraphorce.dto.auth.UserAuthDTO;
import com.meraphorce.mapper.IMapper;
import com.meraphorce.models.User;

/**
 * Class to convert User Entity to UserAuthDTO
 * @author Juan Chavez
 * @since May/29/2024
 */
public class UserAuthMapper implements IMapper<User, UserAuthDTO> {

	@Override
	public User toEntity(UserAuthDTO dtoValue) {
		// TODO Auto-generated method stub
		return User.builder()
				.name(dtoValue.getUsername())
				.email(dtoValue.getEmail())
				.password(dtoValue.getPassword())
				.roles(dtoValue.getAuthorities().stream()
						.map(e -> String.valueOf(e))
						.collect(Collectors.joining(",")))
				.build();
	}

	@Override
	public UserAuthDTO toDTO(User entityValue) {
		// TODO Auto-generated method stub
		return UserAuthDTO.builder()
				.name(entityValue.getName())
				.email(entityValue.getEmail())
				.password(entityValue.getPassword())
				.authorities(Arrays.stream(entityValue.getRoles().split(","))
		                .map(SimpleGrantedAuthority::new)
		                .collect(Collectors.toList()))
				.build();
	}

}
