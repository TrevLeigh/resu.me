package pro280.resume.model;

public class Location
{
	private String address;
	private String city;
	private int zipcode;
	
	public Location() {}
	
	public Location(String address, String city, int zipcode)
	{
		this.address = address;
		this.city = city;
		this.zipcode = zipcode;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getAddress()
	{
		return address;
	}
	
	public void setCity(String city)
	{
		this.city = city;
	}
	
	public String getCity()
	{
		return city;
	}
	
	public void setZipcode(int zipcode)
	{
		this.zipcode = zipcode;
	}
	
	public int getZipcode()
	{
		return zipcode;
	}
}