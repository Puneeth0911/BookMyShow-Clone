package com.alpha.adminService.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.alpha.adminService.dto.MovieDto;
import com.alpha.adminService.dto.UpdateTheaterDto;

@Service
public class AdminService {

	@Autowired
	private RestTemplate restTemplate;

	private final String BASE_URL = "http://localhost:8085/admin/movie";
	private final String THEATER_URL = "http://localhost:8085/theater";

	public ResponseEntity<Object> updateMovie(MovieDto dto) {
		String url = BASE_URL + "/update/" + dto.getId();
		HttpEntity<MovieDto> request = new HttpEntity<>(dto);
		try {
			ResponseEntity<MovieDto> response = restTemplate.exchange(url, HttpMethod.PUT, request, MovieDto.class);
			return new ResponseEntity<Object>(response.getBody(), response.getStatusCode());
		} catch (HttpStatusCodeException e) {
			return new ResponseEntity<Object>("Movie not found", e.getStatusCode());
		}
	}

	public List<MovieDto> searchMovies(String name) {
		String url = BASE_URL + "/search?name=" + name;
		ResponseEntity<MovieDto[]> response = restTemplate.getForEntity(url, MovieDto[].class);
		return Arrays.asList(response.getBody());
	}

	// UPDATE THEATER ACCOUNT
	public ResponseEntity<Object> updateTheaterAccount(long theaterid, UpdateTheaterDto dto) {
		String url = THEATER_URL + "/updateAccount?theaterid=" + theaterid;
		HttpEntity<UpdateTheaterDto> request = new HttpEntity<>(dto);
		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PATCH, request, Object.class);
		return response;
	}

	public ResponseEntity<Object> deleteShow(long theaterid, long showid) {
		String url = THEATER_URL + "/show/delete?theaterid=" + theaterid + "&showid=" + showid;
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
		return new ResponseEntity<Object>(response.getBody(), response.getStatusCode());
	}
}
