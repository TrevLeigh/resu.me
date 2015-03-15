package pro280.resume.model;

public class User {
	private Long ID;
	private String name; 
	private String skills;
	private String username;
	private String password;
	
	public User() {}
	
	//For registration purposes
	public User(String name, String skills, String username, String password) {
		this.name = name;
		this.skills = skills; 
		this.username = username; 
		this.password = password;
	}
	
	//For log in purposes
	public User(Long ID, String name, String skills, String username, String password) {
		this.ID = ID;
		this.name = name;
		this.skills = skills; 
		this.username = username; 
		this.password = password;
	}
	
	public Long getID()
	{
		return ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
