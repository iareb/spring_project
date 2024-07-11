package it.corso.service;

import java.util.List;

import it.corso.dto.UserDto;
import it.corso.dto.UserLoginRequestDto;
import it.corso.dto.UserRegistrationDto;
import it.corso.dto.UserUpdateDto;
import it.corso.model.User;

public interface UserService {
	
	void userRegistration(UserRegistrationDto userDto);
	boolean login(UserLoginRequestDto userDto);
	boolean existsUser(String email);
	
	UserDto getUserById(int id);
	UserDto getUserDtoByEmail(String email);
	User getUserByEmail(String email);
	
	List<UserDto> getUsers();
	void updateUserData(UserUpdateDto userDto);
	void deleteUser(String email);
	
	void addRole(int userId, int roleId, String roleName);
	
	void subscribeToCourse(int userId, int courseId);
	void unsubscribeFromCourse(int userId, int courseId);

}
