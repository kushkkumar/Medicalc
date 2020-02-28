public class PatientDetails {
    private Integer pid;
    private String name = null;
    private String dob = null;
    private String sex = null;
    private String address = null;
    private Long phone;
    public PatientDetails() {
        this.pid = new Integer(0);
        this.name = "";
        this.dob = "";
        this.sex = "";
        this.address = "";
        this.phone = new Long(0L);
    }
    public PatientDetails(Integer pid, String name, String dob, String sex, String address, Long phone) {
        this.pid = pid;
        this.name = name;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }
    public void setPid(Integer pid) { this.pid = pid; }
    public Integer getPid() { return this.pid; }
    public void setName(String name) { this.name = name; }
    public String getName() { return this.name; }
    public void setDob(String dob) { this.dob = dob; }
    public String getDob() { return this.dob; }
    public void setSex(String sex) { this.sex = sex; }
    public String getSex() { return this.sex; }
    public void setAddress(String address) { this.address = address; }
    public String getAddress() { return this.address; }
    public void setPhone(Long phone) { this.phone = phone; }
    public Long getPhone() { return this.phone; }
}