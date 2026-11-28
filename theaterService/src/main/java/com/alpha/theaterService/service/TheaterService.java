package com.alpha.theaterService.service;

import java.sql.Date;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.alpha.theaterService.dto.NewShowDto;
import com.alpha.theaterService.dto.ResponseStructure;
import com.alpha.theaterService.dto.UpdateShowDto;
import com.alpha.theaterService.entity.Movie;
import com.alpha.theaterService.entity.Seat;
import com.alpha.theaterService.entity.Show;
import com.alpha.theaterService.entity.Theater;
import com.alpha.theaterService.exception.MovieNotFoundException;
import com.alpha.theaterService.exception.SeatAlreadySelectedException;
import com.alpha.theaterService.exception.SeatNotAvailableException;
import com.alpha.theaterService.exception.SeatNotFoundException;
import com.alpha.theaterService.exception.ShowNotFoundException;
import com.alpha.theaterService.exception.TheaterNotFoundException;
import com.alpha.theaterService.repository.MovieRepository;
import com.alpha.theaterService.repository.SeatRepository;
import com.alpha.theaterService.repository.ShowRepository;
import com.alpha.theaterService.repository.TheaterRepository;
import jakarta.transaction.Transactional;
import com.alpha.theaterService.dto.UpdateTheaterDto;

@Service
public class TheaterService {

	@Autowired
	private TheaterRepository theaterrepository;

	@Autowired
	private ShowRepository showrepository;

	@Autowired
	private MovieRepository movierepository;

	@Autowired
	private SeatRepository seatRepo;

//	 CREATE THEATER
	public ResponseEntity<ResponseStructure<Theater>> createTheaterAccount(Theater t) {
		Theater theatersaved = theaterrepository.save(t);
		ResponseStructure<Theater> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.CREATED.value());
		rs.setMessage("Theater created successfully");
		rs.setData(theatersaved);
		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

//	 FIND THEATER
	public ResponseEntity<ResponseStructure<Theater>> findTheater(long id) {
		Theater theater = theaterrepository.findById(id).orElseThrow(() -> new TheaterNotFoundException());
		ResponseStructure<Theater> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Theater Found");
		rs.setData(theater);
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

//	 DELETE THEATER
	public ResponseEntity<ResponseStructure<String>> deleteTheater(long id) {
		Theater theater = theaterrepository.findById(id).orElseThrow(() -> new TheaterNotFoundException());
		theaterrepository.delete(theater);
		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Theater Deleted Successfully");
		rs.setData("Theater deleted successfully");
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

//	 CREATE NEW SHOW
	public ResponseEntity<ResponseStructure<Show>> createNewShow(long theaterid, NewShowDto dto) {
		Theater theater = theaterrepository.findById(theaterid).orElseThrow(() -> new TheaterNotFoundException());
		Movie movie = movierepository.findById(dto.getMovieId()).orElseThrow(() -> new MovieNotFoundException());
		boolean exists = showrepository.existsByTheater_IdAndDateAndTimeAndScreennumber(theaterid, dto.getDate(),
				dto.getTime(), dto.getScreennumber());
		if (exists) {
			ResponseStructure<Show> rs = new ResponseStructure<>();
			rs.setStatuscode(HttpStatus.CONFLICT.value());
			rs.setMessage("Show Already Exists");
			rs.setData(null);
			return new ResponseEntity<>(rs, HttpStatus.CONFLICT);
		}
		Show sh = new Show();
		sh.setMovie(movie);
		sh.setTheater(theater);
		sh.setTitle(movie.getTitle());
		sh.setDescription(dto.getDescription());
		sh.setDuration(dto.getDuration());
		sh.setLanguage(dto.getLanguage());
		sh.setScreennumber(dto.getScreennumber());
		sh.setDate(dto.getDate());
		sh.setTime(dto.getTime());
		sh.setPrice(dto.getPrice());
		List<Seat> seats = generateSeats(dto.getCapacity(), sh);
		sh.setSeats(seats);
		Show savedShow = showrepository.save(sh);
		ResponseStructure<Show> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.CREATED.value());
		rs.setMessage("New Show Created Successfully");
		rs.setData(savedShow);
		return new ResponseEntity<>(rs, HttpStatus.CREATED);
	}

//	 GENERATE SEATS
	public List<Seat> generateSeats(int capacity, Show show) {
		if (capacity <= 0) {
			throw new IllegalArgumentException("Capacity must be positive");
		}
		List<Seat> seats = new ArrayList<>();
		String[] categories = { "SILVER", "GOLD", "DIAMOND" };
		int baseCapacity = capacity / 3;
		int remainder = capacity % 3;
		char row = 'A';
		for (int i = 0; i < 3; i++) {
			int categoryCapacity = baseCapacity;
			if (i < remainder) {
				categoryCapacity++;
			}
			int seatNumber = 1;
			for (int j = 1; j <= categoryCapacity; j++) {
				Seat seat = new Seat();
				seat.setSeatno(row + String.valueOf(seatNumber));
				seat.setCategory(categories[i]);
				seat.setStatus("AVAILABLE");
				seat.setShow(show);
				seats.add(seat);
				seatNumber++;
				if (seatNumber > 10) {
					seatNumber = 1;
					row++;
				}
			}
			if (seatNumber != 1) {
				row++;
			}
		}
		return seats;
	}

//	 GET ALL PLAYING SHOWS
	public ResponseEntity<ResponseStructure<List<Show>>> getAllPlayingShows(long theaterid) {
		Theater theater = theaterrepository.findById(theaterid).orElseThrow(() -> new TheaterNotFoundException());
		List<Show> shows = theater.getAllPlayingShows();
		ResponseStructure<List<Show>> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("All Playing Shows");
		rs.setData(shows);
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	// FIND SHOW BY ID
	public ResponseEntity<ResponseStructure<Show>> findShowById(long showid) {
		Show show = showrepository.findById(showid).orElseThrow(() -> new ShowNotFoundException());
		ResponseStructure<Show> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Show found successfully");
		rs.setData(show);
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	// UPDATE SHOW
	public ResponseEntity<ResponseStructure<Show>> updateShow(long showid, UpdateShowDto dto) {
		Show show = showrepository.findById(showid).orElseThrow(() -> new ShowNotFoundException());
		show.setDate(dto.getDate());
		show.setTime(dto.getTime());
		show.setScreennumber(dto.getScreennumber());
		Show updatedShow = showrepository.save(show);
		ResponseStructure<Show> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Show updated successfully");
		rs.setData(updatedShow);
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	// FIND MOVIE BY TITLE
	public Movie findMovie(String title) {
		return movierepository.findByTitle(title).orElseThrow(() -> new MovieNotFoundException());
	}

	// BUY TICKETS - FIND AVAILABLE SHOWS
	public ResponseEntity<ResponseStructure<List<Show>>> buyTickets(String movie, String language, Date date) {
		List<Show> shows = showrepository.findByTitleContainingIgnoreCaseAndLanguageIgnoreCaseAndDate(movie, language,
				date);
		ResponseStructure<List<Show>> response = new ResponseStructure<>();
		response.setStatuscode(HttpStatus.OK.value());
		response.setMessage("AVAILABLE SHOWS");
		response.setData(shows);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

//	 FIND AVAILABLE SEATS
	public List<Seat> findAvailableSeats(long showId) {
		if (!showrepository.existsById(showId)) {
			throw new ShowNotFoundException();
		}
		return seatRepo.findByShow_IdAndStatus(showId, "AVAILABLE");
	}

//	 LOCK SELECTED SEATS

	@Transactional
	public List<Seat> lockSeats(long showId, long customerId, List<String> seatNumbers) {

		if (!showrepository.existsById(showId)) {
			throw new ShowNotFoundException();
		}

		List<String> lockedKeys = new ArrayList<>();
		List<String> customerSeats = new ArrayList<>();

		for (int i = 0; i < seatNumbers.size(); i++) {
			seatNumbers.set(i, seatNumbers.get(i).toUpperCase());
		}

		List<Seat> seats = seatRepo.findByShow_IdAndSeatnoIn(showId, seatNumbers);

		if (seats.size() != seatNumbers.size()) {
			throw new SeatNotFoundException("SELECTED SEATS NOT FOUND");
		}

		for (Seat seat : seats) {
			if (!"AVAILABLE".equalsIgnoreCase(seat.getStatus())) {
				throw new SeatAlreadySelectedException();
			}
		}

		String customerKey = "customerlocks:" + customerId;

		for (Seat seat : seats) {

			String key = "lockseat:" + showId + ":" + seat.getSeatno();

			Boolean locked = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(customerId),
					Duration.ofSeconds(40));

			if (!Boolean.TRUE.equals(locked)) {

				for (String lockedKey : lockedKeys) {
					redisTemplate.delete(lockedKey);
				}

				for (String customerSeat : customerSeats) {
					redisTemplate.opsForSet().remove(customerKey, customerSeat);
				}

				throw new SeatAlreadySelectedException();
			}

			lockedKeys.add(key);

			String seatData = showId + ":" + seat.getSeatno();

			redisTemplate.opsForSet().add(customerKey, seatData);

			customerSeats.add(seatData);
		}

		redisTemplate.expire(customerKey, Duration.ofSeconds(40));

		return seats;
	}

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public List<Seat> getAvailableSeats(long showId) {
		if (!showrepository.existsById(showId)) {
			throw new ShowNotFoundException();
		}
		List<Seat> seats = seatRepo.findByShow_IdAndStatus(showId, "AVAILABLE");
		List<Seat> availableSeats = new ArrayList<>();
		for (Seat seat : seats) {
			String key = "lockseat:" + showId + ":" + seat.getSeatno();
			Boolean isLocked = redisTemplate.hasKey(key);
			if (!Boolean.TRUE.equals(isLocked)) {
				availableSeats.add(seat);
			}
		}
		return availableSeats;
	}

	public String buyTicket(long customerId, long showId, String seatNo) {
		if (!showrepository.existsById(showId)) {
			throw new ShowNotFoundException();
		}
		List<Seat> seats = seatRepo.findByShow_IdAndSeatnoIn(showId, Collections.singletonList(seatNo.toUpperCase()));
		if (seats.isEmpty()) {
			throw new RuntimeException("Seat not found in this show");
		}
		Seat seat = seats.get(0);
		if (!"AVAILABLE".equalsIgnoreCase(seat.getStatus())) {
			throw new SeatAlreadySelectedException();
		}
		String key = "lockseat:" + showId + ":" + seatNo.toUpperCase();
		Boolean locked = redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(customerId),
				Duration.ofSeconds(40));
		if (!Boolean.TRUE.equals(locked)) {
			throw new SeatAlreadySelectedException();
		}
		return "Seat " + seatNo.toUpperCase() + " locked successfully for customer " + customerId
				+ ". Proceed to payment within 20 seconds.";
	}

	@Transactional
	public String confirmSeats(long showId, long customerId, List<String> seatNumbers) {
	    List<Seat> seats = seatRepo.findByShow_IdAndSeatnoIn(showId, seatNumbers);
	    if (seats.size() != seatNumbers.size()) {
	        throw new SeatNotFoundException("SELECTED SEATS NOT FOUND");
	    }
	    for (Seat seat : seats) {
	        if (!seat.getStatus().equalsIgnoreCase("AVAILABLE")) {
	            throw new SeatAlreadySelectedException();
	        }
	        String key = "lockseat:" + showId + ":" + seat.getSeatno();
	        String owner = redisTemplate.opsForValue().get(key);
	        if (!String.valueOf(customerId).equals(owner)) {
	            throw new SeatAlreadySelectedException();
	        }
	    }
	    for (Seat seat : seats) {
	        seat.setStatus("BOOKED");
	    }
	    seatRepo.saveAll(seats);
	    String customerKey = "customerlocks:" + customerId;
	    for (Seat seat : seats) {
	        String key = "lockseat:" + showId + ":" + seat.getSeatno();
	        String owner = redisTemplate.opsForValue().get(key);
	        if (String.valueOf(customerId).equals(owner)) {
	            redisTemplate.delete(key);
	        }
	        redisTemplate.opsForSet().remove(customerKey, showId + ":" + seat.getSeatno());
	    }
	    return "Seats confirmed successfully";
	}

//	 UPDATE THEATER ACCOUNT
	public ResponseEntity<ResponseStructure<Theater>> updateTheaterAccount(long theaterid, UpdateTheaterDto dto) {
		Theater theater = theaterrepository.findById(theaterid).orElseThrow(() -> new TheaterNotFoundException());
		if (dto.getName() != null) {
			theater.setName(dto.getName());
		}
		if (dto.getDescription() != null) {
			theater.setDescription(dto.getDescription());
		}
		if (dto.getCity() != null) {
			theater.setCity(dto.getCity());
		}
		if (dto.getContactno() != null) {
			theater.setContactno(dto.getContactno());
		}
		if (dto.getMailid() != null) {
			theater.setMailid(dto.getMailid());
		}
		Theater updatedTheater = theaterrepository.save(theater);
		ResponseStructure<Theater> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Theater account updated successfully");
		rs.setData(updatedTheater);
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	public ResponseEntity<Object> deleteShow(long theaterid, long showid) {
		System.out.println("Theater ID: " + theaterid);
		System.out.println("Show ID: " + showid);
		System.out.println("Total Shows: " + showrepository.count());
		showrepository.findAll().forEach(s -> System.out.println("Available Show ID: " + s.getId()));
		Theater theater = theaterrepository.findById(theaterid).orElseThrow(() -> new TheaterNotFoundException());
		Show show = showrepository.findById(showid).orElseThrow(() -> new ShowNotFoundException());
		if (show.getTheater().getId() != theaterid) {
			throw new RuntimeException("Show does not belong to this theater");
		}
		showrepository.delete(show);
		return ResponseEntity.ok("Show deleted successfully");
	}

	public List<Show> findByDate(long theaterid, String date) {
		return showrepository.findByTheater_IdAndDate(theaterid, Date.valueOf(date));
	}

	public List<Show> findShowsByMovie(long theaterid, String title) {
		return showrepository.findByTheater_IdAndTitleContainingIgnoreCase(theaterid, title);
	}

	public List<Show> findShowsByScreen(long theaterid, int screennumber) {
		return showrepository.findByTheater_IdAndScreennumber(theaterid, screennumber);
	}

	public Show getShowDetails(long theaterid, long showid) {
		theaterrepository.findById(theaterid).orElseThrow(() -> new TheaterNotFoundException());
		Show show = showrepository.findById(showid).orElseThrow(() -> new ShowNotFoundException());
		if (show.getTheater().getId() != theaterid) {
			throw new RuntimeException("Show does not belong to this theater");
		}
		return show;
	}

	public List<Long> cancelSeatSelection(long customerId) {
		String customerKey = "customerlocks:" + customerId;
		Set<String> lockedSeats = redisTemplate.opsForSet().members(customerKey);
		List<Long> showIds = new ArrayList<>();
		if (lockedSeats == null || lockedSeats.isEmpty()) {
			return showIds;
		}
		for (String seat : lockedSeats) {
			String[] arr = seat.split(":");
			long showId = Long.parseLong(arr[0]);
			String seatNo = arr[1];
			String key = "lockseat:" + showId + ":" + seatNo;
			// Check whether the lock belongs to this customer
			String owner = redisTemplate.opsForValue().get(key);
			if (String.valueOf(customerId).equals(owner)) {
				redisTemplate.delete(key);
				if (!showIds.contains(showId)) {
					showIds.add(showId);
				}
			}
			// Remove seat from customer mapping
			redisTemplate.opsForSet().remove(customerKey, seat);
		}
		redisTemplate.delete(customerKey);
		return showIds;
	}
}
