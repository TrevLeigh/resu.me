import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Persistens {

    private long UserId = 1000;
    private long Resumeid = 1000000;

    public void addResume(Resume resume, long UserId)
    {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(b);
            out.writeObject(resume);
            byte[] ba = b.toByteArray();
            String addStatement = "insert into Resumes (Resumeid, UserId, Resume) " +
                                    "values('"+Resumeid+++"','"+UserId+"','"+ba+"')";
            st.executeUpdate(addStatement);
            con.commit();
        }catch(Exception e) {
            System.out.println("could not add your resume");
        }
    }

    public void editResume(Resume newResume, long ResumeId)
    {
        String updateStatement = "UPDATE Resumes " +
                                    "SET Resume = " + newResume +
                                    " WHERE ResumeId = " + ResumeId;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            st.executeQuery(updateStatement);
            con.commit();
        }catch(Exception e) {
            System.out.println("could not update your resume");
        }
    }

    public long getResumeId(Resume resume)
    {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(b);
            out.writeObject(resume);
            byte[] ba = b.toByteArray();
            String getStatement = "SELECT Resume from Resumes " +
                    "WHERE Resume = " + ba;

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(getStatement);
            con.commit();
            return rs.getLong("ResumeId");
        }catch(Exception e) {
            System.out.println("could not get your resume");
        }
        return 0;
    }

    public Resume getResume(long ResumeId)
    {
        String getStatement = "SELECT Resume from Resumes " +
                                "WHERE ResumeId = " + ResumeId;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(getStatement);
            con.commit();
            byte[] b = rs.getBytes("Resume");
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ObjectInputStream o = new ObjectInputStream(bais);
            return (Resume)o.readObject();
        }catch(Exception e) {
            System.out.println("could not get your resume");
        }
        return null;
    }

    public Resume[] getResumes(long UserId)
    {
        String getStatement = "SELECT Resume from Resumes " +
                "WHERE ResumeId = " + UserId;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(getStatement);
            con.commit();
            ArrayList<Resume> resumes = new ArrayList<Resume>();
            while(rs.next())
            {
                byte[] b = rs.getBytes("Resume");
                ByteArrayInputStream bais = new ByteArrayInputStream(b);
                ObjectInputStream o = new ObjectInputStream(bais);
                resumes.add((Resume)o.readObject());
            }
            return (Resume[])resumes.toArray();
        }catch(Exception e) {
            System.out.println("could not get your resume");
        }
        return null;
    }

    public void deleteResume(long ResumeId)
    {
        String deleteStatement = "DELETE FROM Resumes " +
                                "WHERE ResumeId = " + ResumeId;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            st.executeQuery(deleteStatement);
            con.commit();
        }catch(Exception e) {
            System.out.println("could not remove your resume");
        }
    }

    public void addUser(User user)
    {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(b);
            out.writeObject(user);
            byte[] ba = b.toByteArray();
            String addStatement = "insert into Users (UserId, User) " +
                    "values('"+UserId+++"','"+ba+"')";
            st.executeUpdate(addStatement);
            con.commit();
        }catch(Exception e) {
            System.out.println("could not add your resume");
        }
    }

    public void editResume(User newUser, long userId)
    {
        String updateStatement = "UPDATE Users " +
                "SET User = " + newUser +
                " WHERE UserId = " + userId;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            st.executeQuery(updateStatement);
            con.commit();
        }catch(Exception e) {
            System.out.println("could not update your resume");
        }
    }

    public long getUserId(User user)
    {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(b);
            out.writeObject(user);
            byte[] ba = b.toByteArray();
            String getStatement = "SELECT User from Users " +
                    "WHERE User = " + ba;

            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(getStatement);
            con.commit();
            return rs.getLong("UserId");
        }catch(Exception e) {
            System.out.println("could not get your resume");
        }
        return 0;
    }

    public User getUser(long UserId)
    {
        String getStatement = "SELECT User from Users " +
                "WHERE UserId = " + UserId;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(getStatement);
            con.commit();
            byte[] b = rs.getBytes("User");
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ObjectInputStream o = new ObjectInputStream(bais);
            return (User)o.readObject();
        }catch(Exception e) {
            System.out.println("could not get your resume");
        }
        return null;
    }

    public void deleteUser(long UserId)
    {
        String deleteStatement = "DELETE FROM Users " +
                "WHERE UserId = " + UserId;
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection con = DriverManager.getConnection("jdcb:odbc:DataPersistens");
            Statement st = con.createStatement();
            st.executeQuery(deleteStatement);
            con.commit();
        }catch(Exception e) {
            System.out.println("could not remove your resume");
        }
    }
}
