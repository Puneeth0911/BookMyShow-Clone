package com.alpha.theaterService.dto;

public class CreateMovieDto {
	private String title;
	private String description;
	private String moviecast;
	private double duration;
	private String languages;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMoviecast() {
		return moviecast;
	}
	public void setMoviecast(String moviecast) {
		this.moviecast = moviecast;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public String getLanguages() {
		return languages;
	}
	public void setLanguages(String languages) {
		this.languages = languages;
	}
	
}
