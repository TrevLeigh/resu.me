package pro280.resume.search;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import search_results_testing.Employee;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/search/*")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	private List<String> connectToDB(String username, String password, String queryParam)
	{
		List<String> listToReturn = new ArrayList<String>();
		
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
			Connection con = DriverManager.getConnection(
						         "jdbc:mysql://localhost/resume",
								 username,
								 password);
	
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, skills FROM User WHERE skills LIKE \'%" + queryParam + "%\';");
			
			while (rs.next()) 
			{
//				Long id = rs.getLong("id");
//				String name = rs.getString("name");
//				String skillSet = rs.getString("skills");
//				String[] skillsAsArr = skillSet.split(",\\s");
				
				listToReturn.add(rs.getString("name"));
				
//				listToReturn.add(new Employee(id, name, skillsAsArr));
				
//				System.out.println("ID: " + id);
//				System.out.println("NAME: " + name);
//				System.out.println("SKILLS: " + skillSet);
			}
		}
		catch(SQLException sqle)
		{
			System.out.println("Something went wrong while connecting to the database");
			sqle.printStackTrace();
		}
		
		return listToReturn;
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
		String skillsToSearch = request.getParameter("skillsToSearch");
		List<String> searchResults = connectToDB("root", "admin", skillsToSearch);
		request.setAttribute("searchResults", searchResults);
		request.setAttribute("skillsLookingFor", skillsToSearch);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/searchResults.jsp");
		rd.forward(request, response);
	}

}