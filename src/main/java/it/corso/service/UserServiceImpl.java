package it.corso.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.dao.CourseDao;
import it.corso.dao.UserDao;
import it.corso.dto.UserDto;
import it.corso.dto.UserLoginRequestDto;
import it.corso.dto.UserRegistrationDto;
import it.corso.dto.UserUpdateDto;
import it.corso.model.Course;
import it.corso.model.Role;
import it.corso.model.Tipology;
import it.corso.model.User;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void userRegistration(UserRegistrationDto userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		
		String sha256hex = DigestUtils.sha256Hex(userDto.getPassword());
		user.setPassword(sha256hex);
		
		userDao.save(user);
	}

	
	@Override
	public boolean login(UserLoginRequestDto userDto) {
		
		String email = userDto.getEmail();
		String password = userDto.getPassword();
		
		Optional<User> userOptional = userDao.findByEmail(email);
		
		if (userOptional.isPresent()) {
			
			User user = userOptional.get();			
			String hashedPassword = DigestUtils.sha256Hex(password);
			
			return hashedPassword.equals(user.getPassword());
		}
		
		return false;
		
	}

	@Override
	public boolean existsUser(String email) {
		Optional<User> userOptional = userDao.findByEmail(email);
		
		if (userOptional.isPresent()) {
			return true;
		}
		
		return false;
	}
	
	
	@Override
	public UserDto getUserById(int id) {
		
		Optional<User> userOptional = userDao.findById(id);
		
		if (!userOptional.isPresent()) {
			return new UserDto();
		}
		
		User userDb = userOptional.get();
		UserDto userDto = modelMapper.map(userDb, UserDto.class);
		return userDto;
	}
	
	
	@Override
	public UserDto getUserDtoByEmail(String email) {
		
		Optional<User> userOptional = userDao.findByEmail(email);
		
		if (!userOptional.isPresent()) {
			return new UserDto();
		}
		
		User userDb = userOptional.get();
		UserDto userDto = modelMapper.map(userDb, UserDto.class);
		return userDto;
		
	}
	
	@Override
	public User getUserByEmail(String email) {
		
		Optional<User> userOptional = userDao.findByEmail(email);
		
		if (!userOptional.isPresent()) {
			return new User();
		}
		
		return userOptional.get();		
	}
	
	
	@Override
	public List<UserDto> getUsers() {
		List<User> users = (List<User>) userDao.findAll();
		
		List<UserDto> usersDto = modelMapper.map(users, new TypeToken<List<UserDto>>() {}.getType());
		return usersDto;
	}
	

	@Override
	public void updateUserData(UserUpdateDto userDto) {
		
		Optional<User> userOptional = userDao.findByEmail(userDto.getEmail());
		if (userOptional.isPresent()) {
			
			User userDb = userOptional.get();
			userDb.setName(userDto.getName());
			userDb.setLastName(userDto.getLastName());
			userDb.setEmail(userDto.getEmail());
			
	        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
	            String sha256hex = DigestUtils.sha256Hex(userDto.getPassword());
	            userDb.setPassword(sha256hex);
	        }
			
			userDao.save(userDb);
		}
		else {
			throw new EntityNotFoundException("Not found user with email " + userDto.getEmail());
		}

	}
	

	@Override
	public void deleteUser(String email) {
		
		Optional<User> userOptional = userDao.findByEmail(email);
		if (userOptional.isPresent()) {
			userDao.delete(userOptional.get());
		}
	}
	
	@Override
	public void addRole(int userId, int roleId, String roleName) {
		
		Optional<User> userOptional = userDao.findById(userId);
		if (userOptional.isPresent()) {
			
			List<Role> roles = new ArrayList<>();
			Role role = new Role();
			role.setId(roleId);
			role.setTipology(Tipology.valueOf(roleName));
			roles.add(role);	
			User userDb = userOptional.get();
			userDb.setRoles(roles);
			userDao.save(userDb);
		}
	}


	@Override
	public void subscribeToCourse(int userId, int courseId) {
		
		Optional<User> userOptional = userDao.findById(userId);
		Optional<Course> courseOptional = courseDao.findById(courseId);
		
		try {
		
			if (userOptional.isPresent() && courseOptional.isPresent()) {
				
				Course course = courseOptional.get();
				User user = userOptional.get();
				
				user.getCourses().add(course);
				userDao.save(user);
			}	
		} 
		catch (Exception e) {
			throw new RuntimeException("Could not subscribe user to course, " + e.getMessage());
		}
	}


	@Override
	public void unsubscribeFromCourse(int userId, int courseId) {
		
	    try {
	        Optional<User> userOptional = userDao.findById(userId);
	        if (!userOptional.isPresent()) {
	            throw new EntityNotFoundException("User with ID " + userId + " not found");
	        }

	        Optional<Course> courseOptional = courseDao.findById(courseId);
	        if (!courseOptional.isPresent()) {
	            throw new EntityNotFoundException("Course with ID " + courseId + " not found");
	        }

	        User user = userOptional.get();
	        Course course = courseOptional.get();
	        
	        user.getCourses().remove(course);
	        userDao.save(user);
	        
	    } catch (Exception e) {
	        throw new RuntimeException("Failed to unsubscribe user from course: " + e.getMessage());
	    }
	}

}
