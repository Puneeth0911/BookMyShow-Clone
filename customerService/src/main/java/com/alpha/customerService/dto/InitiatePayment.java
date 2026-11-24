package com.alpha.customerService.dto;

public class InitiatePayment {

    private double amount;
    private long customerId;
    private long ticketId;
    private String paymentMode;

    private boolean paymentStatus = false;
	public InitiatePayment() {
		super();
	}

	public InitiatePayment(double amount, long customerId, long ticketId, String paymentMode, boolean paymentStatus) {
		super();
		this.amount = amount;
		this.customerId = customerId;
		this.ticketId = ticketId;
		this.paymentMode = paymentMode;
		this.paymentStatus = paymentStatus;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
   
}