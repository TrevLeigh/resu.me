package pro280.resume.servlet;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pro280.resume.model.Resume;

/**
 * Servlet implementation class Resume
 */
@WebServlet("/resu.me/*")
public class ResumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ResumeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String text;
	private String name = "First Name & Last Name";
	private String street = "Street Address";
	private String city = "City";
	private String state = "State";
	private String zip = "Zip";
	private String homePhone = "Home Phone";
	private String cellPhone = "Cell Phone";
	private String email = "Email";
	private String qualifications = "Enter some of your many qualifications here!";
	private String techSkills = "Wow some future employeers by showing off your skills";
	private String accomplishments = "You are an awesome person so show off your many accomplishments";
	private String workEx = "Tell us more about your incredible work experience";
	private String edu = "Let us know the great times in college";
	private String key = "Give us some key words that describes the job that you are after";

	Resume resume = new Resume(text,name,street,city,state,zip,homePhone,cellPhone,email,qualifications, techSkills,accomplishments, workEx, edu,key);

	private Pattern RESUME_VIEW = Pattern.compile("/resu.me/Resume");


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		Matcher m = RESUME_VIEW.matcher(uri);
		if(m.find()){
			request.setAttribute("resume", resume);
			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/layout.jsp");
			view.forward(request, response);
		}
		else if(request.getPathInfo().endsWith("edit")){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/editresume.jsp");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		Matcher m = RESUME_VIEW.matcher(uri);
		if(m.find()){
			response.sendRedirect(request.getContextPath() + "/resu.me/edit");
		}
		else if(request.getPathInfo().endsWith("edit")){
			resume.setText(request.getParameter("user_content"));
			resume.setName(request.getParameter("name"));
			resume.setStreet(request.getParameter("street"));
			resume.setCity(request.getParameter("city"));
			resume.setState(request.getParameter("state"));
			resume.setZip(request.getParameter("zip"));
			resume.setHomePhone(request.getParameter("home"));
			resume.setCellPhone(request.getParameter("cell"));
			resume.setEmail(request.getParameter("email"));
			resume.setQualifications(request.getParameter("qualifications"));
			resume.setTechSkills(request.getParameter("techSkills"));
			resume.setAccomplishments(request.getParameter("accomplishments"));
			resume.setWorkEx(request.getParameter("workEx"));
			resume.setEdu(request.getParameter("edu"));
			resume.setKey(request.getParameter("key"));


			request.setAttribute("resume", resume);
			response.sendRedirect(request.getContextPath() + "/resu.me/Resume");
		}

	}

}
