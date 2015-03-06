package pro280.resume.model;

import java.util.Arrays;
import java.util.List;

public class Employer
{
	private final Long ID;
	private String name;
	private List<String> skillsLookingFor;
	public Location location;
	
	public Employer(Long ID)
	{
		this.ID = ID;
	}
	
	public Employer(Long ID, String name, String[] skillsLookingFor, Location location)
	{
		this.ID = ID;
		this.name = name;
		this.skillsLookingFor = Arrays.asList(skillsLookingFor);
		this.location = location;
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
	
	public void setSkillsLookingFor(List<String> skillsLookingFor)
	{
		this.skillsLookingFor = skillsLookingFor;
	}
	
	public List<String> getSkillsLookingFor()
	{
		return skillsLookingFor;
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