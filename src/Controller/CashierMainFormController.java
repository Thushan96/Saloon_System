package Controller;

import Model.ServiceDetails;
import Util.ValidationUtil;
import animatefx.animation.Flash;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.ServiceDetailsTM;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

public class CashierMainFormController implements Initializable {

    public JFXTextField txtAppNo;
    public JFXButton btnSearch;
    public JFXTextField txtServicePeson;
    public JFXButton btnPay;
    public Label lblTotal;
    public TableView<ServiceDetailsTM> tblAppoinment;
    public TableColumn<Object, Object> colAppNo;
    public TableColumn<Object, Object> colServiceName;
    public TableColumn<Object, Object> colDiscount;
    public TableColumn<Object, Object> colPrice;
    public TableColumn<Object, Object> colDelete;
    public JFXButton btnDelete;
    public StackPane MyStackPane;
    public JFXTextField txtAmount;

    ObservableList<ServiceDetailsTM> Oblist= FXCollections.observableArrayList();
    int cartSelectedRowForRemove = -1;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^[A][-][0-9]{3,}$");

    LinkedHashMap<JFXTextField,Pattern> map2=new LinkedHashMap<>();
    Pattern amountPattern = Pattern.compile("^[1-9][0-9]+(.00)?$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    colAppNo.setCellValueFactory(new PropertyValueFactory<>("AppNo"));
    colServiceName.setCellValueFactory(new PropertyValueFactory<>("ServiceName"));
    colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
    colDiscount.setCellValueFactory(new PropertyValueFactory<>("Discount"));

        tblAppoinment.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            cartSelectedRowForRemove = (int) newValue;
        });

        btnSearch.setDisable(true);
        btnPay.setDisable(true);
        storeValidations();

    }

    private void storeValidations() {
        map.put(txtAppNo,idPattern);
        map2.put(txtAmount,amountPattern);


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

    public void textFeildKeyChange(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map2,btnPay);


        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
                new Flash(errorText).play();
            } else if (response instanceof Boolean) {

            }
        }
    }

    String tempService1=null;
    String tempService2=null;

    List<ServiceDetailsTM> ListTm=new ArrayList<>();
    public void searchOnActiob(ActionEvent event) throws SQLException, ClassNotFoundException {
        //Empty the table
        if (tblAppoinment.getItems().isEmpty()){

        }else{
            ListTm.clear();
            Oblist.removeAll();
            tblAppoinment.getItems().clear();
            tblAppoinment.refresh();
            ttl=0.0;
            btnPay.setDisable(true);
        }
        //check already paid or not
        boolean status = getStatus(txtAppNo.getText());
        System.out.println(status);
        if(status){
           new AlertTypes().failAlert(MyStackPane);
           return;
        }else {

            //Search service person name
            String PersonName = new AppoinmentController().SearchEmpId(txtAppNo.getText());
            txtServicePeson.setText(PersonName);
            //search discount
            Double dicount = new AppoinmentController().SearchDicount(txtAppNo.getText());
            //search service details
            List<ServiceDetails> servicesDetailTable = new ServiceController().getServicesDetailTable(txtAppNo.getText());

            List<String> nameList = new ArrayList<>();

            String[] name = new String[servicesDetailTable.size()];
            //Search service name
//        for (ServiceDetails serviceDetails : servicesDetailTable) {
//            nameList = new ServiceController().GetServiceName(serviceDetails.getSid());
//            System.out.println(nameList.toString());
//            System.out.println(nameList.size());
//        }

            for (int i = 0; i < name.length; i++) {
                name[i] = String.valueOf(new ServiceController().GetServiceName(servicesDetailTable.get(i).getSid()));
                tempService1=name[i]+" ";
            }


//        for (ServiceDetailsTM serviceDetailsTM : ListTm) {
//            ListTm.add(new ServiceDetailsTM(servicesDetailTable. ))
//        }

            //add to final tm list
            for (int i = 0; i < name.length; i++) {
                ListTm.add(new ServiceDetailsTM(servicesDetailTable.get(i).getAppNo(), name[i], servicesDetailTable.get(i).getSid(),
                        servicesDetailTable.get(i).getPrice(), dicount));
            }


            Oblist.addAll(ListTm);
            tblAppoinment.setItems(Oblist);

            calculateCost();
            btnSearch.setDisable(true);
            btnPay.setDisable(true);
        }


    }

    ButtonType Yes=new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
    ButtonType No=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

    public void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        ServiceDetailsTM temp=tblAppoinment.getSelectionModel().getSelectedItem();
        String ServiceID=temp.getServiceId();
        String AppNo=temp.getAppNo();

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove the item",Yes,No);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.orElse(Yes)==No){
            return;
        }


        if (cartSelectedRowForRemove==-1){
            new AlertTypes().selectRow(MyStackPane);
        }else{

            if(deleteService(ServiceID,AppNo)){
                new AlertTypes().SuccessAlert(MyStackPane);
            }else{
                new AlertTypes().failAlert(MyStackPane);
                return;
            }
            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("delete from service_details where app_No=? and service_Id=?");
            stm.setObject(1,txtAppNo.getText());
            stm.setObject(2,temp.getServiceId());
            stm.executeUpdate();

            Oblist.remove(cartSelectedRowForRemove);
            ttl=0;
            calculateCost();
            tblAppoinment.refresh();
        }
    }
    double ttl=0;

    void calculateCost(){
        for (ServiceDetailsTM serviceDetailsTM:Oblist
        ) {
            ttl+=serviceDetailsTM.getPrice();
        }
        lblTotal.setText(ttl+"0/=");
    }
    public boolean deleteService(String ServiceId,String AppNo) throws SQLException, ClassNotFoundException {
        //remove from service details
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("DELETE FROM service_details WHERE service_Id=? AND app_No=?");
        stm.setString(1,ServiceId);
        stm.setString(2,AppNo);
        return stm.executeUpdate()>0;

    }
    int paymentId = 0;
    String AppNo = null;
    double price = 0;
    Date date = null;
    HashMap<String, Object> hashMap=new HashMap<String, Object>();
    double balance;
    String price2 = null;
    double cash=0;
    public void payOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {


        if(Double.parseDouble(ttl+"0")<=Double.parseDouble(txtAmount.getText())){
            balance=ttl-Double.parseDouble(txtAmount.getText());
        }else{
            new AlertTypes().failAlert(MyStackPane);
            return;
        }

        updateAppoinment(txtAppNo.getText());

        if(txtAmount.getText().isEmpty()){
            new AlertTypes().failAlert(MyStackPane);
            return;
        }


        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("insert into payments set appNo=?,payment=?");
        stm.setString(1,txtAppNo.getText());
        stm.setObject(2,ttl);

        if (stm.executeUpdate()>0){
            getDetails();
            for (ServiceDetailsTM serviceDetailsTM:Oblist
            ) {
                price2=String.valueOf(serviceDetailsTM.getPrice());
            }
            cash=Double.parseDouble(txtAmount.getText());


            hashMap.put("BillId",paymentId);
            hashMap.put("total",ttl);
            hashMap.put("cash",cash);
            hashMap.put("Balance",balance);
            hashMap.put("SerachId",txtAppNo.getText());


            try {
                JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/AppoinmentReceipt.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(load);
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,hashMap,DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint,false);

            } catch (JRException e) {
                e.printStackTrace();
            }

            System.out.println(paymentId);
            System.out.println(AppNo);
            System.out.println(price);
            System.out.println(date);

            new AlertTypes().SuccessAlert(MyStackPane);
            tblAppoinment.getItems().clear();
            lblTotal.setText(null);
            ttl=0.0;
            btnSearch.setDisable(true);
            txtAmount.setText(null);
            txtServicePeson.setText(null);
            txtAppNo.setText(null);
            Oblist.removeAll();
            ListTm.clear();
        }else{
            new AlertTypes().failAlert(MyStackPane);
        }

    }
    public void updateAppoinment(String AppNo) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("UPDATE appoinment SET paid=1 WHERE app_No=?");
        stm.setObject(1,AppNo);
        stm.executeUpdate();
    }
    public boolean getStatus(String AppNo) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("select paid from appoinment where app_No=?");
        stm.setObject(1,AppNo);
        ResultSet rst = stm.executeQuery();
        boolean b = false;
        while (rst.next()){
            b=rst.getBoolean(1);
        }

        return b;

    }

    public void getDetails() throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DbConnection.getInstance().getConnection().prepareStatement("select * from payments where appNo=?");
        statement.setObject(1,txtAppNo.getText());
        ResultSet rst = statement.executeQuery();
        while (rst.next()){
            paymentId=rst.getInt(1);
            AppNo=rst.getString(2);
            price=rst.getDouble(3);
            date=rst.getDate(4);
        }
    }

}
