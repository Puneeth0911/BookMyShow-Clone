
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
public class MovieController {

	@Autowired
	private AdminService adminservice;

	@PostMapping("/admin/createmovie")
//	http://localhost:8085/admin/createmovie
//	{"title":"Interstellar","description":"A team of astronauts travels through a wormhole to find a new home for humanity.","moviecast":"Matthew McConaughey, Anne Hathaway, Jessica Chastain","duration":169,"languages":"English"}
	public ResponseEntity<ResponseStructure<Movie>> createMovie(@RequestBody CreateMovieDto dto) {
		return adminservice.createMovie(dto);
	}

	@GetMapping("/admin/movie/find")
//	http://localhost:8085/admin/movie/find?id=1
	public ResponseEntity<ResponseStructure<Movie>> findMovie(@RequestParam long id) {
		return adminservice.findMovie(id);
	}

	@GetMapping("/admin/movie/all")
//	http://localhost:8085/admin/movie/all
	public ResponseEntity<ResponseStructure<List<Movie>>> getAllMovies() {
		return adminservice.getAllMovies();
	}

	@PutMapping("/admin/movie/update/{id}")
	public Movie updateMovie(@PathVariable long id, @RequestBody CreateMovieDto dto) {
		return adminservice.updateMovie(id, dto);
	}

	@GetMapping("/admin/movie/search")
	public List<Movie> searchMovies(@RequestParam String name) {
		return adminservice.searchMovies(name);
	}

}
