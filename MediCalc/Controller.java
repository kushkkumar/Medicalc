import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.collections.*;

import javafx.scene.control.cell.*;

import javafx.geometry.*;

import javafx.util.*;

import java.net.*;

import java.io.*;

import java.sql.*;

import java.util.*;



public class Controller implements Initializable {


    @FXML
    private AnchorPane parent;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FadeTransition.applyFadeTransition(parent, Duration.seconds(4),(e)->{
            try {
                Parent fxml= FXMLLoader.load(getClass().getResource("front.fxml"));
                parent.getChildren().removeAll();
                parent.getChildren().setAll(fxml);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    MediCalcDataBase database = new MediCalcDataBase();

    @FXML

    // to login page---------------------------

    public void clerkLogin(ActionEvent event)throws Exception{
        if (DynamicVar.getDynamo().currentClerkId != 0) {
            Parent view2= FXMLLoader.load(getClass().getResource("clerk.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Clerk");
            st.setScene(scene2);
            st.show();
        } else {
            Parent view2= FXMLLoader.load(getClass().getResource("clerkLogin.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Login page");
            st.setScene(scene2);
            st.show();
        }
    }
    public void doctorLogin(ActionEvent event)throws Exception{
        if (DynamicVar.getDynamo().currentDoctorId != 0) {
            Parent view2= FXMLLoader.load(getClass().getResource("doctor.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Doctor");
            st.setScene(scene2);
            st.show();
        } else {
            Parent view2= FXMLLoader.load(getClass().getResource("doctorLogin.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Login page");
            st.setScene(scene2);
            st.show();
        }
        
    }
    public void nurseLogin(ActionEvent event)throws Exception{
        if (DynamicVar.getDynamo().currentNurseId != 0) {
            Parent view2= FXMLLoader.load(getClass().getResource("nurse.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Nurse");
            st.setScene(scene2);
            st.show();
        } else {
            Parent view2= FXMLLoader.load(getClass().getResource("nurseLogin.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Login page");
            st.setScene(scene2);
            st.show();
        }
    }
    public void adminLogin(ActionEvent event)throws Exception{
        Parent view2= FXMLLoader.load(getClass().getResource("adminLogin.fxml"));
        Scene scene2=new Scene(view2);
        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("Login page");
        st.setScene(scene2);
        st.show();



        // login to personal page--------------------------------------------------------------


    
    }
    

    // Clerk Login

    @FXML
    private TextField clerkLoginUserName, patientNameClerk, patientPhoneNumberClerk, patientIdToSearch;
    @FXML
    private PasswordField clerkLoginPassword;
    @FXML
    private TextArea patientAddressClerk;
    @FXML
    private DatePicker dobPatientClerk;
    @FXML
    private ToggleGroup groupAddPatient;
    public void Clerk(ActionEvent event)throws Exception{
        if (database.doAuth("clerk", clerkLoginUserName.getText(), clerkLoginPassword.getText())) {
            DynamicVar.getDynamo().currentClerkId = database.getEmployeeId("clerk", clerkLoginUserName.getText(), clerkLoginPassword.getText());
            DynamicVar.getDynamo().currentDoctorId = 0;
            DynamicVar.getDynamo().currentNurseId = 0;
            Parent view2= FXMLLoader.load(getClass().getResource("clerk.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Clerk");
            st.setScene(scene2);
            st.show();
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Unsucessful Login", "Unauthorized Credentials!!!");
        }
    }
    public void addPatient(ActionEvent event) throws Exception {
        String date = null, gender = null;
        if (!patientNameClerk.getText().matches("^$") && !patientPhoneNumberClerk.getText().matches("^$") && !patientAddressClerk.getText().matches("^$")) {
            if (patientPhoneNumberClerk.getText().matches("[0-9]{10}")) {
                if (groupAddPatient.getSelectedToggle() != null) {
                    if (dobPatientClerk.getValue() != null) {
                        date = dobPatientClerk.getValue().toString();
                        if (((RadioButton)groupAddPatient.getSelectedToggle()).getText().equals("MALE")) {
                            gender = "M";
                        } else {
                            gender = "F";
                        }
                        int val = database.addPatient(patientNameClerk.getText(), gender, Long.parseLong(patientPhoneNumberClerk.getText()), date, patientAddressClerk.getText());
                        if (val == 0) {
                            AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not add patient.");
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Patient ID : " + val);
                            // Parent view2= FXMLLoader.load(getClass().getResource("front.fxml"));
                            // Scene scene2=new Scene(view2);
                            // Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                            // st.setTitle("Dashboard");
                            // st.setScene(scene2);
                            // st.show();
                            patientNameClerk.setText("");
                            patientPhoneNumberClerk.setText("");
                            patientAddressClerk.setText("");
                            dobPatientClerk.setValue(null);
                            ((RadioButton)groupAddPatient.getSelectedToggle()).setSelected(false);
                        }
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please select a valid date");
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please select your gender");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Phone Number");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please fill all the fields.");
        }
    }
    public void searchPatient(ActionEvent event) throws Exception {
        if (patientIdToSearch.getText().matches("[0-9]*")) {
            if (database.checkPatientExist(Integer.parseInt(patientIdToSearch.getText()))) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Patient exists");
                // Parent view2= FXMLLoader.load(getClass().getResource("front.fxml"));
                // Scene scene2=new Scene(view2);
                // Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                // st.setTitle("Dashboard");
                // st.setScene(scene2);
                // st.show();
                patientIdToSearch.setText("");
            } else {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Patient doesn't exist");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Id");
        }
    }










    // Doctor Login



    private int currentPatientId = 0;
    @FXML
    private TextField doctorUserNameLogin, patientIdGet, patientAilment, patientMedicine, patientQuantity;
    @FXML
    private PasswordField doctorPasswordLogin;
    @FXML
    private Label patientIdLabel, patientNameLabel, patientDobLabel, patientSexLabel;
    public void Doctor(ActionEvent event)throws Exception{
        if (database.doAuth("doctor", doctorUserNameLogin.getText(), doctorPasswordLogin.getText())) {
            DynamicVar.getDynamo().currentDoctorId = database.getEmployeeId("doctor", doctorUserNameLogin.getText(), doctorPasswordLogin.getText());
            DynamicVar.getDynamo().currentClerkId = 0;
            DynamicVar.getDynamo().currentNurseId = 0;
            Parent view2= FXMLLoader.load(getClass().getResource("doctor.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Doctor");
            st.setScene(scene2);
            st.show();
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Unsucessful Login", "Unauthorized Credentials!!!");
        }
    }
    public void doctorSearchPatient(ActionEvent event) throws Exception {
        if (patientIdGet.getText().matches("[0-9]*") && !patientIdGet.getText().matches("^$")) {
            ResultSet r = database.getPatientDetails(Integer.parseInt(patientIdGet.getText()));
            if (r != null) {
                if (r.next()) {
                    currentPatientId = Integer.parseInt(patientIdGet.getText());
                    patientIdLabel.setText(patientIdGet.getText());
                    patientNameLabel.setText(r.getString("Name"));
                    patientDobLabel.setText(r.getString("DOB"));
                    if (r.getString("Sex").equals("M")) {
                        patientSexLabel.setText("Male");
                    } else {
                        patientSexLabel.setText("Female");
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Patinet Doesn't exist.");
                    currentPatientId = 0;
                    patientIdLabel.setText("NONE");
                    patientNameLabel.setText("NONE");
                    patientDobLabel.setText("NONE");
                    patientSexLabel.setText("NONE");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not search for patient.");
                currentPatientId = 0;
                patientIdLabel.setText("NONE");
                patientNameLabel.setText("NONE");
                patientDobLabel.setText("NONE");
                patientSexLabel.setText("NONE");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Patient Id");
            currentPatientId = 0;
            patientIdLabel.setText("NONE");
            patientNameLabel.setText("NONE");
            patientDobLabel.setText("NONE");
            patientSexLabel.setText("NONE");
        }
    }
    public void addPrescription(ActionEvent event) throws Exception {
        if (currentPatientId != 0) {
            if (!patientAilment.getText().matches("^$") && !patientMedicine.getText().matches("^$") && !patientQuantity.getText().matches("^$")) {
                if (patientQuantity.getText().matches("[0-9]*")) {
                    if (database.checkMedicine(patientMedicine.getText())) {
                        int val = database.addPrescription(currentPatientId, DynamicVar.getDynamo().currentDoctorId, patientMedicine.getText(), Integer.parseInt(patientQuantity.getText()), patientAilment.getText());
                        if (val != 0) {
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Patient Prescription Successful.");
                            currentPatientId = 0;
                            patientIdLabel.setText("NONE");
                            patientNameLabel.setText("NONE");
                            patientDobLabel.setText("NONE");
                            patientSexLabel.setText("NONE");
                            patientIdGet.setText("");
                            patientAilment.setText("");
                            patientMedicine.setText("");
                            patientQuantity.setText("");
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not add prescription.");
                        }
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please enter valid medicine.");
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please enter valid quantity.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please fill all the fields.");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Patient Id");
        }
    }







    // Nurse Login


    private int currentPatientNurseId;
    @FXML
    private TextField nurseLoginUserName, getPatientIdSearch, roomIdNurse, bedNumberNurse;
    @FXML
    private PasswordField nurseLoginPassword;
    @FXML
    private Label nursePatientId, nursePatientName, nursePatientDob, nursePatientSex;
    public void Nurse(ActionEvent event)throws Exception{
        if (database.doAuth("nurse", nurseLoginUserName.getText(), nurseLoginPassword.getText())) {
            DynamicVar.getDynamo().currentNurseId = database.getEmployeeId("nurse", nurseLoginUserName.getText(), nurseLoginPassword.getText());
            DynamicVar.getDynamo().currentClerkId = 0;
            DynamicVar.getDynamo().currentDoctorId = 0;
            Parent view2= FXMLLoader.load(getClass().getResource("nurse.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
            st.setTitle("Nurse");
            st.setScene(scene2);
            st.show();
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Unsucessful Login", "Unauthorized Credentials!!!");
        }
    }
    public void searchPatientNurse(ActionEvent event) throws Exception {
        if (!getPatientIdSearch.getText().matches("^$")) {
            if (getPatientIdSearch.getText().matches("[0-9]*")) {
                ResultSet r = database.getPatientDetails(Integer.parseInt(getPatientIdSearch.getText()));
                if (r != null) {
                    if (r.next()) {
                        currentPatientNurseId = Integer.parseInt(getPatientIdSearch.getText());
                        nursePatientId.setText(getPatientIdSearch.getText());
                        nursePatientName.setText(r.getString("Name"));
                        nursePatientDob.setText(r.getString("DOB"));
                        if (r.getString("Sex").equals("M")) {
                            nursePatientSex.setText("Male");
                        } else {
                            nursePatientSex.setText("Female");
                        }
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Patinet Doesn't exist.");
                        currentPatientNurseId = 0;
                        nursePatientId.setText("NONE");
                        nursePatientName.setText("NONE");
                        nursePatientDob.setText("NONE");
                        nursePatientSex.setText("NONE");
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not search for patient.");
                    currentPatientNurseId = 0;
                    nursePatientId.setText("NONE");
                    nursePatientName.setText("NONE");
                    nursePatientDob.setText("NONE");
                    nursePatientSex.setText("NONE");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Patient Id");
                currentPatientNurseId = 0;
                nursePatientId.setText("NONE");
                nursePatientName.setText("NONE");
                nursePatientDob.setText("NONE");
                nursePatientSex.setText("NONE");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Patient Id");
            currentPatientNurseId = 0;
            nursePatientId.setText("NONE");
            nursePatientName.setText("NONE");
            nursePatientDob.setText("NONE");
            nursePatientSex.setText("NONE");
        }
    }
    public void admitPatient(ActionEvent event) {
        if (currentPatientNurseId != 0) {
            if (!roomIdNurse.getText().matches("^$") && !bedNumberNurse.getText().matches("^$")) {
                if (roomIdNurse.getText().matches("[0-9]*") && bedNumberNurse.getText().matches("[0-9]*")) {
                    if (database.checkRoom(Integer.parseInt(roomIdNurse.getText()))) {
                        int val = database.admitPatient(currentPatientNurseId, Integer.parseInt(roomIdNurse.getText()), Integer.parseInt(bedNumberNurse.getText()));
                        if (val != 0) {
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Patient Admission Successful.");
                            currentPatientNurseId = 0;
                            nursePatientId.setText("NONE");
                             nursePatientName.setText("NONE");
                            nursePatientDob.setText("NONE");
                            nursePatientSex.setText("NONE");
                            getPatientIdSearch.setText("");
                            roomIdNurse.setText("");
                            bedNumberNurse.setText("");
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not admit patient.");
                        }
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Room number doesn't exist.");
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Values in the fields.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please fill in all fields.");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Patient Id");
        }
    }
    public void dischargePatient(ActionEvent event) throws Exception {
        if (!getPatientIdSearch.getText().matches("^$")) {
            if (getPatientIdSearch.getText().matches("[0-9]*")) {
                int val = database.dischargePatient(Integer.parseInt(getPatientIdSearch.getText()));
                if (val != 0) {
                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Patient Discharged Successfully.");
                    getPatientIdSearch.setText("");
                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not discharge patient.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Patient Id");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Patient Id");
        }
    }









    // Admin Login

    @FXML
    private TextField adminUsernameField;
    @FXML
    private PasswordField adminPasswordField;

    public void Admin(ActionEvent event)throws Exception{
        Window window = ((Node)event.getSource()).getScene().getWindow();
        if (database.doAdminAuth(adminUsernameField.getText().toString(), adminPasswordField.getText().toString())) {
            DynamicVar.getDynamo().currentClerkId = 0;
            DynamicVar.getDynamo().currentDoctorId = 0;
            DynamicVar.getDynamo().currentNurseId = 0;
            Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
            Scene scene2=new Scene(view2);
            Stage st=(Stage)window;
            st.setTitle("Admin");
            st.setScene(scene2);
            st.show();
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, window, "Unsucessful Login", "Unauthorized Credentials!!!");
        }
    }


    public void About(ActionEvent event)throws Exception{
        Parent view2= FXMLLoader.load(getClass().getResource("about.fxml"));
        Scene scene2=new Scene(view2);
        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("About");
        st.setScene(scene2);
        st.show();
    }



    // Patient Details and Prescription



    public void patientPage(ActionEvent event) throws Exception {
        Parent view2= FXMLLoader.load(getClass().getResource("patient.fxml"));
        Scene scene2=new Scene(view2);
        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("About");
        st.setScene(scene2);
        st.show();
    }
    public void patientDetailsFromId(ActionEvent event) throws Exception {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Patient Details from Id");
        dialog.setHeaderText("Enter the patient Id:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent((x) -> {
            if (!x.matches("^$") && x.matches("[0-9]*")) {
                ResultSet r = database.getPatientDetails(Integer.parseInt(x));
                if (r != null) {
                    try {
                        if (r.next()) {
                            String message = "";
                            message += "Patient Id: " + r.getInt("Pid") + "\n";
                            message += "Patient Name: " + r.getString("Name") + "\n";
                            message += "Patient DOB: " + r.getString("DOB") + "\n";
                            message += "Patient Sex: " + r.getString("Sex") + "\n";
                            message += "Patient Address: " + r.getString("Address") + "\n";
                            message += "Patient Phone Number: " + r.getLong("Contact_No");
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Patient Details", message);
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Patient Doesn't Exist.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Cannot retrive data.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Invalid id.");
            }
        });
    }
    public void patientDetailsFromName(ActionEvent event) throws Exception {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Get Patient Details from Name");
        dialog.setHeaderText("Enter the patient Name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent((x) -> {
            if (!x.matches("^$")) {
                ResultSet r = database.getPatientDetails(x);
                if (r != null) {
                    try {
                        if (r.next()) {
                            r.beforeFirst();
                            TableView tv = new TableView();
                            TableColumn<Integer,PatientDetails> col1 = new TableColumn<Integer,PatientDetails>("Patient Id");
                            col1.setCellValueFactory(new PropertyValueFactory<Integer,PatientDetails>("pid"));
                            TableColumn<String,PatientDetails> col2 = new TableColumn<String,PatientDetails>("Name");
                            col2.setCellValueFactory(new PropertyValueFactory<String,PatientDetails>("name"));
                            TableColumn<String,PatientDetails> col3 = new TableColumn<String,PatientDetails>("Date Of Birth");
                            col3.setCellValueFactory(new PropertyValueFactory<String,PatientDetails>("dob"));
                            TableColumn<String,PatientDetails> col4 = new TableColumn<String,PatientDetails>("Sex");
                            col4.setCellValueFactory(new PropertyValueFactory<String,PatientDetails>("sex"));
                            TableColumn<String,PatientDetails> col5 = new TableColumn<String,PatientDetails>("Address");
                            col5.setCellValueFactory(new PropertyValueFactory<String,PatientDetails>("address"));
                            TableColumn<Long,PatientDetails> col6 = new TableColumn<Long,PatientDetails>("Phone Number");
                            col6.setCellValueFactory(new PropertyValueFactory<Long,PatientDetails>("phone"));
                            tv.getColumns().add(col1);
                            tv.getColumns().add(col2);
                            tv.getColumns().add(col3);
                            tv.getColumns().add(col4);
                            tv.getColumns().add(col5);
                            tv.getColumns().add(col6);
                            while (r.next()) {
                                tv.getItems().add(new PatientDetails(new Integer(r.getInt("Pid")), r.getString("Name"), r.getString("DOB"), r.getString("Sex"), r.getString("Address"), new Long(r.getLong("Contact_No"))));
                            }
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Info");
                            alert.setHeaderText("Patient Details");
                            alert.setContentText("Patient Details of name " + x);
                            GridPane.setVgrow(tv, Priority.ALWAYS);
                            GridPane.setHgrow(tv, Priority.ALWAYS);
                            GridPane gp = new GridPane();
                            gp.setMaxWidth(Double.MAX_VALUE);
                            gp.add(tv, 0, 0);
                            alert.getDialogPane().setContent(gp);
                            alert.showAndWait();
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Patient Name Doesn't Exist.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Cannot retrive data.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Name.");
            }
        });
    }
    public void patientDetailsFromDate(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Get Patient Details from Date");
        alert.setHeaderText("Select Date");
        DatePicker dp = new DatePicker();
        dp.setPromptText("Select Date");
        GridPane.setVgrow(dp, Priority.ALWAYS);
        GridPane.setHgrow(dp, Priority.ALWAYS);
        GridPane gp = new GridPane();
        gp.setMaxWidth(Double.MAX_VALUE);
        gp.add(dp, 0, 0);
        alert.getDialogPane().setContent(gp);
        alert.showAndWait();
        if (dp.getValue() != null) {
            ResultSet r = database.getPatientDetailsFromDate(dp.getValue().toString());
            if (r != null) {
                try {
                    if (r.next()) {
                        r.beforeFirst();
                        TableView tv = new TableView();
                        TableColumn<Integer,PatientDetails> col1 = new TableColumn<Integer,PatientDetails>("Patient Id");
                        col1.setCellValueFactory(new PropertyValueFactory<Integer,PatientDetails>("pid"));
                        TableColumn<String,PatientDetails> col2 = new TableColumn<String,PatientDetails>("Name");
                        col2.setCellValueFactory(new PropertyValueFactory<String,PatientDetails>("name"));
                        TableColumn<String,PatientDetails> col3 = new TableColumn<String,PatientDetails>("Date Of Birth");
                        col3.setCellValueFactory(new PropertyValueFactory<String,PatientDetails>("dob"));
                        TableColumn<String,PatientDetails> col4 = new TableColumn<String,PatientDetails>("Sex");
                        col4.setCellValueFactory(new PropertyValueFactory<String,PatientDetails>("sex"));
                        TableColumn<String,PatientDetails> col5 = new TableColumn<String,PatientDetails>("Address");
                        col5.setCellValueFactory(new PropertyValueFactory<String,PatientDetails>("address"));
                        TableColumn<Long,PatientDetails> col6 = new TableColumn<Long,PatientDetails>("Phone Number");
                        col6.setCellValueFactory(new PropertyValueFactory<Long,PatientDetails>("phone"));
                        tv.getColumns().add(col1);
                        tv.getColumns().add(col2);
                        tv.getColumns().add(col3);
                        tv.getColumns().add(col4);
                        tv.getColumns().add(col5);
                        tv.getColumns().add(col6);
                        while (r.next()) {
                            tv.getItems().add(new PatientDetails(new Integer(r.getInt("Pid")), r.getString("Name"), r.getString("DOB"), r.getString("Sex"), r.getString("Address"), new Long(r.getLong("Contact_No"))));
                        }
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("Info");
                        alert1.setHeaderText("Patient Details");
                        alert1.setContentText("Patient Details on date " + dp.getValue());
                        GridPane.setVgrow(tv, Priority.ALWAYS);
                        GridPane.setHgrow(tv, Priority.ALWAYS);
                        GridPane gp1 = new GridPane();
                        gp1.setMaxWidth(Double.MAX_VALUE);
                        gp1.add(tv, 0, 0);
                        alert1.getDialogPane().setContent(gp1);
                        alert1.showAndWait();
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Patient Name Doesn't Exist.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Cannot retrive data.");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Select Valid Date");
        }
    }
    public void todayPrescription(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Get Today's Prescription Details");
        alert.setHeaderText("Today's Prescription Details");
        alert.setContentText("Enter patient id and doctor id");
        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(20, 150, 10, 10));
        TextField pid = new TextField(), phyid = new TextField();
        pid.setPromptText("Enter Patient Id");
        phyid.setPromptText("Enter Doctor Id");
        g.add(pid, 0, 0);
        g.add(phyid, 0, 1);
        alert.getDialogPane().setContent(g);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (!pid.getText().matches("^$") && !phyid.getText().matches("^$") && pid.getText().matches("[0-9]*") && phyid.getText().matches("[0-9]*")) {
                ResultSet r = database.getPrescription(Integer.parseInt(pid.getText()), Integer.parseInt(phyid.getText()));
                if (r != null) {
                    try {
                        if (r.next()) {
                            r.beforeFirst();
                            TableView tv = new TableView();
                            TableColumn<String,PrescriptionDetails> col1 = new TableColumn<String,PrescriptionDetails>("Patient Name");
                            col1.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("patientName"));
                            TableColumn<String,PrescriptionDetails> col2 = new TableColumn<String,PrescriptionDetails>("Patient DOB");
                            col2.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("patientDob"));
                            TableColumn<String,PrescriptionDetails> col3 = new TableColumn<String,PrescriptionDetails>("Patient Sex");
                            col3.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("patientSex"));
                            TableColumn<String,PrescriptionDetails> col4 = new TableColumn<String,PrescriptionDetails>("Date");
                            col4.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("date"));
                            TableColumn<String,PrescriptionDetails> col5 = new TableColumn<String,PrescriptionDetails>("Doctor Name");
                            col5.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("doctorName"));
                            TableColumn<String,PrescriptionDetails> col6 = new TableColumn<String,PrescriptionDetails>("Doctor Phone Number");
                            col6.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("doctorPhone"));
                            TableColumn<String,PrescriptionDetails> col7 = new TableColumn<String,PrescriptionDetails>("Ailment");
                            col7.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("ailment"));
                            TableColumn<String,PrescriptionDetails> col8 = new TableColumn<String,PrescriptionDetails>("Medicine");
                            col8.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("medicine"));
                            TableColumn<String,PrescriptionDetails> col9 = new TableColumn<String,PrescriptionDetails>("Quantity");
                            col9.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("quantity"));
                            tv.getColumns().add(col1);
                            tv.getColumns().add(col2);
                            tv.getColumns().add(col3);
                            tv.getColumns().add(col4);
                            tv.getColumns().add(col5);
                            tv.getColumns().add(col6);
                            tv.getColumns().add(col7);
                            tv.getColumns().add(col8);
                            tv.getColumns().add(col9);
                            while (r.next()) {
                                tv.getItems().add(new PrescriptionDetails(r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8), new String("" + r.getInt(9))));
                            }
                            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                            alert1.setTitle("Info");
                            alert1.setHeaderText("Prescription Details");
                            alert1.setContentText("Prescription Details are:");
                            GridPane.setVgrow(tv, Priority.ALWAYS);
                            GridPane.setHgrow(tv, Priority.ALWAYS);
                            GridPane gp1 = new GridPane();
                            gp1.setMaxWidth(Double.MAX_VALUE);
                            gp1.add(tv, 0, 0);
                            alert1.getDialogPane().setContent(gp1);
                            alert1.showAndWait();
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "No Prescriptions on today.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Cannot retrive data.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter valid details.");
            }
        }
    }
    public void anydayPrescription(ActionEvent event) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Get Prescription Details");
        alert.setHeaderText("Prescription Details");
        alert.setContentText("Enter patient id and doctor id and date");
        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(20, 150, 10, 10));
        TextField pid = new TextField(), phyid = new TextField();
        DatePicker dp = new DatePicker();
        dp.setPromptText("Select Date");
        pid.setPromptText("Enter Patient Id");
        phyid.setPromptText("Enter Doctor Id");
        g.add(pid, 0, 0);
        g.add(phyid, 0, 1);
        g.add(dp, 0, 2);
        alert.getDialogPane().setContent(g);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (!pid.getText().matches("^$") && !phyid.getText().matches("^$") && pid.getText().matches("[0-9]*") && phyid.getText().matches("[0-9]*")) {
                if (dp.getValue() != null) {
                    ResultSet r = database.getPrescription(Integer.parseInt(pid.getText()), Integer.parseInt(phyid.getText()), dp.getValue().toString());
                    if (r != null) {
                        try {
                            if (r.next()) {
                                r.beforeFirst();
                                TableView tv = new TableView();
                                TableColumn<String,PrescriptionDetails> col1 = new TableColumn<String,PrescriptionDetails>("Patient Name");
                                col1.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("patientName"));
                                TableColumn<String,PrescriptionDetails> col2 = new TableColumn<String,PrescriptionDetails>("Patient DOB");
                                col2.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("patientDob"));
                                TableColumn<String,PrescriptionDetails> col3 = new TableColumn<String,PrescriptionDetails>("Patient Sex");
                                col3.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("patientSex"));
                                TableColumn<String,PrescriptionDetails> col4 = new TableColumn<String,PrescriptionDetails>("Date");
                                col4.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("date"));
                                TableColumn<String,PrescriptionDetails> col5 = new TableColumn<String,PrescriptionDetails>("Doctor Name");
                                col5.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("doctorName"));
                                TableColumn<String,PrescriptionDetails> col6 = new TableColumn<String,PrescriptionDetails>("Doctor Phone Number");
                                col6.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("doctorPhone"));
                                TableColumn<String,PrescriptionDetails> col7 = new TableColumn<String,PrescriptionDetails>("Ailment");
                                col7.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("ailment"));
                                TableColumn<String,PrescriptionDetails> col8 = new TableColumn<String,PrescriptionDetails>("Medicine");
                                col8.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("medicine"));
                                TableColumn<String,PrescriptionDetails> col9 = new TableColumn<String,PrescriptionDetails>("Quantity");
                                col9.setCellValueFactory(new PropertyValueFactory<String,PrescriptionDetails>("quantity"));
                                tv.getColumns().add(col1);
                                tv.getColumns().add(col2);
                                tv.getColumns().add(col3);
                                tv.getColumns().add(col4);
                                tv.getColumns().add(col5);
                                tv.getColumns().add(col6);
                                tv.getColumns().add(col7);
                                tv.getColumns().add(col8);
                                tv.getColumns().add(col9);
                                while (r.next()) {
                                    tv.getItems().add(new PrescriptionDetails(r.getString(1), r.getString(2), r.getString(3), r.getString(4), r.getString(5), r.getString(6), r.getString(7), r.getString(8), new String("" + r.getInt(9))));
                                }
                                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                alert1.setTitle("Info");
                                alert1.setHeaderText("Prescription Details");
                                alert1.setContentText("Prescription Details are:");
                                GridPane.setVgrow(tv, Priority.ALWAYS);
                                GridPane.setHgrow(tv, Priority.ALWAYS);
                                GridPane gp1 = new GridPane();
                                gp1.setMaxWidth(Double.MAX_VALUE);
                                gp1.add(tv, 0, 0);
                                alert1.getDialogPane().setContent(gp1);
                                alert1.showAndWait();
                            } else {
                                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "No Prescriptions on " + dp.getValue().toString() + ".");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Cannot retrive data.");
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Select a date.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter valid details.");
            }
        }
    }







    //to navbar------------------------------------------------------------

    public void dashboard(ActionEvent event)throws Exception{
        Parent view2= FXMLLoader.load(getClass().getResource("front.fxml"));
        Scene scene2=new Scene(view2);
        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("Dashboard");
        st.setScene(scene2);
        st.show();
    }




    //-----------------------------------------------------------------------------------------


    //admin to manage all details---------------


    // Add or delete clerk

    @FXML
    private TextField clerkName, clerkPhoneNumber, clerkIdToDelete;
    @FXML
    private ToggleGroup groupAddClerk;
    @FXML
    private TextField clerkUserName, clerkPassword;
    public void adminAddOrDeleteClerk(ActionEvent event)throws Exception{
        Parent view2= FXMLLoader.load(getClass().getResource("adddeleteCLerk.fxml"));
        Scene scene2=new Scene(view2);
        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("Admin Add or Delete Clerk");
        st.setScene(scene2);
        st.show();
    }
    public void addClerkButton(ActionEvent event) throws Exception {
        String gender = null;
        if (!clerkName.getText().matches("^$") && !clerkPhoneNumber.getText().matches("^$") && !clerkUserName.getText().matches("^$") && !clerkPassword.getText().matches("^$")) {
            if (clerkPhoneNumber.getText().matches("[0-9]{10}")) {
                if (groupAddClerk.getSelectedToggle() != null) {
                    if (((RadioButton)groupAddClerk.getSelectedToggle()).getText().equals("MALE")) {
                        gender = "M";
                    } else {
                        gender = "F";
                    }
                    int val = database.addClerk(clerkName.getText(), Long.parseLong(clerkPhoneNumber.getText()), gender, clerkUserName.getText(), clerkPassword.getText());
                    if (val == -1) {
                        AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Username already exists.");
                    } else {
                        if (val == 0) {
                            AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not add clerk.");
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Clerk ID : " + val);
                            Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                            Scene scene2=new Scene(view2);
                            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                            st.setTitle("Admin");
                            st.setScene(scene2);
                            st.show();
                        }
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please select your gender");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Phone Number");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please fill all the fields.");
        }
    }
    public void deleteClerkId(ActionEvent event) throws Exception {
        if (clerkIdToDelete.getText().matches("[0-9]*") && !clerkIdToDelete.getText().matches("^$")) {
            int val = database.removeClerk(Integer.parseInt(clerkIdToDelete.getText()));
            if (val != 0) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Clerk deleted sucessfully.");
                Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                Scene scene2=new Scene(view2);
                Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                st.setTitle("Admin");
                st.setScene(scene2);
                st.show();
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Clerk does not exist.");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please enter valid id.");
        }
    }




    // Add or delete doctor

    @FXML
    private TextField doctorName, doctorSepc, doctorPhoneNumber, doctorUserName, doctorPassword, doctorIdToDelete;
    @FXML
    private ToggleGroup groupAddDoctor;
    public void adminAddOrDeleteDoctor(ActionEvent event)throws Exception{
        Parent view2= FXMLLoader.load(getClass().getResource("adddeleteDoctor.fxml"));
        Scene scene2=new Scene(view2);
        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("Admin Add or Delete Doctor");
        st.setScene(scene2);
        st.show();
    }
    public void addDoctorButton(ActionEvent event) throws Exception {
        String gender = null;
        if (!doctorName.getText().matches("^$") && !doctorSepc.getText().matches("^$") && !doctorPhoneNumber.getText().matches("^$") && !doctorUserName.getText().matches("^$") && !doctorPassword.getText().matches("^$")) {
            if (doctorPhoneNumber.getText().matches("[0-9]{10}")) {
                if (groupAddDoctor.getSelectedToggle() != null) {
                    if (((RadioButton)groupAddDoctor.getSelectedToggle()).getText().equals("MALE")) {
                        gender = "M";
                    } else {
                        gender = "F";
                    }
                    int val = database.addDoctor(doctorName.getText(), doctorSepc.getText(), Long.parseLong(doctorPhoneNumber.getText()), gender, doctorUserName.getText(), doctorPassword.getText());
                    if (val == -1) {
                        AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Username already exists.");
                    } else {
                        if (val == 0) {
                            AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not add doctor.");
                        } else {
                            AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Doctor ID : " + val);
                            Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                            Scene scene2=new Scene(view2);
                            Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                            st.setTitle("Admin");
                            st.setScene(scene2);
                            st.show();
                        }
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please select your gender");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Phone Number");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please fill all the fields.");
        }
    }
    public void deleteDoctorId(ActionEvent event) throws Exception {
        if(doctorIdToDelete.getText().matches("[0-9]*") && !doctorIdToDelete.getText().matches("^$")) {
            int val = database.removeDoctor(Integer.parseInt(doctorIdToDelete.getText()));
            if (val != 0) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Doctor deleted sucessfully.");
                Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                Scene scene2=new Scene(view2);
                Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                st.setTitle("Admin");
                st.setScene(scene2);
                st.show();
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Doctor does not exist.");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please enter valid id.");
        }
    }






    // Add or delete nurse

    @FXML
    private TextField nurseName, nursePhoneNumber, nurseRoomNumber, nurseUserName, nursePassword, nurseShiftStart, nurseShiftEnd, nurseIdToDelete;
    @FXML
    private ToggleGroup groupAddNurse;
    public void adminAddOrDeleteNurse(ActionEvent event)throws Exception{
        Parent view2= FXMLLoader.load(getClass().getResource("adddeleteNurse.fxml"));
        Scene scene2=new Scene(view2);
        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("Admin Add or Delete Nurse");
        st.setScene(scene2);
        st.show();
    }
    public void addNurseButton(ActionEvent event) throws Exception {
        String gender;
        if (!nurseName.getText().matches("^$") && !nursePhoneNumber.getText().matches("^$") && !nurseRoomNumber.getText().matches("^$") && !nurseUserName.getText().matches("^$") && !nursePassword.getText().matches("^$") && !nurseShiftStart.getText().matches("^$") && !nurseShiftEnd.getText().matches("^$")) {
            if (nursePhoneNumber.getText().matches("[0-9]{10}")) {
                if (nurseRoomNumber.getText().matches("[0-9]*") && nurseShiftStart.getText().matches("[0-9]*") && nurseShiftEnd.getText().matches("[0-9]*")) {
                    if (groupAddNurse.getSelectedToggle() != null) {
                        if (((RadioButton)groupAddNurse.getSelectedToggle()).getText().equals("MALE")) {
                            gender = "M";
                        } else {
                            gender = "F";
                        }
                        int val = database.addNurse(nurseName.getText(), gender, Long.parseLong(nursePhoneNumber.getText()), Integer.parseInt(nurseRoomNumber.getText()), Integer.parseInt(nurseShiftStart.getText()), Integer.parseInt(nurseShiftEnd.getText()), nurseUserName.getText(), nursePassword.getText());
                        if (val == -1) {
                            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Username already exists.");
                        } else {
                            if (val == 0) {
                                AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not add nurse.");
                            } else {
                                AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Nurse ID : " + val);
                                Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                                Scene scene2=new Scene(view2);
                                Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                                st.setTitle("Admin");
                                st.setScene(scene2);
                                st.show();
                            }
                        }
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please select your gender");
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Room Number or Shift Start or Shift End");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Enter Valid Phone Number");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please fill all the fields.");
        }
    }
    public void deleteNurseId(ActionEvent event) throws Exception {
        if(nurseIdToDelete.getText().matches("[0-9]*") && !nurseIdToDelete.getText().matches("^$")) {
            int val = database.removeNurse(Integer.parseInt(nurseIdToDelete.getText()));
            if (val != 0) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Nurse deleted sucessfully.");
                Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                Scene scene2=new Scene(view2);
                Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                st.setTitle("Admin");
                st.setScene(scene2);
                st.show();
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Nurse does not exist.");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please enter valid id.");
        }
    }







    // Manage medicine and room

    @FXML
    private TextField roomCost, tradeNameMedi, priceMedi, roomNumberToDelete, tradeNameToDelete;
    @FXML
    private ComboBox<String> chooseType;
    public void manageMedicineAndRoom(ActionEvent event)throws Exception {
        Parent view2 = FXMLLoader.load(getClass().getResource("manage.fxml"));
        Scene scene2 = new Scene(view2);
        Stage st = (Stage) ((Node) event.getSource()).getScene().getWindow();
        st.setTitle("Admin Manage medicine and Room");
        st.setScene(scene2);
        st.show();
    }
    public void addRoom(ActionEvent event) throws Exception {
        if (!roomCost.getText().matches("^$")) {
            if (roomCost.getText().matches("[0-9]*")) {
                if (chooseType.getValue() != null) {
                    int val = database.addRoom(chooseType.getValue(), Integer.parseInt(roomCost.getText()));
                    if (val != 0) {
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Room ID : " + val);
                        Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                        Scene scene2=new Scene(view2);
                        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                        st.setTitle("Admin");
                        st.setScene(scene2);
                        st.show();
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not add room.");
                    }
                } else {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please choose a room type.");
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Plese enter vaild cost.");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please fill all the fields.");
        }
    }
    public void deleteRoom(ActionEvent event) throws Exception {
        if (!roomNumberToDelete.getText().matches("^$") && roomNumberToDelete.getText().matches("[0-9]*")) {
            int val = database.removeRoom(Integer.parseInt(roomNumberToDelete.getText()));
            if (val == 0) {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Room does not exist.");
            } else {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Room deleted sucessfully.");
                Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                Scene scene2=new Scene(view2);
                Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                st.setTitle("Admin");
                st.setScene(scene2);
                st.show();
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Plese enter vaild id.");
        }
    }
    public void addMedicine(ActionEvent event) throws Exception {
        if (!tradeNameMedi.getText().matches("^$") && !priceMedi.getText().matches("^$")) {
            if (priceMedi.getText().matches("[0-9]+[.]?[0-9]+")) {
                int val = database.addMedicine(tradeNameMedi.getText(), Float.parseFloat(priceMedi.getText()));
                if (val == -1) {
                    AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Medicine already exists.");
                } else {
                    if (val == 0) {
                        AlertHelper.showAlert(Alert.AlertType.ERROR, ((Node)event.getSource()).getScene().getWindow(), "Error", "Could not add medicine.");
                    } else {
                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Medicine added successfully.");
                        Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                        Scene scene2=new Scene(view2);
                        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                        st.setTitle("Admin");
                        st.setScene(scene2);
                        st.show();
                    }
                }
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please enter valid price.");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Please fill all the fields.");
        }
    }
    public void deleteMedicine(ActionEvent event) throws Exception {
        if (!tradeNameToDelete.getText().matches("^$")) {
            int val = database.removeMedicine(tradeNameToDelete.getText());
            if (val != 0) {
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, ((Node)event.getSource()).getScene().getWindow(), "Info", "Medicine deleted sucessfully.");
                Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
                Scene scene2=new Scene(view2);
                Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
                st.setTitle("Admin");
                st.setScene(scene2);
                st.show();
            } else {
                AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Medicine does not exist.");
            }
        } else {
            AlertHelper.showAlert(Alert.AlertType.WARNING, ((Node)event.getSource()).getScene().getWindow(), "Warning", "Plese enter medicine name.");
        }
    }
    public void closeButton(ActionEvent event) throws Exception {
        Parent view2= FXMLLoader.load(getClass().getResource("front.fxml"));
        Scene scene2=new Scene(view2);
        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("Dashboard");
        st.setScene(scene2);
        st.show();
    }
    public void backButton(ActionEvent event) throws Exception {
        Parent view2= FXMLLoader.load(getClass().getResource("Admin.fxml"));
        Scene scene2=new Scene(view2);
        Stage st=(Stage)((Node)event.getSource()).getScene().getWindow();
        st.setTitle("Admin");
        st.setScene(scene2);
        st.show();
    }
}