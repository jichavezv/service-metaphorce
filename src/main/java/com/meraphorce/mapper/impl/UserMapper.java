package com.meraphorce.mapper.impl;

import com.meraphorce.dto.UserDTO;
import com.meraphorce.mapper.IMapper;
import com.meraphorce.models.User;

/**
 * Mapper class to convert User and UserDTO
 * @author Juan Chavez
 * @since May/14/2024
 */
public class UserMapper implements IMapper<User, UserDTO> {

	@Override
	public User toEntity(UserDTO dtoValue) {
		// TODO Auto-generated method stub
		return User.builder()
				.id(dtoValue.getUserId())
				.name(dtoValue.getUserName())
				.email(dtoValue.getUserEmail())
				.build();
	}

	@Override
	public UserDTO toDTO(User entityValue) {
		// TODO Auto-generated method stub
		return UserDTO.builder()
				.userId(entityValue.getId())
				.userName(entityValue.getName())
				.userEmail(entityValue.getEmail())
				.build();
	}
}
