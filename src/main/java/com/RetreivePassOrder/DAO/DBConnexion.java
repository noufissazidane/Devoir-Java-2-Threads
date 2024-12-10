package com.RetreivePassOrder.DAO;

import java.sql.*;

public class DBConnexion {
	private Connection connexion;
	
	public DBConnexion() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.connexion =DriverManager.getConnection("jdbc:mysql://localhost:3306/ordersdatabase", "root", "");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void closeConnexion() {
		try {
			connexion.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public Connection getConnexion() {
		return connexion;
	}

	public void setConnexion(Connection connexion) {
		this.connexion = connexion;
	}
	
	public boolean selectFromDB(int id) {
		String query = "select * from customer where id = " + id;
		try {
			ResultSet result = (ResultSet) connexion.createStatement().executeQuery(query);;
			if(result.next()) {
				return true;
			} 
			else return false; 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void insert2order(int id ,Date date, double amount, int custid, String status) {
        String query = "insert into orders(id,date,amount,customer_id,status) values (?, ?, ?, ?, ?)";

        try(PreparedStatement statement= connexion.prepareStatement(query)){
            statement.setInt(1, id);
            statement.setDate(2, date);
            statement.setDouble(3, amount);
            statement.setInt(4, custid);
            statement.setString(5, status);
            int result =statement.executeUpdate();
            if(result >0){
                System.out.println("Insertion has been successful");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}

	}

