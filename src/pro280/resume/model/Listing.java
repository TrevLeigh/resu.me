package pro280.resume.model;

import java.util.List;

public class Listing
{
    private Long ID;
    private Long salary;
    private String position;
    private String description;
    private Location location;
    private Employer employer;
    private List<String> skillLookingFor;

    public Listing(Long salary, String position, String description, Location location, Employer employer, List<String> skillLookingFor)
    {
        this.salary = salary;
        this.description = description;
        this.position = position;
        this.location = location;
        this.employer = employer;
        this.skillLookingFor = skillLookingFor;
    }

    public Listing(Long ID, Long salary, String position, String description, Location location, Employer employer, List<String> skillLookingFor)
    {
        this(salary, position, description, location, employer, skillLookingFor);
        this.ID = ID;
    }

    public Long getID() {
        return ID;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
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

    public List<String> getSkillLookingFor() {
        return skillLookingFor;
    }

    public void setSkillLookingFor(List<String> skillLookingFor) {
        this.skillLookingFor = skillLookingFor;
    }
}
