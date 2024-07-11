package it.corso.service;

import java.util.List;

import it.corso.dto.CourseDto;
import it.corso.dto.CourseUpdateDto;
import it.corso.model.Course;

public interface CourseService {
	
	CourseDto getCourseById(int id);
	boolean existsById(int id);
	List<CourseDto> getCourses();
	void courseRegistration(CourseDto courseDto);
	void updateCourse(int id, CourseUpdateDto courseDto);
	void deleteCourse(int id);
	List<CourseDto> findByName(String name);
	List<CourseDto> findByDuration(int duration);
	
}
