package com.RetreivePassOrder;

import java.util.Date;

public class Order {
	public int id;
	public Date date;
	public double amount; 
	public int customer_id;
	public String status;
	
	
	
	public Order(int id, Date date, double amount, int customer_id, String status) {
		super();
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.customer_id = customer_id;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
    public String toString() {
        return "Order [id=" + id + ", date=" + date + ", amount=" + amount + ", customerId="+ customer_id + ", status=" + status + "]";
    }
	

}
