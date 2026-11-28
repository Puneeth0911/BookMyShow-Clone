package com.alpha.theaterService.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.alpha.theaterService.dto.CreateMovieDto;
import com.alpha.theaterService.dto.ResponseStructure;
import com.alpha.theaterService.entity.Movie;
import com.alpha.theaterService.exception.MovieNotFoundException;
import com.alpha.theaterService.repository.MovieRepository;

@Service
public class AdminService {

	@Autowired
	private MovieRepository movierepository;

	public ResponseEntity<ResponseStructure<Movie>> createMovie(CreateMovieDto dto) {
		Movie movie = new Movie(dto.getTitle(), dto.getDescription(), dto.getMoviecast(), dto.getDuration(),
				dto.getLanguages());
		Movie savedMovie = movierepository.save(movie);
		ResponseStructure<Movie> rs = new ResponseStructure<Movie>();
		rs.setStatuscode(HttpStatus.CREATED.value());
		rs.setMessage("Movie created successfully");
		rs.setData(savedMovie);
		return new ResponseEntity<ResponseStructure<Movie>>(rs, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Movie>> findMovie(long id) {
		Movie movie = movierepository.findById(id).orElse(null);
		ResponseStructure<Movie> rs = new ResponseStructure<Movie>();
		if (movie != null) {
			rs.setStatuscode(HttpStatus.OK.value());
			rs.setMessage("Movie found successfully");
			rs.setData(movie);
			return new ResponseEntity<ResponseStructure<Movie>>(rs, HttpStatus.OK);
		}
		rs.setStatuscode(HttpStatus.NOT_FOUND.value());
		rs.setMessage("Movie not found");
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<Movie>>(rs, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseStructure<List<Movie>>> getAllMovies() {
		List<Movie> movies = movierepository.findAll();
		ResponseStructure<List<Movie>> rs = new ResponseStructure<List<Movie>>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Movies fetched successfully");
		rs.setData(movies);
		return new ResponseEntity<ResponseStructure<List<Movie>>>(rs, HttpStatus.OK);
	}

	public Movie updateMovie(long id, CreateMovieDto dto) {
		Movie movie = movierepository.findById(id).orElse(null);
		if (movie == null) {
			throw new MovieNotFoundException();
		}
		movie.setTitle(dto.getTitle());
		movie.setDescription(dto.getDescription());
		movie.setCast(dto.getMoviecast());
		movie.setDuration(dto.getDuration());
		movie.setLanguages(dto.getLanguages());
		return movierepository.save(movie);
	}

	public List<Movie> searchMovies(String name) {
		return movierepository.findByTitleContainingIgnoreCase(name);
	}

}
