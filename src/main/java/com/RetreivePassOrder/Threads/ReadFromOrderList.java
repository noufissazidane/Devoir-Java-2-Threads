package com.RetreivePassOrder.Threads;

import java.util.List;

import com.RetreivePassOrder.Order;


public class ReadFromOrderList extends Thread {
	
	   private Object mutex;
	    public List<Order> orders;

	    public ReadFromOrderList(Object mutex, List<Order> orders) {
	        this.mutex = mutex;
	        this.orders = orders;
	    }

	    @Override
	    public void run() {
	        synchronized (mutex) {
	            while (orders.isEmpty()) { // Vérifier si la liste est vide
	                try {
	                    mutex.wait(); // Attendre une notification
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
	            for (Order order : orders) {
	                System.out.println(order.toString());
	            }
	        }
	    }

}
