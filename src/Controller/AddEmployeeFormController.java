package Controller;

import Model.Employee;
import Util.TableLoadEvent;
import Util.ValidationUtil;
import animatefx.animation.FadeIn;
import animatefx.animation.Flash;
import animatefx.animation.Flip;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddEmployeeFormController implements Initializable {

    public JFXTextField txtEmpPassword;
    public JFXTextField txtEmpSalary;
    public JFXTextField txtEmpRole;
    public JFXTextField txtEmpContact;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpId;
    public StackPane mystacPane;
    public JFXButton btnSave;
    public AnchorPane MyAnchorPane;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^([ABC])[1-9]$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{5,30}$");
    Pattern ContactPattern = Pattern.compile("^(077|076|075|072|071|070|078)[-]?[0-9]{7}$");
    Pattern RolePattern = Pattern.compile("^(Admin|Barber|Cashier)$");
    Pattern SalaryPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern PasswordPattern = Pattern.compile("^[A-z0-9@]{4,}$");

    private TableLoadEvent loadEvent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtEmpId,idPattern);
        map.put(txtEmpName,namePattern);
        map.put(txtEmpAddress,addressPattern);
        map.put(txtEmpContact,ContactPattern);
        map.put(txtEmpRole,RolePattern);
        map.put(txtEmpSalary,SalaryPattern);
        map.put(txtEmpPassword,PasswordPattern);
    }

    public void clear(){
        txtEmpId.clear();
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpContact.clear();
        txtEmpRole.clear();
        txtEmpSalary.clear();
        txtEmpPassword.clear();
    }


    public void ClearOnAction(ActionEvent event) {
        clear();
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
    public void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException {
        Employee e1=new Employee(txtEmpRole.getText(),txtEmpId.getText(),txtEmpName.getText(),
                txtEmpAddress.getText(),txtEmpContact.getText(),Double.parseDouble(txtEmpSalary.getText()),txtEmpPassword.getText());

        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Id FROM employee").executeQuery();
        while (rst.next()){
            if (txtEmpId.getText().equals(rst.getString(1))){
               new AlertTypes().DataExistAlert(mystacPane);
               txtEmpId.setStyle("-fx-text-fill: red");
               new FadeIn(txtEmpId).play();
                return;
            }
        }

        if(new EmployeeController().SaveEmployee(e1)){
            new AlertTypes().SuccessAlert(mystacPane);
            clear();
            loadEvent.loadData();
            Stage window=(Stage)MyAnchorPane.getScene().getWindow();
            window.close();
        }else{
            new AlertTypes().failAlert(mystacPane);
            clear();
        }

    }
    public void setEvent(TableLoadEvent loadEvent){
        this.loadEvent=loadEvent;
    }
}
