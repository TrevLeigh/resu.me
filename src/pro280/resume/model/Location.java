package pro280.resume.model;

public class Location
{
	private Long ID;
	private String address;
	private String state;
	private String city;
	private int zipcode;
	
	public Location(String address, String state, String city, int zipcode)
	{
		this.address = address;
		this.state = state;
		this.city = city;
		this.zipcode = zipcode;
	}

	public Location(Long ID, String address, String state, String city, int zipcode)
	{
		this(address, state, city, zipcode);
		this.ID = ID;
	}

	public Long getID() {
		return ID;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public String getAddress()
	{
		return address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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