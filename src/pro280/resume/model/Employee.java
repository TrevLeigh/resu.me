package pro280.resume.model;

import java.util.Arrays;
import java.util.List;

public class Employee extends User
{
	private List<String> skillSet;
	
	public Employee(String[] skillSet)
	{
		this.skillSet = Arrays.asList(skillSet);
	}

	public Employee(User user, List<String> skillSet)
	{
		super(user.getId(),user.getName(),user.getUsername(),user.getPassword(),user.getLocation());
		this.skillSet = skillSet;
	}

	public Employee(Long ID, String name, String username, String password, Location location, List<String> skillSet)
	{
		super(ID,name,username,password,location);
		this.skillSet = skillSet;
	}

	
	public void setSkills(List<String> skills)
	{
		skillSet = skills;
	}
	
	public List<String> getSkills()
	{
		return skillSet;
	}
}