package pro280.resume.search;

import pro280.resume.model.*;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        String statement = "INSERT INTO resumes(ResumeId, AuthorId, Resume) " +
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
                "SET Resume = ? " +
                "WHERE ResumeId = ?";
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
        String statement = "SELECT ResumeId " +
                "FROM resumes " +
                "WHERE Resume = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setObject(1, resume);
            ResultSet rs = p.executeQuery();
            rs.next();
            result = rs.getLong("ResumeId");
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
                "WHERE ResumeId = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, resumeId);
            ResultSet rs = p.executeQuery();
            rs.next();
            Long id = rs.getLong("ResumeId");
            Long author = rs.getLong("AuthorId");
            Resume resume = (Resume)deserialize(rs.getBytes("Resume"));
            result = new Resume(id,author,resume);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getResumeCount(long userId) {
        int count = 0;
        String statement = "SELECT Resume " +
                "FROM resumes " +
                "WHERE UserId = ?";
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
                "WHERE UserId = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                Resume r = deserialize(rs.getBytes("Resume"));
                long id = rs.getLong("ResumeId");
                long author = rs.getLong("AuthorId");
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
                "WHERE ResumeId = ?";
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
                "WHERE AuthorId = ?";
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
        String statement = "INSERT INTO users (Name, Username, Password, LocationId) " +
                "VALUES(?,?,?,?)";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, user.getName());
            p.setString(2, user.getUsername());
            p.setString(3, user.getPassword());
            Long LocationId;
            try {
                LocationId = addLocation(user.getLocation());
            } catch (Exception e) {
                LocationId = getLocationId(user.getLocation());
            }
            p.setLong(4, LocationId);
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getUserId(user);
    }

    public void editUser(User newUser, long userId) {
        String statement = "UPDATE users " +
                "SET Name = ?, Username = ?, password = ?, LocationId = ? " +
                "WHERE UserId = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, newUser.getName());
            p.setString(2, newUser.getUsername());
            p.setString(3, newUser.getPassword());
            Long LocationId;
            try{
                LocationId = addLocation(newUser.getLocation());
            }catch(Exception e){
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
        String statement = "SELECT UserId " +
                "FROM users " +
                "WHERE Name = ? " +
                "AND Username = ? " +
                "AND Password = ? " +
                "AND LocationId = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, user.getName());
            p.setString(2, user.getUsername());
            p.setString(3, user.getPassword());
            p.setLong(4, user.getLocation().getID());
            ResultSet rs = p.executeQuery();
            rs.next();
            Long id = rs.getLong("UserId");
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
                "WHERE UserId = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            ResultSet rs = p.executeQuery();
            rs.next();
            Location l = getLocation(rs.getLong("LocationId"));
            Long id = rs.getLong("UserId");
            String name = rs.getString("Name");
            String username = rs.getString("Username");
            String password = rs.getString("Password");
            result = new User(id, name, username, password, l);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteUser(long userId) {
        String statement = "DELETE FROM users " +
                "WHERE UserId = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(statement);
            pstmt.setLong(1, userId);
            pstmt.executeUpdate();
            pstmt.close();
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
        String statement = "INSERT INTO employees (EmployerId, Skill) " +
                "VALUES(?,?)";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            for (String s : user.getSkills()) {
                p.setLong(1, id);
                p.setString(2, s);
                p.executeUpdate();
            }
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
        deleteEmployee(userId);
        addEmployee(newUser);
    }

    public Long getEmployeeId(Employee user) {
        User u = null;
        try {
            u = (User)user.getClass().getSuperclass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getUserId(u);
    }

    public Employee getEmployee(long userId) {
        Employee result = null;
        String statement = "SELECT Skill " +
                "FROM employees " +
                "WHERE UserId = ?";
        try {
            User user = getUser(userId);
            ArrayList<String> skills = new ArrayList<String>();
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            ResultSet rs = p.executeQuery();
            while (rs.next()) {
                skills.add(rs.getString("Skill"));
            }
            p.close();
            result = new Employee(user, skills);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteEmployee(long userId) {
        String statement = "DELETE FROM employees " +
                "WHERE UserId = ?";
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
        String statement = "INSERT INTO employers (EmployeeId, CompanyName) " +
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
                "SET CompanyName = ? " +
                "WHERE EmployerId = ?";
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
            u = (User)user.getClass().getSuperclass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getUserId(u);
    }

    public Employer getEmployer(long userId) {
        Employer result = null;
        String statement = "SELECT CompanyName " +
                "FROM employers " +
                "WHERE EmployerId = ?";
        try {
            User user = getUser(userId);
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, userId);
            ResultSet rs = p.executeQuery();
            rs.next();
            result = new Employer(rs.getString("CompanyName"), user);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteEmployer(long userId) {
        String statement = "DELETE FROM employers " +
                "WHERE EmployerId = ?";
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
        String statement = "INSERT INTO locations (Address, State, City, ZipCode) " +
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
        String statement = "SELECT LocationId " +
                "FROM locations " +
                "WHERE Address = ? " +
                "AND State = ? " +
                "AND City = ? " +
                "AND ZipCode = ?";
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
                "WHERE LocationId = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setLong(1, locationId);
            ResultSet rs = p.executeQuery();
            rs.next();
            long id = rs.getLong("LocationId");
            String address = rs.getString("Address");
            String state = rs.getString("State");
            String city = rs.getString("City");
            int zip = rs.getInt("ZipCode");
            result = new Location(id, address, state, city, zip);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Long addListing(Listing listing) {
        Long result = 0l;
        String statement = "INSERT INTO listings (Salary, Position, Description, LocationId, EmployerId) " +
                "VALUES(?,?,?,?,?)";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, listing.getSalary());
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
            p.executeUpdate();
            result = getListingId(listing);
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void editListing(Listing newListing, long listingId) {
        String statement ="UPDATE listings " +
                "SET Salary = ?, Position = ?, Description = ?, LocationId = ?, EmployerId = ?" +
                "WHERE listingsId = ?";
        try {
            PreparedStatement p = con.prepareStatement(statement);
            p.setString(1, newListing.getSalary());
            p.setString(2, newListing.getPosition());
            p.setString(3, newListing.getDescription());
            p.setLong(4, getLocationId(newListing.getLocation()));
            p.setLong(6, getEmployerId(newListing.getEmployer()));
            p.executeUpdate();
            p.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Long getListingId(Listing location) {
        Long result = 0l;
        String statement = "SELECT LocationId " +
                "FROM locations " +
                "WHERE Address = ? " +
                "AND State = ? " +
                "AND City = ? " +
                "AND ZipCode = ?";
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

    public Listing getListing(long locationId) {
        Listing result = null;
        String statement = "SELECT * " +
                "FROM locations " +
                "WHERE LocationId = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(statement);
            pstmt.setLong(1, locationId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = null;
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteListing(long locationId) {
        String statement = "DELETE FROM locations " +
                "WHERE LocationId = ?";
        try {
            PreparedStatement pstmt = con.prepareStatement(statement);
            pstmt.setLong(1, locationId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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