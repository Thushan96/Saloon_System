package Controller;

import Model.Employee;
import Util.TableLoadEvent;
import Util.ValidationUtil;
import animatefx.animation.FadeIn;
import animatefx.animation.Flash;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import tm.Customer;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CustomerSignupFormController implements Initializable {
    public StackPane MyStackPane;
    public JFXTextField txtNic;
    public JFXTextField txtName;
    public JFXTextField txtAge;
    public JFXButton btnSave;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^(([1-9][0-9]{8})[v]|[1-9][0-9]{11})$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern agePattern = Pattern.compile("^[1-9][0-9]$");



    private TableLoadEvent loadEvent;

    public void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException {
        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT cust_NIC FROM customer").executeQuery();
        while (rst.next()){
            if (txtNic.getText().equals(rst.getString(1))){
                new AlertTypes().CustomerExistAlert(MyStackPane);
                txtNic.setStyle("-fx-text-fill: red");
                new FadeIn(txtNic).play();
                return;
            }
        }
        Customer c1=new Customer(txtNic.getText(),txtName.getText(),Integer.parseInt(txtAge.getText()));
        if(SaveCustomer(c1)){
            new AlertTypes().SuccessAlert(MyStackPane);
            clear();
            loadEvent.loadData();
        }else{
            new AlertTypes().failAlert(MyStackPane);
            clear();
        }



    }
    public boolean SaveCustomer(Customer c) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer SET cust_NIC=?,cust_Name=?,cust_Age=?");
        stm.setObject(1, c.getNic());
        stm.setObject(2, c.getName());
        stm.setObject(3, c.getAge());
        return stm.executeUpdate() > 0;
    }

    public void ClearOnAction(ActionEvent event) {
        clear();
    }
    private void clear(){
        txtNic.clear();
        txtName.clear();
        txtAge.clear();
    }

    public void setEvent(TableLoadEvent loadEvent){
        this.loadEvent=loadEvent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtNic,idPattern);
        map.put(txtName,namePattern);
        map.put(txtAge,agePattern);
    }

    public void textFields_Key_Pressed(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map,btnSave);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
                new Flash(errorText).play();
            } else if (response instanceof Boolean) {

            }
        }
    }
}
