package it.corso.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.corso.dao.CourseDao;
import it.corso.dto.CourseDto;
import it.corso.dto.CourseUpdateDto;
import it.corso.model.Course;
import it.corso.model.User;

@Service
public class CourseServiceImpl implements CourseService {
	
	@Autowired
	private CourseDao courseDao;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CourseDto getCourseById(int id) {
		
		Optional<Course> courseOptional = courseDao.findById(id);
		
		if (!courseOptional.isPresent()) {
			return new CourseDto();
		}
		
		Course courseDb = courseOptional.get();
		CourseDto courseDto = modelMapper.map(courseDb, CourseDto.class);
		return courseDto;
	}

	@Override
	public boolean existsById(int id) {
		Optional<Course> courseOptional = courseDao.findById(id);
		
		if (courseOptional.isPresent()) {
			return true;
		}
		
		return false;
	}

	
	@Override
	public List<CourseDto> getCourses() {
		List<Course> courses = (List<Course>) courseDao.findAll();
		
		List<CourseDto> coursesDto = modelMapper.map(courses, new TypeToken<List<CourseDto>>() {}.getType());
		return coursesDto;
	}

	
	@Override
	public void courseRegistration(CourseDto courseDto) {
		
		Course course = new Course();
		course.setId(courseDto.getId());
		course.setName(courseDto.getName());
		course.setShortDescription(courseDto.getShortDescription());
		course.setFullDescription(courseDto.getFullDescription());
		course.setDuration(courseDto.getDuration());
		
		courseDao.save(course);
	}

	@Override
	public void updateCourse(int id, CourseUpdateDto courseDto) {
		
		Optional<Course> courseOptional = courseDao.findById(id);
		if (courseOptional.isPresent()) {
			
			Course courseDb = courseOptional.get();		
			courseDb.setName(courseDto.getName());
			courseDb.setShortDescription(courseDto.getShortDescription());
			courseDb.setFullDescription(courseDto.getFullDescription());
			courseDb.setDuration(courseDto.getDuration());
			
			courseDao.save(courseDb);
		}	
	}

	@Override
	public void deleteCourse(int id) {
		
		Optional<Course> courseOptional = courseDao.findById(id);
		if (courseOptional.isPresent()) {
			courseDao.delete(courseOptional.get());
		}
	}

	@Override
	public List<CourseDto> findByName(String name) {
		List<Course> courses = courseDao.findByNameContainingIgnoreCase(name);
		List<CourseDto> coursesDto = modelMapper.map(courses, new TypeToken<List<CourseDto>>() {}.getType());
		return coursesDto;
	}

	@Override
	public List<CourseDto> findByDuration(int duration) {
		List<Course> courses = courseDao.findByDuration(duration);
		List<CourseDto> coursesDto = modelMapper.map(courses, new TypeToken<List<CourseDto>>() {}.getType());
		return coursesDto;
	}

}