package com;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class Project {

	public Connection connect() {

		Connection con = null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paf_gb2", "root", null);
			// For testing
			System.out.println("Successfully connected");
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String insertProject(String code, String name, String price, String desc) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into project (`projectID`,`projectCode`,`projectName`,`projectFund`,`projectDesc`)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setDouble(4, Double.parseDouble(price));
			preparedStmt.setString(5, desc);
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newProjects = readProjects();
			output = "{\"status\":\"success\", \"data\": \"" + newProjects + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the project.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	public String readProjects() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Project Code</th> " + "<th>Project Name</th><th>Project Fund</th>"
					+ "<th>Project Description</th>" + "<th>Update</th>" + "<th>Remove</th></tr>";
			String query = "select * from project";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String projectID = Integer.toString(rs.getInt("projectID"));
				String projectCode = rs.getString("projectCode");
				String projectName = rs.getString("projectName");
				String projectFund = Double.toString(rs.getDouble("projectFund"));
				String projectDesc = rs.getString("projectDesc");
				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate' " + "name='hidItemIDUpdate' " + "type='hidden' value='"
						+ projectID + "'>" + projectCode + "</td>";
				output += "<td>" + projectName + "</td>";
				output += "<td>" + projectFund + "</td>";
				output += "<td>" + projectDesc + "</td>";
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-projectid='"
						+ projectID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the projects.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	public String updateProject(String ID, String code, String name, String price, String desc) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE project SET projectCode=?,projectName=?,projectFund=?,projectDesc=? WHERE projectID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setDouble(3, Double.parseDouble(price));
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, Integer.parseInt(ID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newProjects = readProjects();
			output = "{\"status\":\"success\", \"data\": \"" + newProjects + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the project.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	
	
	public String deleteProject(String projectID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from project where projectID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(projectID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newProjects = readProjects();
			output = "{\"status\":\"success\", \"data\": \"" + newProjects + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the project.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
}

