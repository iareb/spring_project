package it.corso.mapper;

import it.corso.dto.CourseDto;
import it.corso.model.Course;

public class CourseMapper {
	
	public static CourseDto toDto(Course course) {
		
		CourseDto courseDto = new CourseDto();
		courseDto.setName(course.getName());
		courseDto.setShortDescription(course.getShortDescription());
		courseDto.setFullDescription(course.getFullDescription());
		courseDto.setDuration(course.getDuration());
		return courseDto;
	}
	
	public static Course toEntity(CourseDto courseDto) {
		
		Course course = new Course();
		course.setId(courseDto.getId());
		course.setName(courseDto.getName());
		course.setShortDescription(courseDto.getShortDescription());
		course.setFullDescription(courseDto.getFullDescription());
		course.setDuration(courseDto.getDuration());
		return course;
	}
	
}
