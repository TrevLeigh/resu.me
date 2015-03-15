package pro280.resume.model;

public class Resume {
    private Long ID;
    private Long AuthorID;
    private String text;
    private String name;
    private String street;
    private String city;
    private String state;
    private int zip;
    private String homePhone;
    private String cellPhone;
    private String email;
    private String qualifications;
    private String techSkills;
    private String accomplishments;
    private String workEx;
    private String edu;
    private String key;

    public Resume(String text, String name, String street, String city, String state, int zip, String homePhone,
                  String cellPhone, String email, String qualifications, String techSkills, String accomplishments,
                  String workEx, String edu, String key) {
        this.text = text;
        this.name = name;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.email = email;
        this.qualifications = qualifications;
        this.techSkills = techSkills;
        this.accomplishments = accomplishments;
        this.workEx = workEx;
        this.edu = edu;
        this.key = key;
    }

    public Resume(Long ID, Long AuthorID, Resume resume) {
        this(resume.getText(), resume.getName(), resume.getStreet(), resume.getCity(), resume.getState(),
                resume.getZip(), resume.getHomePhone(), resume.getCellPhone(), resume.getEmail(),
                resume.getQualifications(), resume.getTechSkills(), resume.getAccomplishments(), resume.getWorkEx(),
                resume.getEdu(), resume.getKey());
        this.ID = ID;
        this.AuthorID = AuthorID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getTechSkills() {
        return techSkills;
    }

    public void setTechSkills(String techSkills) {
        this.techSkills = techSkills;
    }

    public String getAccomplishments() {
        return accomplishments;
    }

    public void setAccomplishments(String accomplishments) {
        this.accomplishments = accomplishments;
    }

    public String getWorkEx() {
        return workEx;
    }

    public void setWorkEx(String workEx) {
        this.workEx = workEx;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getAuthorID() {
        return AuthorID;
    }

    public void setAuthorID(Long authorID) {
        AuthorID = authorID;
    }
}
