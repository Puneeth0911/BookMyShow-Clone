
package com.alpha.theaterService.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.theaterService.dto.NewShowDto;
import com.alpha.theaterService.dto.ResponseStructure;
import com.alpha.theaterService.dto.UpdateShowDto;
import com.alpha.theaterService.dto.UpdateTheaterDto;
import com.alpha.theaterService.entity.Movie;
import com.alpha.theaterService.entity.Seat;
import com.alpha.theaterService.entity.Show;
import com.alpha.theaterService.entity.Theater;

import com.alpha.theaterService.service.AdminService;
import com.alpha.theaterService.service.TheaterService;

@RestController
public class TheaterController {

	@Autowired
	private TheaterService theaterservice;

	@Autowired
	private AdminService adminservice;

	// CREATE THEATER ACCOUNT
	@PostMapping("/theater/createaccount")
//	http://localhost:8085/theater/createaccount
//	{"name":"INOX Cinemas","description":"Premium Multiplex Theater","city":"Hyderabad","contactno":9123456780,"mailid":"inox@gmail.com","totalScreens":4,"status":"OPEN","address":{"street":"Madhapur Main Road","city":"Hyderabad","state":"Telangana","pincode":500081,"country":"India"}}
	public ResponseEntity<ResponseStructure<Theater>> createTheaterAccount(@RequestBody Theater t) {
		System.out.println("Creating Theater Account");
		return theaterservice.createTheaterAccount(t);
	}

	// FIND THEATER
	@GetMapping("/theater/find")
//	http://localhost:8085/theater/find?id=52
	public ResponseEntity<ResponseStructure<Theater>> findTheater(@RequestParam long id) {
		System.out.println("Finding Theater with id " + id);
		return theaterservice.findTheater(id);
	}

	// DELETE THEATER
	@DeleteMapping("/theater/delete")
//	http://localhost:8085/theater/delete?id=102
	public ResponseEntity<ResponseStructure<String>> deleteTheater(@RequestParam long id) {
		return theaterservice.deleteTheater(id);
	}

	// GET ALL MOVIES
	@GetMapping("/theater/movie/all")
//	http://localhost:8085/theater/movie/all
	public ResponseEntity<ResponseStructure<List<Movie>>> getAllMovies() {

		return adminservice.getAllMovies();
	}

	// CREATE NEW SHOW
	@PostMapping("/theater/createNewShow")
//	http://localhost:8085/theater/createNewShow?theaterid=152
//	{"movieId":1,"title":"RRR","description":"Action Movie","duration":2.5,"language":"Telugu","screennumber":2,"date":"2026-11-25","time":"18:30:00","price":200.0,"capacity":30}
	public ResponseEntity<ResponseStructure<Show>> createNewShow(@RequestParam long theaterid,
			@RequestBody NewShowDto dto) {
		System.out.println("Creating New Show");
		return theaterservice.createNewShow(theaterid, dto);
	}

	// GET ALL PLAYING SHOWS
	@GetMapping("/theater/findAllPlayingShows")
//	http://localhost:8085/theater/findAllPlayingShows?theaterid=152
	public ResponseEntity<ResponseStructure<List<Show>>> getAllPlayingShows(@RequestParam long theaterid) {
		return theaterservice.getAllPlayingShows(theaterid);
	}

	// FIND SHOW BY ID
	@GetMapping("/theater/findShow")
//	http://localhost:8085/theater/findShow?showid=7
	public ResponseEntity<ResponseStructure<Show>> findShowById(@RequestParam long showid) {
		return theaterservice.findShowById(showid);
	}

	// UPDATE SHOW
	@PutMapping("/theater/updateShow")
	public ResponseEntity<ResponseStructure<Show>> updateShow(@RequestParam long showid,
			@RequestBody UpdateShowDto dto) {

		return theaterservice.updateShow(showid, dto);
	}

	// FIND MOVIE BY TITLE
	@GetMapping("/findMovie")
	public Movie findMovie(@RequestParam String title) {

		return theaterservice.findMovie(title);
	}

	// BUY TICKETS - FIND AVAILABLE SHOWS
	@GetMapping("/customer/buytickets")
	public ResponseEntity<ResponseStructure<List<Show>>> buyTickets(@RequestParam String moviename,
			@RequestParam String language, @RequestParam Date date) {

		return theaterservice.buyTickets(moviename, language, date);
	}

	// GET AVAILABLE SEATS (DATABASE + REDIS CHECK)
	@GetMapping({ "/shows/seats/available", "/availableseats" })
	public ResponseEntity<List<Seat>> getAvailableSeats(@RequestParam long showId) {

		List<Seat> seats = theaterservice.getAvailableSeats(showId);

		return ResponseEntity.ok(seats);
	}

	// LOCK SELECTED SEATS
	@PostMapping("/shows/seats/lock")
	public ResponseEntity<List<Seat>> lockSeats(@RequestParam long showId, @RequestParam long customerId,
			@RequestBody List<String> seatNumbers) {

		List<Seat> seats = theaterservice.lockSeats(showId, customerId, seatNumbers);
		return ResponseEntity.ok(seats);
	}

	@PostMapping("/customer/buyticket")
	public ResponseEntity<String> buyTicket(@RequestParam long id, @RequestParam long sid,
			@RequestParam String seatno) {

		String response = theaterservice.buyTicket(id, sid, seatno);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/shows/seats/confirm")
	public ResponseEntity<String> confirmSeats(@RequestParam long showId, @RequestParam long customerId,
			@RequestBody List<String> seatNumbers) {

		String result = theaterservice.confirmSeats(showId, customerId, seatNumbers);
		return ResponseEntity.ok(result);
	}

	@PatchMapping("/theater/updateAccount")
	public ResponseEntity<ResponseStructure<Theater>> updateTheaterAccount(@RequestParam long theaterid,
			@RequestBody UpdateTheaterDto dto) {
		return theaterservice.updateTheaterAccount(theaterid, dto);
	}

	@DeleteMapping("/theater/show/delete")
	public ResponseEntity<Object> deleteShow(@RequestParam long theaterid, @RequestParam long showid) {
		return theaterservice.deleteShow(theaterid, showid);
	}

	@GetMapping("/theater/show/findByDate")
	public List<Show> findByDate(@RequestParam long theaterid, @RequestParam String date) {
		return theaterservice.findByDate(theaterid, date);
	}

	@GetMapping("/theater/show/findByMovie")
	public List<Show> findShowsByMovie(@RequestParam long theaterid, @RequestParam String title) {
		return theaterservice.findShowsByMovie(theaterid, title);
	}

	@GetMapping("/theater/show/findByScreen")
	public List<Show> findShowsByScreen(@RequestParam long theaterid, @RequestParam int screennumber) {
		return theaterservice.findShowsByScreen(theaterid, screennumber);
	}

	@GetMapping("/theater/show/details")
	public Show getShowDetails(@RequestParam long theaterid, @RequestParam long showid) {
		return theaterservice.getShowDetails(theaterid, showid);
	}

	@DeleteMapping("/show/seats/cancel")
	public ResponseEntity<List<Long>> cancelSeatSelection(@RequestParam long customerId) {
		List<Long> showIds = theaterservice.cancelSeatSelection(customerId);
		return ResponseEntity.ok(showIds);
	}

}
