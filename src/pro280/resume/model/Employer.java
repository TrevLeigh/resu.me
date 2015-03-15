package pro280.resume.model;

public class Employer extends User
{
	private String companyName;

	public Employer(String companyName)
	{
		this.companyName = companyName;
	}

	public Employer(String companyName, User user)
	{
		super(user.getId(), user.getName(), user.getUsername(), user.getPassword(), user.getLocation());
		this.companyName = companyName;
	}

	public Employer(Long ID, String companyName, String name, String username, String password, Location location)
	{
		super(ID, name, username, password, location);
		this.companyName = companyName;
	}
	
	public void setCompanyName(String name)
	{
		this.companyName = name;
	}
	
	public String getCompanyName()
	{
		return companyName;
	}
}