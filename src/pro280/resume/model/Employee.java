package pro280.resume.model;

import java.util.Arrays;
import java.util.List;

public class Employee
{
	private final Long ID;
	private String name;
	private List<String> skillSet;
	private Location location;
	
	public Employee(Long ID)
	{
		this.ID = ID;
	}
	
	public Employee(Long ID, String name, String[] skillSet)
	{
		this.ID = ID;
		this.name = name;
		this.skillSet = Arrays.asList(skillSet);
	}
	
	public Long getID()
	{
		return ID;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setSkills(List<String> skills)
	{
		skillSet = skills;
	}
	
	public List<String> getSkills()
	{
		return skillSet;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public void setLocation(Location location)
	{
		this.location = location;
	}
}