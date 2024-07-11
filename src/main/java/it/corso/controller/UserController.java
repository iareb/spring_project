package it.corso.controller;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.corso.dto.UserDto;
import it.corso.dto.UserLoginRequestDto;
import it.corso.dto.UserLoginResponseDto;
import it.corso.dto.UserRegistrationDto;
import it.corso.dto.UserUpdateDto;
import it.corso.model.Role;
import it.corso.model.User;
import it.corso.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;


@RestController
@Path("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	public UserLoginResponseDto issueToken(String email) {
	    
	    byte[] secret = "cndeoivevfnkvenfiv448934jfv98rmfu4t843tm40".getBytes();
	      Key key = Keys.hmacShaKeyFor(secret);
	    
	      User infoUser = userService.getUserByEmail(email);
	      Map<String, Object> map = new HashMap<>();
	      
	      map.put("name", infoUser.getName());
	      map.put("lastName", infoUser.getLastName());
	      map.put("email", infoUser.getEmail());
	      
	      List<String> roles = new ArrayList<>();
	      for (Role role : infoUser.getRoles()) {
	        roles.add(role.getTipology().name());
	        
	      }
	      
	      map.put("roles", roles);
	      
	      Date creationDate = new Date();
	      Date end = java.sql.Timestamp.valueOf(LocalDateTime.now().plusMinutes(15L));
	    
	      String tokenJwt = Jwts.builder()
	          .setClaims(map)
	          .setIssuer("http://localhost:8080")
	          .setIssuedAt(creationDate)
	          .setExpiration(end)
	          .signWith(key)
	          .compact();
	      
	      UserLoginResponseDto token = new UserLoginResponseDto();
	      token.setToken(tokenJwt);
	      token.setTokenCreationTime(creationDate);
	      token.setTtl(end);
	      
	    return token;
	  }
	  
	
	@POST
	@Path("/registration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registration(@Valid @RequestBody UserRegistrationDto userDto) {
		
		try {
			
			if (!Pattern.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}", 
					userDto.getPassword())) {
				
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			if (userService.existsUser(userDto.getEmail())) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			userService.userRegistration(userDto);
			return Response.status(Response.Status.OK).build();
			
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
	        
	    try {
	          if(userService.login(userLoginRequestDto)) {
	            return Response.ok(issueToken(userLoginRequestDto.getEmail())).build();
	          }
	        } 
	    catch (Exception e) {
	    	  
	          return Response.status(Response.Status.BAD_REQUEST).build();
	    }
	    
	    return Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		
		try {
			List<UserDto> usersList = userService.getUsers();
			return Response.status(Response.Status.OK).entity(usersList).build();

		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUserById(@PathParam("id") int id) {
		
		try {
			UserDto userDto = userService.getUserById(id);
			return Response.status(Response.Status.OK).entity(userDto).build();
		}
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByEmail(@QueryParam("email") String email) {
		
		try {
			
			UserDto userDto = userService.getUserDtoByEmail(email);
			return Response.status(Response.Status.OK).entity(userDto).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@RequestBody UserUpdateDto userDto) {
		
		try {
			userService.updateUserData(userDto);
			return Response.status(Response.Status.OK).build();

		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@DELETE
	@Path("/delete/{email}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response deleteUser(@PathParam("email") String email) {
		
		try {
			userService.deleteUser(email);
			return Response.status(Response.Status.OK).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@PUT
	@Path("/{userId}/role/{roleId}/{roleName}")
	public Response addRole(@PathParam("userId") int userId, @PathParam("roleId") int roleId, @PathParam("roleName") String roleName) {
		
		try {
			userService.addRole(userId, roleId, roleName);
			return Response.status(Response.Status.OK).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@POST
	@Path("/{userId}/subscribe/{courseId}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response subscribeToCourse(@PathParam("userId") int userId, @PathParam("courseId") int courseId) {
		
		try {
			userService.subscribeToCourse(userId, courseId);
			return Response.status(Response.Status.OK).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	
	@POST
	@Path("/{userId}/unsubscribe/{courseId}")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response unsubscribeFromCourse(@PathParam("userId") int userId, @PathParam("courseId") int courseId) {
		
		try {
			userService.unsubscribeFromCourse(userId, courseId);
			return Response.status(Response.Status.OK).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
}
