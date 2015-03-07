package pro280.resume.search;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.registry.infomodel.User;

import pro280.resume.model.Resume;

public class Persistence {

    private long userCount = 0;
    Connection con = null;
    public Persistence()
    {
        try{
            Class.forName("org.gjt.mm.mysql.Driver");
            con  = DriverManager.getConnection("jdbc:mysql://localhost:7000/rezudotme", "happy_torch", "2001033954");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public Persistence(String URL, String username, String password)
    {
        try{
            Class.forName("org.gjt.mm.mysql.Driver");
            con  = DriverManager.getConnection(URL, username, password);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public long makeUserId()
    {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("ddMMyyyy");
        String date1 = format1.format(date);
        if(++userCount > 99999999)
        {
            userCount = 1;
        }
        String num = userCount +""+ date1;
        return Long.parseLong(num);
    }
    public long makeResumeId(long userId)
    {
        int count = getResumeCount(userId) + 1;
        String num = count +""+ userId;
        return Long.parseLong(num);
    }
    public void addResume(Resume resume, long userId)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO resumes(ResumeId, UserId, Resume) VALUES(?,?,?)");
            pstmt.setLong(1,makeResumeId(userId));
            pstmt.setLong(2,userId);
            pstmt.setObject(3,resume);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void editResume(Resume newResume, long resumeId)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE resumes SET Resume = ? WHERE ResumeId = ?");
            pstmt.setObject(1,newResume);
            pstmt.setLong(2,resumeId);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public long getUserIdFromResume(long resumeId)
    {
        long result = 0;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT UserId FROM Resumes WHERE ResumeId = ?");
            pstmt.setLong(1, resumeId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = rs.getLong("UserId");
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public long getResumeId(Resume resume)
    {
        long result = 0;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT ResumeId FROM Resumes WHERE Resume = ?");
            pstmt.setObject(1, resume);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = rs.getLong("ResumeId");
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public Resume getResume(long resumeId)
    {
        Resume result = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT Resume FROM resumes WHERE ResumeId = ?");
            pstmt.setLong(1, resumeId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = deserialize(rs.getBytes("Resume"));
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    private int getResumeCount(long userId)
    {
        int count = 0;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT Resume FROM resumes WHERE UserId = ?");
            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                if(count > 255)
                    count = 0;
                count++;
            }
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return count;
    }
    public Map<Long,Resume> getResumes(long userId)
    {
        Map<Long,Resume> resumes = new HashMap<Long, Resume>();
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM resumes WHERE UserId = ?");
            pstmt.setLong(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                Resume r = deserialize(rs.getBytes("Resume"));
                resumes.put(rs.getLong("ResumeId"), r);
            }
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return resumes;
    }
    public void deleteResume(long resumeId)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM resumes WHERE ResumeId = ?");
            pstmt.setLong(1,resumeId);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteResumes(long userId)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM resumes WHERE UserId = ?");
            pstmt.setLong(1,userId);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void addUser(User user)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO users (UserId, User) VALUES(?,?)");
            pstmt.setLong(1,makeUserId());
            pstmt.setObject(2, user);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void editUser(User newUser, long userId)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE users SET User = ? WHERE UserId = ?");
            pstmt.setObject(1,newUser);
            pstmt.setLong(2,userId);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public long getUserId(User user)
    {
        long result = 0;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Users WHERE User = ?");
            pstmt.setObject(1, user);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = rs.getLong("UserId");
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public User getUser(long userId)
    {
        User result = null;
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Users WHERE UserId = ?");
            pstmt.setLong(1,userId);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            result = deserialize(rs.getBytes("User"));
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public void deleteUser(long userId)
    {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM Users WHERE UserId = ?");
            pstmt.setLong(1, userId);
            pstmt.executeUpdate();
            pstmt.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public <T> T deserialize(byte[] bytes)
    {
        T result = null;
        try {
            ByteArrayInputStream b = new ByteArrayInputStream(bytes);
            ObjectInputStream o = new ObjectInputStream(b);
            result = (T)o.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}