package com.alpha.adminService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alpha.adminService.dto.MovieDto;
import com.alpha.adminService.dto.UpdateTheaterDto;
import com.alpha.adminService.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PutMapping("/movie/update")
	public ResponseEntity<Object> updateMovie(@RequestBody MovieDto dto) {
		return adminService.updateMovie(dto);
	}

	@GetMapping("/movie/search")
	public List<MovieDto> searchMovies(@RequestParam String name) {
		return adminService.searchMovies(name);
	}

	@PatchMapping("/theater/updateAccount")
	public ResponseEntity<Object> updateTheaterAccount(@RequestParam long theaterid,
			@RequestBody UpdateTheaterDto dto) {
		return adminService.updateTheaterAccount(theaterid, dto);
	}

	@DeleteMapping("/theater/show/delete")
	public ResponseEntity<Object> deleteShow(@RequestParam long theaterid, @RequestParam long showid) {
		return adminService.deleteShow(theaterid, showid);
	}
}
