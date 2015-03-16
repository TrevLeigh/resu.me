package pro280.resume.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pro280.resume.model.Location;
import pro280.resume.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Connection connectToDB(String username, String password) {
		Connection con = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
		}

		System.out.println("MySQL JDBC Driver Registered!");

		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost/resume_db",
					username,
					password);
		} catch(SQLException sqle) {
			System.out.println("Something went wrong while connecting to the database");
			sqle.printStackTrace();
		}

		return con;
	}
	
	private User logIn(String username, String password) {
		User userToReturn = null;
		try {
			Connection con = connectToDB("root","admin");
			Statement ps = con.createStatement();
			ResultSet result = ps.executeQuery("select * from users where username='" + username+"' and password='"+password+"'");
			while(result.next()) {
				userToReturn = new User(result.getLong("user_id"), result.getString("name"), result.getString("username"), result.getString("password"), new Location(result.getLong("location_id")));
				
//				userToReturn.setName(result.getString("name"));
//				userToReturn.setPassword(result.getString("password"));
//				userToReturn.setSkills(result.getString("skills"));
//				userToReturn.setUsername(result.getString("username"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userToReturn;
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User userInSession = logIn(request.getParameter("username"), request.getParameter("password"));
		HttpSession session = request.getSession();
		if(userInSession != null)
		{
			session.setAttribute("user_id", userInSession.getId());
			session.setMaxInactiveInterval(600);
			request.setAttribute("model", userInSession);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/home.jsp");
			rd.forward(request, response);
		}
		else
		{
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/login.jsp");
			rd.forward(request, response);
		}
	}
}

/* 
 * user.setPassword(request.getParameter("password"));
			user.setUsername(request.getParameter("username"));
			
			request.setAttribute("user", user);

			logIn(user.getUsername(),user.getPassword());
			user.setSkills("");
			user.getName();
			
			response.sendRedirect("/Registration/success");
 */
