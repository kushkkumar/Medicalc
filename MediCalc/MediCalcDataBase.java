import java.sql.*;
import java.io.*;
import java.security.*;
import java.math.*;
import java.util.*;
import java.io.*;

import javax.xml.transform.Result;

import java.text.SimpleDateFormat;

public class MediCalcDataBase {
    Connection con;
    PreparedStatement st;
    String loggedIn;
    public int currentDoctorId = 0;
    // Date date; // To get the current date
    public SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd"); // To format the date object


    // Create a connection to the database
    public MediCalcDataBase() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/medicalc", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // method that creates a md5 hash
    public String getMD5Hash(String s) {
        String hashText = "0";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(s.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hashText = no.toString(16);
            while(hashText.length() < 32) {
                hashText += "0";
            }
            // return hashText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashText;
    }


    // Add new patient
    public int addPatient(String name, String sex, long contactNumber, String dob, String address) {
        int id = 0;
        try {
            ResultSet r = con.createStatement().executeQuery("SELECT MAX(Pid) FROM patient;");
            r.next();
            id = r.getInt(1);
            if (r.wasNull()) {
                id = 100001;
            } else {
                ++id;
            }
            st = con.prepareStatement("INSERT INTO patient (Pid, Name, DOB, Sex, Address, Contact_No, Date) VALUES (?, ?, ?, ?, ?, ?, ?);");
            st.setInt(1, id);
            st.setString(2, name);
            st.setString(3, dob);
            st.setString(4, sex);
            st.setString(5, address);
            st.setLong(6, contactNumber);
            st.setString(7, dateFormatter.format(new java.util.Date()));
            int i = st.executeUpdate();
            System.out.println(i + " row inserted.");
        } catch (Exception e) {
            id = 0;
            e.printStackTrace();
        } finally {
            return id;
        }
    }


    // Check patient exist
    public boolean checkPatientExist(int id) {
        boolean val = false;
        try {
            st = con.prepareStatement("SELECT MAX(Pid) FROM patient WHERE Pid=?;");
            st.setInt(1, id);
            ResultSet r = st.executeQuery();
            r.next();
            r.getInt(1);
            if (r.wasNull()) {
                val = false;
            } else {
                val =  true;
            }   
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Add a doctor
    public int addDoctor(String name, String speciality, long contactNumber, String sex, String username, String password) {
        int id = 0;
        try {
            ResultSet r = con.createStatement().executeQuery("SELECT MAX(Phy_Id) FROM doctor;");
            r.next();
            id = r.getInt(1);
            if (id == 0) {
                id = 200001;
            } else {
                ++id;
            }
            r = con.createStatement().executeQuery("SELECT User_Name FROM users WHERE User_Name='" + username + "';");
            if (!(r.next())) {
                st = con.prepareStatement("INSERT INTO doctor (D_Name, Speciality, Phy_Id, Phone_No, Date_Of_Joining, Sex) VALUES (?, ?, ?, ?, ?, ?);");
                st.setString(1, name);
                st.setString(2, speciality);
                st.setInt(3, id);
                st.setLong(4, contactNumber);
                st.setString(5, dateFormatter.format(new java.util.Date()));
                st.setString(6, sex);
                int i = st.executeUpdate();
                System.out.println(i + " row inserted.");
                st = con.prepareStatement("INSERT INTO users (Type, User_Name, Password, Id) VALUES (?, ?, ?, ?);");
                st.setString(1, "doctor");
                st.setString(2, username);
                st.setString(3, getMD5Hash(password));
                st.setInt(4, id);
                int j = st.executeUpdate();
                System.out.println(j + " row inserted.");
            } else {
                id = -1;
            }
        } catch (Exception e) {
            id = 0;
            e.printStackTrace();
        } finally {
            return id;
        }
    }


    // Remove doctor
    public int removeDoctor(int id) {
        int val = 0;
        try {
           if (id != 0) {
                st = con.prepareStatement("UPDATE prescribe SET Phy_Id=0 WHERE Phy_Id=?;");
                st.setInt(1, id);
                int k = st.executeUpdate();
                System.out.println(k + " row updated.");
                st = con.prepareStatement("DELETE FROM doctor WHERE Phy_Id = ?;");
                st.setInt(1, id);
                int i = st.executeUpdate();
                System.out.println(i + " row deleted.");
                st = con.prepareStatement("DELETE FROM users WHERE Id = ?;");
                st.setInt(1, id);
                int j = st.executeUpdate();
                System.out.println(j + " row deleted.");
                if (i == j && i != 0) {
                    val = i;
                } else {
                    val = 0;
                }
           }
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Add Clerk
    public int addClerk(String name, long contactNumber, String sex, String username, String password) {
        int id = 0;
        try {
            ResultSet r = con.createStatement().executeQuery("SELECT MAX(Clerk_Id) FROM clerk;");
            r.next();
            id = r.getInt(1);
            if (r.wasNull()) {
                id = 300001;
            } else {
                ++id;
            }
            r = con.createStatement().executeQuery("SELECT User_Name FROM users WHERE User_Name='" + username + "';");
            if (!(r.next())) {
                st = con.prepareStatement("INSERT INTO clerk (Clerk_Id, Name, Contact_No, Date_Of_Joining, Sex) VALUES (?, ?, ?, ?, ?);");
                st.setInt(1, id);
                st.setString(2, name);
                st.setLong(3, contactNumber);
                st.setString(4, dateFormatter.format(new java.util.Date()));
                st.setString(5, sex);
                int i = st.executeUpdate();
                System.out.println(i + " row inserted.");
                st = con.prepareStatement("INSERT INTO users (Type, User_Name, Password, Id) VALUES (?, ?, ?, ?);");
                st.setString(1, "clerk");
                st.setString(2, username);
                st.setString(3, getMD5Hash(password));
                st.setInt(4, id);
                int j = st.executeUpdate();
                System.out.println(j + " row inserted.");
            } else {
                id = -1;
            }
        } catch (Exception e) {
            id = 0;
            e.printStackTrace();
        } finally {
            return id;
        }
    }


    // Remove clerk
    public int removeClerk(int id) {
        int val = 0;
        try {
            st = con.prepareStatement("DELETE FROM clerk WHERE Clerk_Id = ?;");
            st.setInt(1, id);
            int i = st.executeUpdate();
            System.out.println(i + " row deleted.");
            st = con.prepareStatement("DELETE FROM users WHERE Id = ?;");
            st.setInt(1, id);
            int j = st.executeUpdate();
            System.out.println(j + " row deleted.");
            if (i == j && i != 0) {
                val = i;
            } else {
                val = 0;
            }
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Add rooms
    public int addRoom(String roomType, int roomCost) {
        int id = 0;
        try {
            ResultSet r = con.createStatement().executeQuery("SELECT MAX(Rno) FROM room;");
            r.next();
            id = r.getInt(1);
            if (id == 0) {
                id = 400001;
            } else {
                ++id;
            }
            st = con.prepareStatement("INSERT INTO room (Rno, Room_Type, Room_Cost) VALUES (?, ?, ?);");
            st.setInt(1, id);
            st.setString(2, roomType);
            st.setInt(3, roomCost);
            int i = st.executeUpdate();
            System.out.println(i + " row inserted.");
        } catch (Exception e) {
            id = 0;
            e.printStackTrace();
        } finally {
            return id;
        }
    }


    // Remove room
    public int removeRoom(int id) {
        int val = 0;
        try {
            if (id != 0) {
                st = con.prepareStatement("UPDATE admitted_in SET Rno=0 WHERE Rno=?;");
                st.setInt(1, id);
                int j = st.executeUpdate();
                System.out.println(j + " row updated.");
                st = con.prepareStatement("UPDATE nurse SET Room_No=0 WHERE Room_No=?;");
                st.setInt(1, id);
                int k = st.executeUpdate();
                System.out.println(k + " row updated.");
                st = con.prepareStatement("DELETE FROM room WHERE Rno = ?;");
                st.setInt(1, id);
                int i = st.executeUpdate();
                System.out.println(i + " row deleted.");
                val = i;
            }
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Add medicine
    public int addMedicine(String tradeName, float price) {
        int val = 0;
        try {
            st = con.prepareStatement("SELECT Trade_Name FROM drug WHERE Trade_Name = ?;");
            st.setString(1, tradeName);
            ResultSet r = st.executeQuery();
            if (!(r.next())) {
                st = con.prepareStatement("INSERT INTO drug (Trade_Name, Price) VALUES (?, ?);");
                st.setString(1, tradeName.toLowerCase());
                st.setFloat(2, price);
                int i = st.executeUpdate();
                val = i;
                System.out.println(i + " row inserted.");
            } else {
                val = -1;
            }
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Remove medicine
    public int removeMedicine(String tradeName) {
        int val = 0;
        try {
            if (!(tradeName.toLowerCase().equals("none"))) {
                st = con.prepareStatement("UPDATE prescribe SET Trade_Name='none' WHERE Trade_Name=?;");
                st.setString(1, tradeName);
                int j = st.executeUpdate();
                System.out.println(j + " row updated.");
                st = con.prepareStatement("DELETE FROM drug WHERE Trade_Name = ?;");
                st.setString(1, tradeName);
                int i = st.executeUpdate();
                val = i;
                System.out.println(i + " row deleted.");
            }
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Add nurse
    public int addNurse(String name, String sex, long contactNumber, int roomNo, int shiftStart, int shiftEnd, String username, String password) {
        int id = 0;
        try {
            ResultSet r = con.createStatement().executeQuery("SELECT MAX(Nid) FROM nurse;");
            r.next();
            id = r.getInt(1);
            if (id == 0) {
                id = 500001;
            } else {
                ++id;
            }
            r = con.createStatement().executeQuery("SELECT User_Name FROM users WHERE User_Name='" + username + "';");
            if (!(r.next())) {
                st = con.prepareStatement("INSERT INTO nurse (Nid, Name, Sex, Phone_No, Room_No, Shift_Start, Shift_End, Date_Of_Joining) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
                st.setInt(1, id);
                st.setString(2, name);
                st.setString(3, sex);
                st.setLong(4, contactNumber);
                st.setInt(5, roomNo);
                st.setInt(6, shiftStart);
                st.setInt(7, shiftEnd);
                st.setString(8, dateFormatter.format(new java.util.Date()));
                int i = st.executeUpdate();
                System.out.println(i + " row inserted.");
                st = con.prepareStatement("INSERT INTO users (Type, User_Name, Password, Id) VALUES (?, ?, ?, ?);");
                st.setString(1, "nurse");
                st.setString(2, username);
                st.setString(3, getMD5Hash(password));
                st.setInt(4, id);
                int j = st.executeUpdate();
                System.out.println(j + " row inserted.");
            } else {
                id = -1;
            }
        } catch (Exception e) {
            id = 0;
            e.printStackTrace();
        } finally {
            return id;
        }
    }


    // Remove nurse
    public int removeNurse(int id) {
        int val = 0;
        try {
            if (id != 0) {
                st = con.prepareStatement("UPDATE patient SET Nid=0 WHERE Nid=?;");
                st.setInt(1, id);
                int k = st.executeUpdate();
                System.out.println(k + " row updated.");
                st = con.prepareStatement("DELETE FROM nurse WHERE Nid = ?;");
                st.setInt(1, id);
                int i = st.executeUpdate();
                System.out.println(i + " row deleted.");
                st = con.prepareStatement("DELETE FROM users WHERE Id = ?;");
                st.setInt(1, id);
                int j = st.executeUpdate();
                System.out.println(j + " row deleted.");
                if (i == j && i != 0) {
                    val = i;
                } else {
                    val = 0;
                }
            }
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Add prescription
    public int addPrescription(int pid, int phyId, String tradeName, int quantity, String ailment) {
        int val = 0;
        try {
            st = con.prepareStatement("INSERT INTO prescribe (Pid, Phy_Id, Trade_Name, Date, Quantity, Ailment) VALUES (?, ?, ?, ?, ?, ?);");
            st.setInt(1, pid);
            st.setInt(2, phyId);
            st.setString(3, tradeName.toLowerCase());
            st.setString(4, dateFormatter.format(new java.util.Date()));
            st.setInt(5, quantity);
            st.setString(6, ailment);
            int i = st.executeUpdate();
            val = i;
            System.out.println(i + " row inserted.");
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Admit a patient
    public int admitPatient(int pid, int rno, int bedno) {
        int val = 0;
        try {
            st  = con.prepareStatement("INSERT INTO admitted_in (Pid, Rno, Bed_No, Date) VALUES (?, ?, ?, ?);");
            st.setInt(1, pid);
            st.setInt(2, rno);
            st.setInt(3, bedno);
            st.setString(4, dateFormatter.format(new java.util.Date()));
            int i = st.executeUpdate();
            System.out.println(i + " row inserted.");
            val = i;
            st = con.prepareStatement("UPDATE patient SET Nid=(SELECT MAX(Nid) FROM nurse WHERE Room_No=?),Room_Start=? WHERE Pid=?;");
            st.setInt(1, rno);
            st.setString(2, dateFormatter.format(new java.util.Date()));
            st.setInt(3, pid);
            int j = st.executeUpdate();
            System.out.println(j + " row updated.");
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Discharge patient
    public int dischargePatient(int pid) {
        int val = 0;
        try {
            st = con.prepareStatement("UPDATE patient SET Room_End=? WHERE Pid=?;");
            st.setString(1, dateFormatter.format(new java.util.Date()));
            st.setInt(2, pid);
            int i = st.executeUpdate();
            val = i;
            System.out.println(i + " row updated.");
            st = con.prepareStatement("DELETE FROM admitted_in WHERE Pid=?;");
            st.setInt(1, pid);
            int j = st.executeUpdate();
            System.out.println(j + " row deleted.");
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }


    // Get patient details with patient id
    public ResultSet getPatientDetails(int id) {
        ResultSet r = null;
        try {
            st = con.prepareStatement("SELECT * FROM patient WHERE Pid=?;");
            st.setInt(1, id);
            r = st.executeQuery();
        } catch (Exception e) {
            r = null;
            e.printStackTrace();
        } finally {
            return r;
        }
    }


    // Get patient details with patient name
    public ResultSet getPatientDetails(String name) {
        ResultSet r = null;
        try {
            st = con.prepareStatement("SELECT * FROM patient WHERE Name=?;");
            st.setString(1, name);
            r = st.executeQuery();
        } catch (Exception e) {
            r = null;
            e.printStackTrace();
        } finally {
            return r;
        }
    }


    // Get patient details with patient date
    public ResultSet getPatientDetailsFromDate(String date) {
        ResultSet r = null;
        try {
            st = con.prepareStatement("SELECT * FROM patient WHERE Date=?;");
            st.setString(1, date);
            r = st.executeQuery();
        } catch (Exception e) {
            r = null;
            e.printStackTrace();
        } finally {
            return r;
        }
    }


    // Get prescription of today
    public ResultSet getPrescription(int pid, int phyId) {
        ResultSet r = null;
        try {
            st = con.prepareStatement("SELECT pt.Name,pt.DOB,pt.Sex,p.Date,d.D_Name,d.Phone_No,p.Ailment,p.Trade_Name,p.Quantity FROM prescribe p,patient pt,doctor d WHERE p.Pid=pt.Pid AND p.Phy_Id=d.Phy_Id AND p.Pid=? AND p.Phy_Id=? AND p.Date=?;");
            st.setInt(1, pid);
            st.setInt(2, phyId);
            st.setString(3, dateFormatter.format(new java.util.Date()));
            r = st.executeQuery();
        } catch (Exception e) {
            r = null;
            e.printStackTrace();
        } finally {
            return r;
        }
    }



    // Get prescription of any date
    public ResultSet getPrescription(int pid, int phyId, String date) {
        ResultSet r = null;
        try {
            st = con.prepareStatement("SELECT pt.Name,pt.DOB,pt.Sex,p.Date,d.D_Name,d.Phone_No,p.Ailment,p.Trade_Name,p.Quantity FROM prescribe p,patient pt,doctor d WHERE p.Pid=pt.Pid AND p.Phy_Id=d.Phy_Id AND p.Pid=? AND p.Phy_Id=? AND p.Date=?;");
            st.setInt(1, pid);
            st.setInt(2, phyId);
            st.setString(3, date);
            r = st.executeQuery();
        } catch (Exception e) {
            r = null;
            e.printStackTrace();
        } finally {
            return r;
        }
    }

    // Do authentication
    public boolean doAuth(String type, String username, String password) {
        boolean val = false;
        try {
            st = con.prepareStatement("SELECT * FROM users WHERE Type=? AND User_Name=? AND Password=?;");
            st.setString(1, type);
            st.setString(2, username);
            st.setString(3, getMD5Hash(password));
            ResultSet r = st.executeQuery();
            if(!r.next()) {
                val = false;
            } else {
                val = true;
            }
        } catch (Exception e) {
            val = false;
            e.printStackTrace();
        } finally {
            return val;
        }
    }

    // Admin MD5 hash
    public String getAdminMD5Hash(String s) {
        String hashText = "0";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(s.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hashText = no.toString(36);
            while(hashText.length() < 25) {
                hashText += "0";
            }
            // return hashText;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return new String(hashText);
    }

    // Authentication for Admin
    public boolean doAdminAuth(String username, String password) {
        boolean val = false;
        FileInputStream userFile = null;
        FileInputStream passFile = null;
        try {
            userFile = new FileInputStream(".sec/.admin/.admin.txt");
            passFile = new FileInputStream(".sec/.admin/.pass.txt");
            String user = new String(userFile.readAllBytes());
            String pass = new String(passFile.readAllBytes());
            if (user.equals(username) && pass.equals(getAdminMD5Hash(password))) {
                val = true;
            }
        } catch (Exception e) {
            val = false;
            e.printStackTrace();
        } finally {
            try {
                userFile.close();
                passFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return val;
        }
    }

    // Get List of room types
    // public String[] getRoomTypes() {
    //     String[] arr = null;
    //     ArrayList<String> lst = new ArrayList<String>();
    //     try {
    //         st = con.prepareStatement("SELECT Rno,Room_Type FROM room;");
    //         ResultSet r = st.executeQuery();
    //         while (r.next()) {
    //             lst.add(r.getInt(1) + " " + r.getString(2));
    //         }
    //         arr = new String[lst.size()];
    //         arr = lst.toArray(arr);
    //     } catch (Exception e) {
    //         arr = null;
    //         e.printStackTrace();
    //     } finally {
    //         return arr;
    //     }
    // }

    // Get doctor id
    public int getEmployeeId(String type, String username, String password) {
        int val = 0;
        try {
            st = con.prepareStatement("SELECT Id FROM users WHERE Type=? AND User_Name=? AND Password=?;");
            st.setString(1, type);
            st.setString(2, username);
            st.setString(3, getMD5Hash(password));
            ResultSet r = st.executeQuery();
            if (r.next()) {
                val = r.getInt(1);
            } else {
                val = 0;
            }
        } catch (Exception e) {
            val = 0;
            e.printStackTrace();
        } finally {
            return val;
        }
    }

    // Check Medicine

    public boolean checkMedicine(String tradeName) {
        boolean val = false;
        try {
            st = con.prepareStatement("SELECT Trade_Name FROM drug WHERE Trade_Name=?;");
            st.setString(1, tradeName);
            ResultSet r = st.executeQuery();
            if (r.next()) {
                val = true;
            } else {
                val = false;
            }
        } catch (Exception e) {
            val = false;
            e.printStackTrace();
        } finally {
            return val;
        }
    }

    // Check Room
    
    public boolean checkRoom(int id) {
        boolean val = false;
        try {
            st = con.prepareStatement("SELECT Rno FROM room WHERE Rno=?;");
            st.setInt(1 ,id);
            ResultSet r = st.executeQuery();
            if (r.next()) {
                val = true;
            } else {
                val = false;
            }
        } catch (Exception e) {
            val = false;
            e.printStackTrace();
        } finally {
            return val;
        }
    }
}