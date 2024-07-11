package it.corso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.corso.dto.CourseDto;
import it.corso.dto.CourseUpdateDto;
import it.corso.jwt.JWTTokenNeeded;
import it.corso.jwt.Secured;
import it.corso.service.CourseService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/*
@Secured(role = "Admin")
@JWTTokenNeeded
*/
@RestController
@Path("/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@POST
	@Path("/registration")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(@RequestBody CourseDto courseDto) {
		
		try {
			if (courseService.existsById(courseDto.getId())) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			
			courseService.courseRegistration(courseDto);
			return Response.status(Response.Status.OK).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCourseById(@PathParam("id") int id) {
		
		try {
			CourseDto courseDto = courseService.getCourseById(id);
			return Response.status(Response.Status.OK).entity(courseDto).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getCourses() {
		
		try {
			List<CourseDto> courses = courseService.getCourses();
	        return Response.status(Response.Status.OK).entity(courses).build();
	    }
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@PUT
	@Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response updateCourse(@PathParam("id") int id, @RequestBody CourseUpdateDto courseDto) {
		
		try {
			courseService.updateCourse(id, courseDto);
			return Response.status(Response.Status.OK).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}	
	}
	
	@DELETE
	@Path("/delete/{id}")
    @Produces(MediaType.APPLICATION_JSON)
	public Response deleteCourse(@PathParam("id") int id) {
		
		try {
			courseService.deleteCourse(id);
			return Response.status(Response.Status.OK).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	@GET
	@Path("/{name}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCourseByName(@PathParam("name") String name) {
		try {
			courseService.findByName(name);
			return Response.status(Response.Status.OK).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
	
	@GET
	@Path("/length/{duration}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getCourseByDuration(@PathParam("duration") int duration) {
		try {
			courseService.findByDuration(duration);
			return Response.status(Response.Status.OK).build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}
	
}
