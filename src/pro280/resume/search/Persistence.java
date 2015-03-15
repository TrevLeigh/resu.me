package pro280.resume.search;

import pro280.resume.model.*;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Persistence {


    Connection con = null;

    public Persistence() {
        this("jdbc:mysql://localhost:7000/rezudotme", "happy_torch", "2001033954");
    }

    public Persistence(String URL, String username, String password) {
        try {
            Class.forName("org.gjt.mm.mysql.Driver");
            con = DriverManager.getConnection(URL, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long addResume(Resume resume, Long AuthorId) {
        String statement = "INSERT INTO resumes(resume_id, author_id, resume) " +
                "VALUES(?,?)";
        Long result = 0l;
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, AuthorId);
            p.setObject(2, resume);
            p.executeUpdate();
            result = getResumeId(resume);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void editResume(Resume newResume, long resumeId) {
        String statement = "UPDATE resumes " +
                "SET resume = ? " +
                "WHERE resume_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setObject(1, newResume);
            p.setLong(2, resumeId);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getResumeId(Resume resume) {
        Long result = 0l;
        String statement = "SELECT resume_id " +
                "FROM resumes " +
                "WHERE resume = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setObject(1, resume);
            ResultSet rs = p.executeQuery();
            rs.next();
            result = rs.getLong("resume_id");
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Resume getResume(long resumeId) {
        Resume result = null;
        String statement = "SELECT * " +
                "FROM resumes " +
                "WHERE resume_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, resumeId);
            ResultSet rs = p.executeQuery();
            rs.next();
            Long author = rs.getLong("author_id");
            Resume resume = deserialize(rs.getBytes("resume"));
            result = new Resume(resumeId, author, resume);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getResumeCount(long userId) {
        int count = 0;
        String statement = "SELECT resume " +
                "FROM resumes " +
                "WHERE author_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                if (count > 255)
                    count = 0;
                count++;
            }
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Map<Long, Resume> getResumes(long userId) {
        Map<Long, Resume> resumes = new HashMap<Long, Resume>();
        String statement = "SELECT * " +
                "FROM resumes " +
                "WHERE author_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Resume r = deserialize(rs.getBytes("resume"));
                long id = rs.getLong("resume_id");
                long author = rs.getLong("author_id");
                resumes.put(id, new Resume(id, author, r));
            }
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resumes;
    }

    public void deleteResume(long resumeId) {
        String statement = "DELETE FROM resumes " +
                "WHERE resume_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, resumeId);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteResumes(long userId) {
        String statement = "DELETE FROM resumes " +
                "WHERE author_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long addUser(User user) {
        String statement = "INSERT INTO users (name, location_id, username, password) " +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, user.getName());
            Long LocationId;
            try {
                LocationId = addLocation(user.getLocation());
            } catch (Exception e) {
                LocationId = getLocationId(user.getLocation());
            }
            p.setLong(2, LocationId);
            p.setString(3, user.getUsername());
            p.setString(4, user.getPassword());
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getUserId(user);
    }

    public void editUser(User newUser, long userId) {
        String statement = "UPDATE users " +
                "SET name = ?, username = ?, password = ?, location_id = ? " +
                "WHERE user_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, newUser.getName());
            p.setString(2, newUser.getUsername());
            p.setString(3, newUser.getPassword());
            Long LocationId;
            try {
                LocationId = addLocation(newUser.getLocation());
            } catch (Exception e) {
                LocationId = getLocationId(newUser.getLocation());
            }
            p.setLong(4, LocationId);
            p.setLong(5, userId);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getUserId(User user) {
        Long result = 0l;
        String statement = "SELECT user_id " +
                "FROM users " +
                "WHERE name = ? " +
                "AND username = ? " +
                "AND password = ? " +
                "AND location_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, user.getName());
            p.setString(2, user.getUsername());
            p.setString(3, user.getPassword());
            p.setLong(4, getLocationId(user.getLocation()));
            ResultSet rs = p.executeQuery();
            rs.next();
            Long id = rs.getLong("user_id");
            result = id;
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public User getUser(long userId) {
        User result = null;
        String statement = "SELECT * " +
                "FROM users " +
                "WHERE user_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            ResultSet rs = p.executeQuery();
            rs.next();
            Location l = getLocation(rs.getLong("location_id"));
            String name = rs.getString("name");
            String username = rs.getString("username");
            String password = rs.getString("password");
            result = new User(userId, name, username, password, l);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteUser(long userId) {
        String statement = "DELETE FROM users " +
                "WHERE user_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long addEmployee(Employee user) {
        Long id;
        try {
            id = addUser((User) user.getClass().getSuperclass().newInstance());
        } catch (Exception e) {
            id = getUserId(user);
        }
        String statement = "INSERT INTO employees (employee_id, skillset) " +
                "VALUES(?,?)";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, id);
            p.setString(2, ListToString(user.getSkills()));
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void editEmployee(Employee newUser, long userId) {
        try {
            editUser((User) newUser.getClass().getSuperclass().newInstance(), userId);
        } catch (Exception e) {
            try {
                addUser((User) newUser.getClass().getSuperclass().newInstance());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        String statement = "UPDATE employees " +
                "SET skillset = ? " +
                "WHERE employee_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, ListToString(newUser.getSkills()));
            p.setLong(2, userId);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getEmployeeId(Employee user) {
        User u = null;
        try {
            u = (User) user.getClass().getSuperclass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getUserId(u);
    }

    public Employee getEmployee(long userId) {
        Employee result = null;
        String statement = "SELECT skillset " +
                "FROM employees " +
                "WHERE employee_id = ?";
        try {
            User user = getUser(userId);
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            ResultSet rs = p.executeQuery();
            rs.next();
            result = new Employee(user, StringToList(rs.getString("skillset")));
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteEmployee(long userId) {
        String statement = "DELETE FROM employees " +
                "WHERE employee_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            p.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long addEmployer(Employer user) {
        Long id;
        try {
            id = addUser((User) user.getClass().getSuperclass().newInstance());
        } catch (Exception e) {
            id = getUserId(user);
        }
        String statement = "INSERT INTO employers (employer_id, company_name) " +
                "VALUES(?,?)";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, user.getId());
            p.setString(2, user.getCompanyName());
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void editEmployer(Employer newUser, long userId) {
        try {
            editUser((User) newUser.getClass().getSuperclass().newInstance(), userId);
        } catch (Exception e) {
            try {
                addUser((User) newUser.getClass().getSuperclass().newInstance());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        String statement = "UPDATE employees " +
                "SET company_name = ? " +
                "WHERE employer_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, newUser.getCompanyName());
            p.setLong(2, userId);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getEmployerId(Employer user) {
        User u = null;
        try {
            u = (User) user.getClass().getSuperclass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getUserId(u);
    }

    public Employer getEmployer(long userId) {
        Employer result = null;
        String statement = "SELECT CompanyName " +
                "FROM employers " +
                "WHERE employer_id = ?";
        try {
            User user = getUser(userId);
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            ResultSet rs = p.executeQuery();
            rs.next();
            result = new Employer(rs.getString("company_name"), user);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteEmployer(long userId) {
        String statement = "DELETE FROM employers " +
                "WHERE employer_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            p.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long addLocation(Location location) {
        Long result = 0l;
        String statement = "INSERT INTO locations (address, state, city, zip) " +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, location.getAddress());
            p.setString(2, location.getState());
            p.setString(3, location.getCity());
            p.setInt(4, location.getZipcode());
            p.executeUpdate();
            result = getLocationId(location);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Long getLocationId(Location location) {
        Long result = 0l;
        String statement = "SELECT location_id " +
                "FROM locations " +
                "WHERE address = ? " +
                "AND state = ? " +
                "AND city = ? " +
                "AND zip = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, location.getAddress());
            p.setString(2, location.getState());
            p.setString(3, location.getCity());
            p.setInt(4, location.getZipcode());
            ResultSet rs = p.executeQuery();
            rs.next();
            result = rs.getLong("LocationId");
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Location getLocation(long locationId) {
        Location result = null;
        String statement = "SELECT * " +
                "FROM locations " +
                "WHERE location_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, locationId);
            ResultSet rs = p.executeQuery();
            rs.next();
            String address = rs.getString("address");
            String state = rs.getString("state");
            String city = rs.getString("city");
            int zip = rs.getInt("zip");
            result = new Location(locationId, address, state, city, zip);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Long addListing(Listing listing) {
        Long result = 0l;
        String statement = "INSERT INTO listings (salary, position, description, location_id, owner_id, skills_looking_for) " +
                "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, listing.getSalary());
            p.setString(2, listing.getPosition());
            p.setString(3, listing.getDescription());
            Long LocationId;
            try {
                LocationId = addLocation(listing.getLocation());
            } catch (Exception e) {
                LocationId = getLocationId(listing.getLocation());
            }
            p.setLong(4, LocationId);
            Long EmployerId;
            try {
                EmployerId = addEmployer(listing.getEmployer());
            } catch (Exception e) {
                EmployerId = getEmployerId(listing.getEmployer());
            }
            p.setLong(5, EmployerId);
            p.setString(6, ListToString(listing.getSkillLookingFor()));
            p.executeUpdate();
            result = getListingId(listing);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void editListing(Listing newListing, long listingId) {
        String statement = "UPDATE listings " +
                "SET salary = ?, position = ?, description = ?, location_id = ?, owner_id = ?, skills_looking_for = ?" +
                "WHERE listings_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, newListing.getSalary());
            p.setString(2, newListing.getPosition());
            p.setString(3, newListing.getDescription());
            p.setLong(4, getLocationId(newListing.getLocation()));
            p.setLong(5, getEmployerId(newListing.getEmployer()));
            p.setString(6, ListToString(newListing.getSkillLookingFor()));
            p.setLong(7, listingId);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getListingId(Listing listing) {
        Long result = 0l;
        String statement = "SELECT listing_id " +
                "FROM listings " +
                "WHERE salary = ? " +
                "AND position = ? " +
                "AND description = ? " +
                "AND location_id = ? " +
                "AND owner_id = ?" +
                "AND skills_looking_for = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, listing.getSalary());
            p.setString(2, listing.getPosition());
            p.setString(3, listing.getDescription());
            p.setLong(4, getLocationId(listing.getLocation()));
            p.setLong(5, getEmployerId(listing.getEmployer()));
            p.setString(6, ListToString(listing.getSkillLookingFor()));
            ResultSet rs = p.executeQuery();
            rs.next();
            result = rs.getLong("location_id");
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Listing getListing(long listingId) {
        Listing result = null;
        String statement = "SELECT * " +
                "FROM listings " +
                "WHERE listing_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, listingId);
            ResultSet rs = p.executeQuery();
            rs.next();
            Long salary = rs.getLong("salary");
            String position = rs.getString("position");
            String description = rs.getString("description");
            Location location = getLocation(rs.getLong("location_id"));
            Employer employer = getEmployer(rs.getLong("employer_id"));
            List<String> skills = StringToList(rs.getString("skills_looking_for"));
            result = new Listing(listingId, salary, position, description, location, employer, skills);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteListing(long listingId) {
        String statement = "DELETE FROM listings " +
                "WHERE listing_id = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, listingId);
            p.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String ListToString(List<String> skills) {
        String result = "";
        for (int i = 0; i < skills.size() - 1; i++) {
            result += skills.get(i) + " ";
        }
        result += skills.get(skills.size() - 1);
        return result;
    }

    public List<String> StringToList(String skills) {
        List<String> result = Arrays.asList(skills.split(" "));
        return result;
    }

    public <T> T deserialize(byte[] bytes) {
        T result = null;
        try {
            ByteArrayInputStream b = new ByteArrayInputStream(bytes);
            ObjectInputStream o = new ObjectInputStream(b);
            result = (T) o.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}