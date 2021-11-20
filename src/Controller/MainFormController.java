package Controller;

import Model.PrintDetails;
import Model.ServicePersons;
import Model.Services;
import Util.TableLoadEvent;
import Util.ValidationUtil;
import animatefx.animation.Flash;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class MainFormController implements TableLoadEvent, Initializable {

    public JFXButton btnLogin;
    public JFXButton btnSignUp;
    public JFXTextField txtNic;
    public JFXButton btnSearch;
    public Label lblName;
    public JFXComboBox<String> cmbService;
    public JFXButton btnplus;
    public JFXComboBox<String> cmbService2;
    public Label lblService;
    public JFXButton btnMakeAppoinment;
    public JFXButton btnCancelAppoinment;
    public ImageView imgView;
    public Label lblDate;
    public JFXTextField txtname;
    public JFXTextField txtService;
    public FontAwesomeIconView searchLogo;
    public Label lblTotal;
    public JFXTextField txtTotal;
    public StackPane MystackPane;
    public JFXTextField txtAppoinmentNo;
    public Label lblAppNo;
    public JFXComboBox<String> cmbServicePerson;
    public Label lblTime;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^(([1-9][0-9]{8})[v]|[1-9][0-9]{11})$");

    private void storeValidations() {
        map.put(txtNic,idPattern);
    }


    public void logInOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("../View/LogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) btnLogin.getScene().getWindow();
        window.setScene(new Scene(load));


    }

    @Override
    public void loadData() {

    }

    String firstService=null;
    String secondService=null;
    String secondPersonName=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDate();
        visibleDisable();
        storeValidations();
        loadServices();
        setAppoinmentId();
        try {
            loadServicePersons();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        cmbService.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
          if (newValue.isEmpty()){

          }else{
              firstService=newValue;
              btnMakeAppoinment.setVisible(true);
              btnCancelAppoinment.setVisible(true);
          }
        });

        cmbService2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){

            }else{
                secondService=newValue;
            }
        });

        cmbServicePerson.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()){

            }else{
                secondPersonName=newValue;
            }
        });





    }

    private void visibleDisable(){
        btnSearch.setDisable(true);
        btnMakeAppoinment.setVisible(false);
        btnCancelAppoinment.setVisible(false);
        btnplus.setVisible(false);

        cmbService.setVisible(false);
        cmbService2.setVisible(false);
        cmbServicePerson.setVisible(false);

        lblTotal.setVisible(false);
        txtTotal.setVisible(false);
        txtname.setVisible(false);
        txtService.setVisible(false);
        lblName.setVisible(false);
        lblService.setVisible(false);
        lblAppNo.setVisible(false);
        txtAppoinmentNo.setVisible(false);
    }

    public void SignUpOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../View/CustomerSignupForm.fxml"));
        Parent parent = loader.load();
        CustomerSignupFormController controller = loader.<CustomerSignupFormController>getController();
        controller.setEvent(this);
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void BtnPlusOnAction(ActionEvent event) {
        cmbService2.setVisible(true);
    }

    public void SearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM customer WHERE cust_NIC=?");
        stm.setObject(1,txtNic.getText());
        ResultSet rst = stm.executeQuery();
        Customer c=null;
        while (rst.next()){
            c=new Customer (rst.getString("cust_NIC"),rst.getString("cust_Name"),rst.getInt("cust_Age"));
            txtService.setText(rst.getString("cust_Hair_Style"));
        }
        if(c!=null){
            lblName.setVisible(true);
            txtname.setVisible(true);
            lblService.setVisible(true);
            txtService.setVisible(true);
            txtname.setText(c.getName());
            cmbService.setVisible(true);
            btnplus.setVisible(true);
            lblAppNo.setVisible(true);
            txtAppoinmentNo.setVisible(true);
            cmbServicePerson.setVisible(true);
        }else{
            new AlertTypes().CustomerNotExistAlert(MystackPane);
            txtNic.clear();
            new Flash(btnSignUp).play();
        }

    }

    private void loadServices(){
        try {
            List<String>services = getServiceNames();
            cmbService.getItems().addAll(services);
            cmbService2.getItems().addAll(services);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    List<String> SortedArrays=new ArrayList<>();
    public void loadServicePersons() throws SQLException, ClassNotFoundException {
        List<ServicePersons> EmpList=new ArrayList<>();
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Id,emp_Name FROM employee WHERE emp_Role='Barber' ").executeQuery();
        while(rst.next()){
            EmpList.add(new ServicePersons(rst.getString(1),rst.getString(2)));

        }

        List<String> loadedIDs=loadEmpIds();
        for (int i = 0; i <loadedIDs.size() ; i++) {
            for (int j = 0; j <EmpList.size() ; j++) {
                if(loadedIDs.get(i).equalsIgnoreCase(EmpList.get(j).getEmpId())){
                    SortedArrays.add(EmpList.get(j).getEmpName());
                }
            }
        }

        cmbServicePerson.getItems().addAll(SortedArrays);



    }
    public List<String> loadEmpIds() throws SQLException, ClassNotFoundException {

        List<String> EmpPresent=new ArrayList<>();
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("select employee_Id from attendance where `status`='1' and date=?");
        stm.setObject(1,lblDate.getText());
        ResultSet rst = stm.executeQuery();

        while(rst.next()){
            EmpPresent.add(rst.getString(1));
        }
        return EmpPresent;

    }

    public List<String> getServiceNames() throws SQLException, ClassNotFoundException {
        ResultSet rst = DbConnection.getInstance().
                getConnection().prepareStatement("SELECT * FROM service").executeQuery();
        List<String> ids = new ArrayList<>();
        while (rst.next()){
            ids.add(
                    rst.getString(2)
            );
        }
        return ids;
    }

    private void loadDate(){
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void textFields_Key_Pressed(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSearch);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
                new Flash(errorText).play();
            } else if (response instanceof Boolean) {

            }
        }
    }

    private void setAppoinmentId() {
        try {

            txtAppoinmentNo.setText(new AppoinmentController().getAppointmentId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    HashMap<String, Object> hashMap=new HashMap<>();
    PrintDetails p1;

    public void MakeAppoinmentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException, InterruptedException {

        if(secondPersonName==null) {
            new AlertTypes().failAlert(MystackPane);
            return;
        }


        String EmpId=new AppoinmentController().GetEmpName(secondPersonName);
        if(firstService!=null){
            //get service details
           Services s1=new AppoinmentController().serviceDetails(firstService);
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("update  customer set cust_Hair_Style=? where cust_NIC=?");
            stm.setObject(1,firstService);
            stm.setObject(2,txtNic.getText());
            stm.executeUpdate();

            //update appointment details
            if(new AppoinmentController().placeAppoinment(txtAppoinmentNo.getText(),txtNic.getText(),EmpId,
                    Double.parseDouble(txtTotal.getText()),0.00,lblDate.getText())){
                new Alert(Alert.AlertType.INFORMATION,"Appointment Successful").showAndWait();
               // new AlertTypes().SuccessAlert(MystackPane);


            }else{
                new AlertTypes().failAlert(MystackPane);
            }


          //add services
           new AppoinmentController().addtoServiceDetails(s1.getSprice(),txtAppoinmentNo.getText(),s1.getSId());
        }
        if(secondService!=null){
            Services s1=new AppoinmentController().serviceDetails(secondService);
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("update  customer set cust_Hair_Style=? where cust_NIC=?");
            stm.setObject(1,secondService);
            stm.setObject(2,txtNic.getText());
            stm.executeUpdate();

            //add services
            new AppoinmentController().addtoServiceDetails(s1.getSprice(),txtAppoinmentNo.getText(),s1.getSId());
        }


        List<PrintDetails> printDetails = getPrintDetails(txtAppoinmentNo.getText());
        if(printDetails.size()>1) {
            for (int i = 0; i < printDetails.size()-1; i++) {
                if (printDetails.get(i).getAppNo().equals(printDetails.get(i + 1).getAppNo())) {
                    p1=new PrintDetails(printDetails.get(i).getAppNo(), printDetails.get(i).getNic(), printDetails.get(i).getEmpId(), printDetails.get(i).getPrice(),
                            printDetails.get(i).getDiscount() + printDetails.get(i + 1).getDiscount(), printDetails.get(i).getServiceName()+" + "+printDetails.get(i + 1).getServiceName());

                }
            }
        }else{
            for (PrintDetails details : printDetails) {
                p1=new PrintDetails(details.getAppNo(),details.getNic(),details.getEmpId(),details.getPrice(),details.getDiscount(),details.getServiceName());
            }

        }


        hashMap.put("AppNo",p1.getAppNo());
        hashMap.put("Nic",p1.getNic());
        hashMap.put("EmpId",p1.getEmpId());
        hashMap.put("Price",p1.getPrice());
        hashMap.put("Discount",p1.getDiscount());
        hashMap.put("ServiceName",p1.getServiceName());

        try {
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/Appoinment_Receipt.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(load);
            JRDataSource dataSource=new JREmptyDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,hashMap,dataSource);
            JasperViewer.viewReport(jasperPrint,false);

        } catch (JRException e) {
            e.printStackTrace();
        }



        URL resource = getClass().getResource("../View/MainForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) btnLogin.getScene().getWindow();
        window.setScene(new Scene(load));


    }
    public List<PrintDetails> getPrintDetails(String AppNo) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("select appoinment.app_No, appoinment.customer_NIC ,appoinment.employee_Id,appoinment.price,appoinment.discount,service.service_Name from appoinment inner join service_details on appoinment.app_No=service_details.app_No inner join service on service_details.service_Id=service.service_Id where appoinment.app_No=?");
        stm.setObject(1,AppNo);
        ResultSet rst = stm.executeQuery();
        List<PrintDetails> details=new ArrayList<>();
        while (rst.next()){
            details.add(new PrintDetails(rst.getString(1),rst.getString(2),rst.getString(3),rst.getDouble(4),rst.getDouble(5),rst.getString(6)));
        }
        return details;

    }


    public void CancelAppoinmentOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        URL resource = getClass().getResource("../View/MainForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) btnLogin.getScene().getWindow();
        window.setScene(new Scene(load));

    }

    double Total;
    public void SelectServiceOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        lblTotal.setVisible(true);
        txtTotal.setVisible(true);
        if(!cmbService.getSelectionModel().getSelectedItem().isEmpty()){
            Total=new AppoinmentController().getTotal(cmbService.getSelectionModel().getSelectedItem());
            txtTotal.setText(String.valueOf(Total)+"0");
            System.out.println(Total);
        }

    }

    public void SelectServiceOnAction2(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(!cmbService2.getSelectionModel().getSelectedItem().isEmpty()){
            Total+=new AppoinmentController().getTotal(cmbService2.getSelectionModel().getSelectedItem());
            txtTotal.setText(String.valueOf(Total)+"0");
            System.out.println(Total);
        }
    }

    public void SelectServicePersonOnAction(ActionEvent event) {
    }
}
