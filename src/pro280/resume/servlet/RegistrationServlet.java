package pro280.resume.servlet;


import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pro280.resume.model.User;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/Registration/*")
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String name;
	private String skills;
	private String userName;
	private String password;
	private User user = new User(name, skills, userName, password);
	private Pattern REGISTRATION_VIEW = Pattern.compile("/Registration/reg");
//	private Pattern LOGIN_VIEW = Pattern.compile("/Registration/login");
	private Pattern SUCCESS_VIEW = Pattern.compile("/Registration/success");
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

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
	
	private void registerUser(){
		try {
			Connection con = connectToDB("root","");
			PreparedStatement ps;
			ps = con.prepareStatement("insert into User values(?,?,?,?,?)");
			ps.setString(1,"0");
			ps.setString(2,user.getName());
			ps.setString(3,user.getSkills());
			ps.setString(4,user.getUsername());
			ps.setString(5,user.getPassword());
			int i = ps.executeUpdate();
			if(i>0)
			{
				System.out.println("You are sucessfully registered!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		Matcher m = REGISTRATION_VIEW.matcher(uri);
		Matcher mat = SUCCESS_VIEW.matcher(uri);
			
		if(m.find()){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/register.jsp");
			rd.forward(request, response);
		}
		
		else if(mat.find()){
			request.setAttribute("user", user);
			RequestDispatcher red = request.getRequestDispatcher("/WEB-INF/views/success.jsp");
			red.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String uri = request.getRequestURI();
		Matcher m = REGISTRATION_VIEW.matcher(uri);
		Matcher mat = SUCCESS_VIEW.matcher(uri);

		if(m.find()){
			user.setName(request.getParameter("name"));
			user.setPassword(request.getParameter("password"));
			user.setSkills(request.getParameter("skills"));
			user.setUsername(request.getParameter("username"));

			registerUser();
			response.sendRedirect("/Registration/login");
		}
		
		else if(mat.find()){
			user.getName();
			user.getPassword();
			user.getSkills();
			user.getUsername();
			request.setAttribute("user", user);
			response.sendRedirect(request.getContextPath() + "/Registration/login");
			
		}

	}

}
