
package com.alpha.customerService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alpha.customerService.dto.BookSeatDto;
import com.alpha.customerService.dto.CreateCustomerDto;
import com.alpha.customerService.dto.MovieDto;
import com.alpha.customerService.dto.ResponseStructure;
import com.alpha.customerService.dto.SeatDto;
import com.alpha.customerService.entity.Customer;
import com.alpha.customerService.entity.Ticket;
import com.alpha.customerService.service.CustomerService;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerservice;

	@PostMapping("/customer/createAccount")
	public ResponseEntity<ResponseStructure<Customer>> create(@RequestBody CreateCustomerDto c) {
		System.out.println("Customer Registered");
		return customerservice.createCustomerAccount(c);
	}

	@GetMapping("/customer/find")
	public ResponseEntity<ResponseStructure<Customer>> findCustomer(@RequestParam long id) {
		return customerservice.findCustomerById(id);
	}

	@DeleteMapping("/customer/delete")
	public ResponseEntity<ResponseStructure<String>> deleteCustomer(@RequestParam long id) {
		return customerservice.deleteCustomer(id);
	}

	@GetMapping("/customer/findMovie")
	public MovieDto findMovie(@RequestParam String title) {
		return customerservice.findMovie(title);
	}

	@GetMapping("/customer/selectseat")
	public ResponseEntity<List<SeatDto>> selectSeat(@RequestParam long showId) {
		List<SeatDto> seats = customerservice.selectSeat(showId);
		return ResponseEntity.ok(seats);
	}

	@PostMapping("/customer/bookseat")
	public ResponseEntity<String> bookSeats(@RequestBody BookSeatDto dto) {
		customerservice.bookSeats(dto.getCustomerId(), dto.getShowId(), dto.getSeats());
		return ResponseEntity.ok("Booking details stored successfully");
	}

//	@PutMapping("/customer/bookticket/pay")
//	public ResponseEntity<Ticket> bookTicketPayment(@RequestParam long ticketId) {
//		Ticket ticket = customerservice.bookTicketPayment(ticketId);
//		return ResponseEntity.ok(ticket);
//	}

	@PostMapping("/customer/confirmticket/pay")
	public ResponseEntity<Ticket> confirmTicketPayment(@RequestParam long customerid) {
		Ticket ticket = customerservice.confirmTicketPayment(customerid);
		return ResponseEntity.ok(ticket);
	}

}
