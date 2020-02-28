public class PrescriptionDetails {
    private String patientName = null;
    private String patientDob = null;
    private String patientSex = null;
    private String date = null;
    private String doctorName = null;
    private String doctorPhone = null;
    private String ailment = null;
    private String medicine = null;
    private String quantity = null;
    public PrescriptionDetails() {
        this.patientName = "";
        this.patientDob = "";
        this.patientSex = "";
        this.date = "";
        this.doctorName = "";
        this.doctorPhone = "";
        this.ailment = "";
        this.medicine = "";
        this.quantity = "";
    }
    public PrescriptionDetails(String patientName, String patientDob, String patientSex, String date, String doctorName, String doctorPhone, String ailment, String medicine, String quantity) {
        this.patientName = patientName;
        this.patientDob = patientDob;
        this.patientSex = patientSex;
        this.date = date;
        this.doctorName = doctorName;
        this.doctorPhone = doctorPhone;
        this.ailment = ailment;
        this.medicine = medicine;
        this.quantity = quantity;
    }
    public void setPatientName(String patientName) { this.patientName = patientName; }
    public String getPatientName() { return this.patientName; }
    public void setPatientDob(String patientDob) { this.patientDob = patientDob; }
    public String getPatientDob() { return this.patientDob; }
    public void setPatientSex(String patientSex) { this.patientSex = patientSex; }
    public String getPatientSex() { return this.patientSex; }
    public void setDate(String date) { this.date = date; }
    public String getDate() { return this.date; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
    public String getDoctorName() { return this.doctorName; }
    public void setDoctorPhone(String doctorPhone) { this.doctorPhone = doctorPhone; }
    public String getDoctorPhone() { return this.doctorPhone; }
    public void setAilment(String ailment) { this.ailment = ailment; }
    public String getAilment() { return this.ailment; }
    public void setMedicine(String medicine) { this.medicine = medicine; }
    public String getMedicine() { return this.medicine; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public String getQuantity() { return this.quantity; }
}
