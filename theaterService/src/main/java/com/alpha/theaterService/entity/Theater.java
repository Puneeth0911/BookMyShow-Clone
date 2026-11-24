package com.alpha.theaterService.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Theater {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String description;
	private String city;
	private long contactno;
	private String mailid;
	private int totalScreens;
	private String status;
//	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
	private Address address;
    @OneToMany(mappedBy = "theater")
    @JsonIgnore
	private List<Show> allPlayingShows;
    
	public Theater(String name, String description, String city, long contactno, String mailid, int totalScreens,
			String status, Address address, List<Show> allPlayingShows) {
		super();
		this.name = name;
		this.description = description;
		this.city = city;
		this.contactno = contactno;
		this.mailid = mailid;
		this.totalScreens = totalScreens;
		this.status = status;
		this.address = address;
		this.allPlayingShows = allPlayingShows;
	}
	public Theater() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public long getContactno() {
		return contactno;
	}
	public void setContactno(long contactno) {
		this.contactno = contactno;
	}
	public String getMailid() {
		return mailid;
	}
	public void setMailid(String mailid) {
		this.mailid = mailid;
	}
	public int getTotalScreens() {
		return totalScreens;
	}
	public void setTotalScreens(int totalScreens) {
		this.totalScreens = totalScreens;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public List<Show> getAllPlayingShows() {
		return allPlayingShows;
	}
	public void setAllPlayingShows(List<Show> allPlayingShows) {
		this.allPlayingShows = allPlayingShows;
	}
	@Override
	public String toString() {
		return "Theater [id=" + id + ", name=" + name + ", description=" + description + ", city=" + city
				+ ", contactno=" + contactno + ", mailid=" + mailid + ", totalScreens=" + totalScreens + ", status="
				+ status + ", address=" + address + ", allPlayingShows=" + allPlayingShows + "]";
	}
    
}
