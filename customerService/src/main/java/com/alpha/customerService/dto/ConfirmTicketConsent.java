package com.alpha.customerService.dto;

import java.sql.Date;
import java.time.LocalTime;

public class ConfirmTicketConsent {
	private String moviename;
	private Date date;
	private LocalTime time;
	private String language;
	private int noOfSeats;
	private String seatnoAndCategory;
	private Double price;
	private Double convenienceFees;
	private String theaterName;
	private int screennumber;
	private Double totalPrice;
	private String customerName;
	private Long customerMobileNo;
	public String getMoviename() {
		return moviename;
	}
	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getNoOfSeats() {
		return noOfSeats;
	}
	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
	public String getSeatnoAndCategory() {
		return seatnoAndCategory;
	}
	public void setSeatnoAndCategory(String seatnoAndCategory) {
		this.seatnoAndCategory = seatnoAndCategory;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getConvenienceFees() {
		return convenienceFees;
	}
	public void setConvenienceFees(Double convenienceFees) {
		this.convenienceFees = convenienceFees;
	}
	public String getTheaterName() {
		return theaterName;
	}
	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}
	public int getScreennumber() {
		return screennumber;
	}
	public void setScreennumber(int screennumber) {
		this.screennumber = screennumber;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Long getCustomerMobileNo() {
		return customerMobileNo;
	}
	public void setCustomerMobileNo(Long customerMobileNo) {
		this.customerMobileNo = customerMobileNo;
	}
	
}
