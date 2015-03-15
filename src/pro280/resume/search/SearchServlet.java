package pro280.resume.search;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search/*")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection connectToDB(String username, String password)
	{
		Connection con = null;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) 
		{
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}

		System.out.println("MySQL JDBC Driver Registered!");
		
		try
		{
			con = DriverManager.getConnection(
						         "jdbc:mysql://localhost/resume_db",
								 username,
								 password);
		}
		catch(SQLException sqle)
		{
			System.out.println("Something went wrong while connecting to the database");
			sqle.printStackTrace();
		}
		
		return con;
	}

	private List<String> searchSkills(String queryParam)
	{
		List<String> listToReturn = new ArrayList<String>();
		
		try
		{
			Connection con = connectToDB("root", "admin");
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, skills FROM users WHERE skills LIKE \'%" + queryParam + "%\';");
			
			while (rs.next()) 
			{
				listToReturn.add(rs.getString("name"));
			}
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
		
		return listToReturn;
	}
	
	private Map<Float, Float> searchLocations(float lat, float lng)
	{
		Map<Float, Float> mapToReturn = new HashMap<Float, Float>();
		
		try
		{
			Connection con = connectToDB("root", "admin");
			
			Statement stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT latitude, longitude FROM Listing where latitude LIKE '%" + lat + "%\'");
			
//			while(rs.next())
//			{
//				mapToReturn.put(rs.getFloat("latitude"), rs.getFloat("longitude"));
//			}
		}
		catch(SQLException sqle)
		{
			sqle.printStackTrace();
		}
		
		return mapToReturn;
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/search.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(!request.getParameter("skillsToSearch").isEmpty())
		{
			String skillsToSearch = request.getParameter("skillsToSearch");
			List<String> searchResults = searchSkills(skillsToSearch);
			request.setAttribute("searchResults", searchResults);
			request.setAttribute("skillsLookingFor", skillsToSearch);
		}
		else if(!request.getParameter("locationToSearch").isEmpty())
		{
			String[] arr = request.getParameter("locationToSearch").split(", ");
			float lat = Float.parseFloat(arr[0]);
			float lng = Float.parseFloat(arr[1]);
			Map<Float, Float> searchResults = searchLocations(lat, lng);
			request.setAttribute("searchResults", searchResults);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/searchResults.jsp");
		rd.forward(request, response);
	}

}

//ResultSet rs = stmt.executeQuery(
//		"SELECT latitude, longitude, SQRT(POW(69.1 * (latitude - " + lat + "), 2) +POW(69.1 * (" + lng + " - longitude) * COS(latitude / 57.3), 2)) AS distance FROM Listing HAVING distance < 50 ORDER BY distance;");