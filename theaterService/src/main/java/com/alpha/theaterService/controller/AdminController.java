
package com.alpha.theaterService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.theaterService.dto.CreateMovieDto;
import com.alpha.theaterService.dto.ResponseStructure;
import com.alpha.theaterService.entity.Movie;
import com.alpha.theaterService.service.AdminService;

@RestController
public class AdminController {

	@Autowired
	private AdminService adminservice;

	@PostMapping("/admin/createmovie")
	public ResponseEntity<ResponseStructure<Movie>> createMovie(@RequestBody CreateMovieDto dto) {
		return adminservice.createMovie(dto);
	}
	
	@GetMapping("/admin/movie/find")
	public ResponseEntity<ResponseStructure<Movie>> findMovie(@RequestParam long id) {
		return adminservice.findMovie(id);
	}

	@GetMapping("/admin/movie/all")
	public ResponseEntity<ResponseStructure<List<Movie>>> getAllMovies() {
		return adminservice.getAllMovies();
	}
}
