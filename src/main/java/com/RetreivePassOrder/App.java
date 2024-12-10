package com.RetreivePassOrder;

import java.util.ArrayList;
import java.util.List;

import com.RetreivePassOrder.Threads.InsertIntoOrderList;
import com.RetreivePassOrder.Threads.ReadFromOrderList;

public class App {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );
        Object mutex= new Object();
        List<Order> Orders = new ArrayList<>();
        InsertIntoOrderList thread = new InsertIntoOrderList(mutex, Orders);
        ReadFromOrderList thread2 = new ReadFromOrderList(mutex, Orders);
        thread.start();
        thread2.start();
    }
}
