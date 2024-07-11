package it.corso.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.corso.model.Course;

public interface CourseDao extends CrudRepository<Course, Integer> {
	
	boolean existsById(int id);
	List<Course> findByNameContainingIgnoreCase(String name);
	List<Course> findByDuration(int duration);
	
}