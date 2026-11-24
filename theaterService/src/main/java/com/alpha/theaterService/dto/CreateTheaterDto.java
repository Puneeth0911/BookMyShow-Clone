package com.alpha.theaterService.dto;

import com.alpha.theaterService.entity.Address;

public class CreateTheaterDto {
	private String name;
    private String description;
    private String city;
    private long contactnumber;
    private String mailid;
    private int totalscreens;
    private Address address;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public long getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(long contactnumber) {
		this.contactnumber = contactnumber;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public int getTotalscreens() {
		return totalscreens;
	}
	public void setTotalscreens(int totalscreens) {
		this.totalscreens = totalscreens;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
    
}
