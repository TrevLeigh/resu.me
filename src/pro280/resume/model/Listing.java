package pro280.resume.model;

public class Listing
{
    private Long ID;
    private String salary;
    private String position;
    private String description;
    private Location location;
    private Employer employer;

    public Listing(String salary, String position, String description, Location location, Employer employer)
    {
        this.salary = salary;
        this.description = description;
        this.position = position;
        this.location = location;
        this.employer = employer;
    }

    public Listing(Long ID, String salary, String position, String description, Location location, Employer employer)
    {
        this(salary, position, description, location, employer);
        this.ID = ID;
    }

    public Long getID() {
        return ID;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }
}
