package it.corso.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import it.corso.dto.UserDto;
import it.corso.model.Role;
import it.corso.model.Tipology;
import it.corso.model.User;

public class UserMapper {
	
	public static UserDto toDto(User user) {
		
		UserDto userDto = new UserDto();
		userDto.setName(user.getName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		
		Set<Tipology> roles = user.getRoles().stream()
								.map(Role::getTipology)
								.collect(Collectors.toSet());
		
		userDto.setRoles(roles);
		return userDto;
	}
	
	public static List<UserDto> toDtoList(List<User> users) {
		return users.stream()
					.map(UserMapper::toDto)
					.collect(Collectors.toList());
	}

}
