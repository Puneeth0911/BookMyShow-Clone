package com.alpha.customerService.dto;

public class SeatDto {

    private long id;
    private String seatno;
    private String category;
    private String status;

    
    public SeatDto() {
		super();
	}

	public SeatDto(long id, String seatno, String category, String status) {
		super();
		this.id = id;
		this.seatno = seatno;
		this.category = category;
		this.status = status;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSeatno() {
        return seatno;
    }

    public void setSeatno(String seatno) {
        this.seatno = seatno;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}