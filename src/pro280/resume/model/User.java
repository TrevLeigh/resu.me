package pro280.resume.model;

public class User {
	private Long Id;
	private String name;
	private String username;
	private String password;
	private Location location;

	public User(){}
	
	public User(Long ID, String name, String username, String password, Location location){
		this(name, username, password, location);
		this.Id = ID;
	}

	public User(String name, String username, String password, Location location){
		this.name = name;
		this.username = username; 
		this.password = password;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Long getId() {
		return Id;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
