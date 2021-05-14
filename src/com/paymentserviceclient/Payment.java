package com.paymentserviceclient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class Payment {
	
    private Connection connect()
    {
        Connection con = null;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

 

            //Provide the correct details: DBServer/DBName, user-name, password
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/payment_db", "root", "");
        }
        catch (Exception e)
        {e.printStackTrace();}
        return con;
    } 

 

    //Read Payment
    public String getPayment() {
        String result = null;
        try
        {
            Connection con = connect();

 

            if (con == null)
            {
              
                return "Error while Connecting to the Database for Inserting."; 
            }

 
         // Prepare the HTML table to be displayed
         			result = "<table  class='table table-dark table-striped'><tr>"
         					+ "<th>Payment ID</th>"
         					+"<th>Customer ID</th>"
         					+ "<th>Payment Date</th>"
         					+ "<th>Payment Amount</th>"
         					+ "<th>Tax Rate</th>"
         					+ "<th>Description</th>"
         					+ "<th>Card Type</th>"
         					+ "<th>Card Number</th>"
         					+ "<th>Card CVN</th>"
         					+ "<th>Update</th>"
         					+ "<th>Delete</th></tr>";


            // create a prepared statement
            String query = " select * from payment";

 


            PreparedStatement preparedStmt = con.prepareStatement(query);
            ResultSet rs = preparedStmt.executeQuery();
            
            JsonArray resultArray = new JsonArray();
            while(rs.next()) {
                JsonObject jsonRow = new JsonObject();
                
                int payment_id=rs.getInt("payment_id") ;
                int customer_id= rs.getInt("customer_id") ;
                String payment_date= rs.getString("payment_date") ;
                double payment_amount= rs.getDouble("payment_amount") ;
                double tax_rate= rs.getDouble("tax_rate") ;
                String description= rs.getString("description") ;
                String card_type= rs.getString("card_type") ;
                String card_number= rs.getString("card_number") ;
                String card_cvn= rs.getString("card_cvn") ;

				// Add a row into the HTML table
				result += "<tr><td>" + payment_id + "</td>";
				result += "<td>" + customer_id + "</td>";
				result += "<td>" + payment_date + "</td>"; 
				result += "<td>" + payment_amount + "</td>";
				result += "<td>" + tax_rate + "</td>";
				result += "<td>" + description + "</td>";
				result += "<td>" + card_type + "</td>"; 
				result += "<td>" + card_number + "</td>";
				result += "<td>" + card_cvn + "</td>";
				
				

				// buttons
				result += "<td><input name='btnUpdate' id='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary' data-paymentid='" + payment_id + "'></td>"
						+"<td><input name='btnRemove' id='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-paymentid='" + payment_id + "'>" + "</td></tr>";
			}

			con.close();

			// Complete the HTML table
			result += "</table>";
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
            return "Error occured while Reading the Payment";
 
        }
        return result;
    }

 //insert payment

    public JsonObject insertPayment( int customer_id , String payment_date, double payment_amount,double tax_rate,String description, String card_type, String card_number, String card_cvn )
    {
        JsonObject result = null;
        try
        {
            Connection con = connect();

 

            if (con == null)
            {
                result = new JsonObject();
                result.addProperty("status", "error");
                result.addProperty("data", "Error while Connecting to the Database for Inserting Payments.");
                return result;
            }

 


            // create a prepared statement
            String query = " insert into payment"+
                    "(`customer_id`,`payment_date`,`payment_amount`,`tax_rate`,`description`,`card_type`, `card_number`, `card_cvn`)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?)";

 


            PreparedStatement preparedStmt = con.prepareStatement(query);

 


            // binding values
            preparedStmt.setInt(1, customer_id);
            preparedStmt.setString(2, payment_date);
            preparedStmt.setDouble(3, payment_amount);
            preparedStmt.setDouble(4, tax_rate);
            preparedStmt.setString(5,description);
            preparedStmt.setString(6,card_type);
            preparedStmt.setString(7,card_number);
            preparedStmt.setString(8,card_cvn);
            
            // execute the statement

 

            preparedStmt.execute();
            con.close();
            
            result = new JsonObject();
            result.addProperty("status", "successfull");
            result.addProperty("data", getPayment());
        }
        catch (Exception e)
        {
            result = new JsonObject();
            result.addProperty("status", "exception");
            result.addProperty("data", "Exception occured while Inserting data !!!");
            System.err.println(e.getMessage());
        }
        return result;
    } 
    
    
    
    //update payment
    public JsonObject updatePayment( int payment_id, int customer_id , String payment_date, double payment_amount,double tax_rate,String description, String card_type, String card_number, String card_cvn )
    {
        JsonObject result = null;
        try
        {
            Connection con = connect();

 

            if (con == null)
            {
                result = new JsonObject();
                result.addProperty("status", "error");
                result.addProperty("data", "Error while Connecting to the Database for Updating Payments !!!");
                return result;
            }

 


            // create a prepared statement
            String query = " update payment set `customer_id`= ? ,`payment_date` = ?,`payment_amount` = ?,`tax_rate` = ?,`description` = ?,`card_type` = ?, `card_number` = ?, `card_cvn` = ? where `payment_id` = ?";
                    

 


            PreparedStatement preparedStmt = con.prepareStatement(query);

 


            // binding values
            preparedStmt.setInt(1, customer_id);
            preparedStmt.setString(2, payment_date);
            preparedStmt.setDouble(3, payment_amount);
            preparedStmt.setDouble(4, tax_rate);
            preparedStmt.setString(5,description);
            preparedStmt.setString(6,card_type);
            preparedStmt.setString(7,card_number);
            preparedStmt.setString(8,card_cvn);
            preparedStmt.setInt(9, payment_id);
            
            // execute the statement

 

           int status =  preparedStmt.executeUpdate();
           
            con.close();
            
            result = new JsonObject();
            
            if(status > 0 ) {
            	
            	      
            	result.addProperty("status", "successfull");
                result.addProperty("data", getPayment());
            }
            else {
            	result.addProperty("status", "failed");
                result.addProperty("data", getPayment());
            }
      
        }
        catch (Exception e)
        {
            result = new JsonObject();
            result.addProperty("status", "exception");
            result.addProperty("data", "Exception occured while Updating Payment Details !!!");
            System.err.println(e.getMessage());
        }
        return result;
    } 
    
    

    //delete payment
    public JsonObject deletePayment( int payment_id )
    {
        JsonObject result = null;
        try
        {
            Connection con = connect();
 
            if (con == null)
            {
                result = new JsonObject();
                result.addProperty("status", "error");
                result.addProperty("data", "Error while Connecting to the Database for Deleting Paymets !!!");
                return result;
            }
 

            // create a prepared statement
            String query = "delete from payment where payment_id=?";
 
            PreparedStatement preparedStmt = con.prepareStatement(query);
 

            // binding values
            preparedStmt.setInt(1, payment_id);
//            preparedStmt.setString(2, name);
//            preparedStmt.setString(3, description);
//            preparedStmt.setString(4, category);
//            preparedStmt.setDouble(5,budget);
//            preparedStmt.setInt(6, payment_id);

            // execute the statement


            int status =  preparedStmt.executeUpdate();
            
            con.close();
            
            result = new JsonObject();
            
            if(status > 0 ) {
            	
            	      
            	result.addProperty("status", "successfull");
                result.addProperty("data", getPayment());

            }
            else {
            	result.addProperty("status", "failed");
                result.addProperty("data", getPayment());	
            }
            
            
        }
        catch (Exception e)
        {
            result = new JsonObject();
            result.addProperty("status", "exception");
            result.addProperty("data", "Exception occured while Deleting the Payment !!!");
            System.err.println(e.getMessage());
        }
        return result;
    } 


    //get single record
    public JsonObject getSinglePayment(int payment_id) {
        JsonObject result = null;
        try
        {
            Connection con = connect();
 
            if (con == null)
            {
                result = new JsonObject();
                result.addProperty("ERROR", "Error while Connecting to the Database for Inserting !!!"); 
            }
 

            // create a prepared statement
            String query = " select * from payment where payment_id=?";
 

            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setInt(1, payment_id);
            ResultSet rs = preparedStmt.executeQuery();

            JsonArray resultArray = new JsonArray();
            while(rs.next()) {
                JsonObject jsonRow = new JsonObject();
                jsonRow.addProperty("payment_id", rs.getInt("payment_id") );
                jsonRow.addProperty("customer_id", rs.getInt("customer_id") );
                jsonRow.addProperty("payment_date", rs.getString("payment_date") );
                jsonRow.addProperty("payment_amount", rs.getDouble("payment_amount") );
                jsonRow.addProperty("tax_rate", rs.getDouble("tax_rate") );
                jsonRow.addProperty("description", rs.getString("description") );
                jsonRow.addProperty("card_type", rs.getString("card_type") );
                jsonRow.addProperty("card_number", rs.getString("card_number") );
                jsonRow.addProperty("card_cvn", rs.getString("card_cvn") );
                resultArray.add(jsonRow);
                //System.out.println(jsonRow.toString());
            }
            result = new JsonObject();
            result.add("payment", resultArray);

            con.close();
        }
        catch (Exception e)
        {
            result = new JsonObject();
            result.addProperty("EXCEPTION", "Error occured while reading Payment !!!");
            System.err.println(e.getMessage());
        }
        return result;
    }



}


