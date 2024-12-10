package com.RetreivePassOrder.Threads;

import com.RetreivePassOrder.Order;
import com.RetreivePassOrder.DAO.DBConnexion;
import com.google.gson.*;
import com.google.gson.stream.JsonWriter;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertIntoOrderList extends Thread {
   DBConnexion db;
    private Object mutex;
    public List<Order> orders;
    public InsertIntoOrderList( Object mutex , List<Order> orders ) {
        db = new DBConnexion();
        this.mutex = mutex;
        this.orders = orders;

    }

    public List<Order> getOrders() {
        return orders;
    }

    public  void run() {
        while (true) {
            synchronized (mutex) {
                try {
                    JsonArray jsonArray = JsonParser.parseReader(new FileReader("/Users/noufissazidane/Desktop/devoirlibre/RetreivePassOrder/RetreivePassOrder/src/Data/input.json")).getAsJsonArray();
                    JsonWriter jsonWriter = new JsonWriter(new FileWriter("/Users/noufissazidane/Desktop/devoirlibre/RetreivePassOrder/RetreivePassOrder/src/Data/output.json"));
                    JsonWriter jsonWriterError = new JsonWriter(new FileWriter("/Users/noufissazidane/Desktop/devoirlibre/RetreivePassOrder/RetreivePassOrder/src/Data/error.json"));
                    jsonWriter.beginArray();
                    jsonWriter.setIndent("  ");
                    jsonWriterError.beginArray();
                    jsonWriterError.setIndent("  ");
                    for (JsonElement element : jsonArray) {
                        JsonObject jsonObject = element.getAsJsonObject();
                        Order order = fromjsonToOrder(jsonObject);
                        int id = order.getId();
                        Date date = order.getDate();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        double amount = order.getAmount();
                        order.setCustomer_id(jsonObject.get("customer_id").getAsInt());
                        int customerId = order.getCustomer_id();
                        String status = order.getStatus();

                        if (verifyexistenceindb(jsonObject)) {
                            orders.add(order);
                            db.insert2order(id, sqlDate, amount, customerId, status);
                            jsonWriter.beginObject();
                            jsonWriter.name("id").value(id);
                            jsonWriter.name("date").value(sqlDate.toString());
                            jsonWriter.name("amount").value(amount);
                            jsonWriter.name("customer_id").value(customerId);
                            jsonWriter.name("status").value(status);
                            jsonWriter.endObject();

                        } else {
                            jsonWriterError.beginObject();
                            jsonWriterError.name("id").value(id);
                            jsonWriterError.name("date").value(sqlDate.toString());
                            jsonWriterError.name("amount").value(amount);
                            jsonWriterError.name("customer_id").value(customerId);
                            jsonWriterError.name("status").value(status);
                            jsonWriterError.endObject();


                        }
                    }
                    jsonWriter.endArray();
                    jsonWriter.close();
                    jsonWriterError.endArray();
                    jsonWriterError.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
               mutex.notify();
            }
            try {
                Thread.sleep(60*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

        private Boolean verifyexistenceindb (JsonObject jsonObject){
            int customer_id = jsonObject.get("customer_id").getAsInt();
            return db.selectFromDB(customer_id);

        }
        private Order fromjsonToOrder (JsonObject jsonObject){
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Order order = gson.fromJson(jsonObject, Order.class);
            return order;
        }


}


