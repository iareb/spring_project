package it.corso.dto;

public class CourseDto {
	
	private int id;
	private String name;
	private String shortDescription;
	private String fullDescription;
	private int duration;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}
	
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public String getFullDescription() {
		return fullDescription;
	}
	
	public void setFullDescription(String fullDescription) {
		this.fullDescription = fullDescription;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
	
}