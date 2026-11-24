package com.alpha.customerService.dto;

import java.util.List;

public class BookSeatDto {

	    private long customerId;
	    private long showId;
	    private List<String> seats;
	    
	    
		public BookSeatDto() {
			super();
		}
		public BookSeatDto(long customerId, long showId, List<String> seats) {
			super();
			this.customerId = customerId;
			this.showId = showId;
			this.seats = seats;
		}
		public long getCustomerId() {
			return customerId;
		}
		public void setCustomerId(long customerId) {
			this.customerId = customerId;
		}
		public long getShowId() {
			return showId;
		}
		public void setShowId(long showId) {
			this.showId = showId;
		}
		public List<String> getSeats() {
			return seats;
		}
		public void setSeats(List<String> seats) {
			this.seats = seats;
		}
}