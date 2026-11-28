package com.alpha.customerService.service;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.alpha.customerService.dto.CreateCustomerDto;
import com.alpha.customerService.dto.MovieDto;
import com.alpha.customerService.dto.ResponseStructure;
import com.alpha.customerService.dto.SeatDto;
import com.alpha.customerService.dto.ShowDto;
import com.alpha.customerService.entity.Customer;
import com.alpha.customerService.entity.Ticket;
import com.alpha.customerService.exception.CustomerAlreadyExistWithPhoneException;
import com.alpha.customerService.exception.CustomerNotFoundException;
import com.alpha.customerService.exception.SeatNotAvailableException;
import com.alpha.customerService.exception.SeatNotFoundException;
import com.alpha.customerService.exception.ShowNotFoundException;
import com.alpha.customerService.exception.TicketNotFoundException;
import com.alpha.customerService.repository.CustomerRepository;
import com.alpha.customerService.repository.TicketRepository;
import com.alpha.customerService.dto.ConfirmTicketConsent;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private TicketRepository ticketRepo;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public ResponseEntity<ResponseStructure<Customer>> createCustomerAccount(CreateCustomerDto createCustomerDto) {
		Optional<Customer> customerOptional = customerRepo.findByPhone(createCustomerDto.getPhone());
		System.out.println("Received Phone: " + createCustomerDto.getPhone());
		System.out.println("Is Phone Present: " + customerOptional.isPresent());
		if (customerOptional.isPresent()) {
			throw new CustomerAlreadyExistWithPhoneException();
		}
		Customer newCustomer = new Customer();
		newCustomer.setName(createCustomerDto.getName());
		newCustomer.setMail(createCustomerDto.getMail());
		newCustomer.setPhone(createCustomerDto.getPhone());
		newCustomer.setAge(createCustomerDto.getAge());
		newCustomer.setGender(createCustomerDto.getGender());
		Customer savedCustomer = customerRepo.save(newCustomer);
		ResponseStructure<Customer> responseStructure = new ResponseStructure<Customer>();
		responseStructure.setStatuscode(HttpStatus.CREATED.value());
		responseStructure.setMessage("NEW CUSTOMER CREATED SUCCESSFULLY");
		responseStructure.setData(savedCustomer);
		return new ResponseEntity<ResponseStructure<Customer>>(responseStructure, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Customer>> findCustomerById(long id) {
		Optional<Customer> customerOptional = customerRepo.findById(id);
		ResponseStructure<Customer> responseStructure = new ResponseStructure<Customer>();
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			responseStructure.setStatuscode(HttpStatus.OK.value());
			responseStructure.setMessage("CUSTOMER FOUND SUCCESSFULLY");
			responseStructure.setData(customer);
			return new ResponseEntity<ResponseStructure<Customer>>(responseStructure, HttpStatus.OK);
		}
		responseStructure.setStatuscode(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage("CUSTOMER NOT FOUND");
		responseStructure.setData(null);
		return new ResponseEntity<ResponseStructure<Customer>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseStructure<String>> deleteCustomer(long id) {
		Optional<Customer> customerOptional = customerRepo.findById(id);
		ResponseStructure<String> responseStructure = new ResponseStructure<String>();
		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			customerRepo.delete(customer);
			responseStructure.setStatuscode(HttpStatus.OK.value());
			responseStructure.setMessage("CUSTOMER DELETED SUCCESSFULLY");
			responseStructure.setData("Customer deleted successfully");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		}
		responseStructure.setStatuscode(HttpStatus.NOT_FOUND.value());
		responseStructure.setMessage("CUSTOMER NOT FOUND");
		responseStructure.setData(null);
		return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.NOT_FOUND);
	}

	public MovieDto findMovie(String title) {
		String url = "http://localhost:8085/findMovie?title=" + title;
		MovieDto movie = restTemplate.getForObject(url, MovieDto.class);
		return movie;
	}

	public List<SeatDto> selectSeat(long showId) {
		String url = "http://localhost:8085/shows/seats/available?showId=" + showId;
		try {
			ResponseEntity<List<SeatDto>> response = restTemplate.exchange(url, HttpMethod.GET, null,
					new ParameterizedTypeReference<List<SeatDto>>() {
					});

			return response.getBody();
		} catch (HttpClientErrorException.NotFound e) {

			throw new ShowNotFoundException("SHOW NOT FOUND");
		}
	}

	public void bookSeats(long customerId, long showId, List<String> seats) {

		Customer customer = customerRepo.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		if (seats == null || seats.isEmpty()) {
			throw new RuntimeException("Select at least one seat");
		}

		ResponseEntity<ResponseStructure<ShowDto>> response = restTemplate.exchange(
				"http://localhost:8085/theater/findShow?showid=" + showId, HttpMethod.GET, null,
				new ParameterizedTypeReference<ResponseStructure<ShowDto>>() {
				});

		ShowDto show = response.getBody().getData();

		// FIRST: Lock seats in TheaterService database
		String seatUrl = "http://localhost:8085/shows/seats/lock?showId=" + showId + "&customerId=" + customerId;
		restTemplate.exchange(seatUrl, HttpMethod.POST, new HttpEntity<>(seats),
				new ParameterizedTypeReference<List<SeatDto>>() {
				});

		// SECOND: Store booking details in Redis
		String seatNumbers = String.join(",", seats);
		double totalAmount = show.getPrice() * seats.size();

		String data = show.getTitle() + "|" + show.getDate() + "|" + show.getTime() + "|" + show.getLanguage() + "|"
				+ show.getScreenno() + "|" + seatNumbers + "|" + show.getDate() + "|" + show.getLanguage() + "|"
				+ customer.getName() + "|" + show.getScreenno() + "|" + totalAmount + "|" + customer.getName() + "|"
				+ showId;

		String key = "confirmticket:" + customerId;

		redisTemplate.opsForValue().set(key, data, Duration.ofSeconds(40));
	}

//	public Ticket bookTicketPayment(long ticketId) {
//		Ticket ticket = ticketRepo.findById((int) ticketId)
//				.orElseThrow(() -> new TicketNotFoundException("TICKET NOT FOUND"));
//
//		// Simulated payment
//		boolean paymentSuccess = true;
//		if (paymentSuccess) {
//			ticket.setPaymentStatus(true);
//		}
//		return ticketRepo.save(ticket);
//	}

	public Ticket confirmTicketPayment(long customerid) {
		String key = "confirmticket:" + customerid;
		String data = redisTemplate.opsForValue().get(key);
		if (data == null)
			throw new RuntimeException("Ticket details not found");
		String[] arr = data.split("\\|");
		long showId = Long.parseLong(arr[12]);
		List<String> seats = Arrays.asList(arr[5].split(","));
		String url = "http://localhost:8085/shows/seats/confirm?showId=" + showId + "&customerId=" + customerid;
		restTemplate.postForEntity(url, seats, String.class);
		Ticket t = new Ticket();
		t.setUsername(arr[11]);
		t.setSeatno(arr[5]);
		t.setShowtime(LocalTime.parse(arr[2]));
		t.setMoviename(arr[0]);
		t.setScreenno(Integer.parseInt(arr[9]));
		t.setTotalamount(Double.parseDouble(arr[10]));
		t.setDate(Date.valueOf(arr[1]));
		t.setLanguage(arr[3]);
		t.setBookingDate(LocalDateTime.now());
		t.setPaymentStatus(true);
		Ticket savedTicket = ticketRepo.save(t);
		redisTemplate.delete(key);
		return savedTicket;
	}

	public List<Long> cancelSeatSelectionTransaction(long customerId) {
		String url = "http://localhost:8085/show/seats/cancel?customerId=" + customerId;
		ResponseEntity<List<Long>> response = restTemplate.exchange(url, HttpMethod.DELETE, null,
				new ParameterizedTypeReference<List<Long>>() {
				});
		redisTemplate.delete("confirmticket:" + customerId);
		return response.getBody();
	}
}
