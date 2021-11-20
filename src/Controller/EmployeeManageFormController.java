package Controller;

import Model.Employee;
import Util.TableLoadEvent;
import Util.ValidationUtil;
import animatefx.animation.Flash;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tm.EmoloyeeTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class EmployeeManageFormController implements Initializable, TableLoadEvent {


    public TableView<EmoloyeeTM> tblEmployee;
    public TableColumn<Object, Object> colId;
    public TableColumn<Object, Object> colName;
    public TableColumn<Object, Object> colContact;
    public TableColumn<Object, Object> colAddress;
    public TableColumn<Object, Object> colSalary;
    public JFXTextField txtEmpID;
    public JFXTextField txtEmpName;
    public JFXTextField txtEmpRole;
    public JFXTextField txtEmpAddress;
    public JFXTextField txtEmpSalary;
    public JFXButton btnSearch;
    public JFXTextField txtEmpContact;
    public JFXTextField txtEmpPassword;
    public StackPane MyStackPane;
    public JFXButton btnUpdate;
    public JFXButton btnDelete;
    ObservableList<EmoloyeeTM> oblist= FXCollections.observableArrayList();

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    LinkedHashMap<JFXTextField, Pattern> map2 = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^([ABC])[1-9]$");
    Pattern namePattern = Pattern.compile("^[A-z ]{3,20}$");
    Pattern addressPattern = Pattern.compile("^[A-z0-9/ ]{5,30}$");
    Pattern ContactPattern = Pattern.compile("^(077|076|075|072|071|070|078)[-]?[0-9]{7}$");
    Pattern RolePattern = Pattern.compile("^(Admin|Barber|Cashier)$");
    Pattern SalaryPattern = Pattern.compile("^[1-9][0-9]*([.][0-9]{2})?$");
    Pattern PasswordPattern = Pattern.compile("^[A-z0-9@]{4,}$");

    public void AddEmployeeOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../View/AddEmployeeForm.fxml"));
        Parent parent = loader.load();
        AddEmployeeFormController controller = loader.<AddEmployeeFormController>getController();
        controller.setEvent(this);
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnUpdate.setDisable(true);
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("Salary"));


        tblEmployee.setItems(loadDataTable());
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtEmpID,idPattern);
        map2.put(txtEmpName,namePattern);
        map2.put(txtEmpAddress,addressPattern);
        map2.put(txtEmpContact,ContactPattern);
        map2.put(txtEmpRole,RolePattern);
        map2.put(txtEmpSalary,SalaryPattern);
        map2.put(txtEmpPassword,PasswordPattern);
    }

    public void clear(){
        txtEmpID.clear();
        txtEmpName.clear();
        txtEmpAddress.clear();
        txtEmpContact.clear();
        txtEmpRole.clear();
        txtEmpSalary.clear();
        txtEmpPassword.clear();
    }

    public ObservableList<EmoloyeeTM> loadDataTable(){
        try {
            List<EmoloyeeTM> allEmployees = EmployeeController.getAllEmployees();
            for (EmoloyeeTM allEmployee : allEmployees) {
                oblist.add(new EmoloyeeTM(
                        allEmployee.getId(),allEmployee.getName(),allEmployee.getContact(),
                        allEmployee.getAddress(),allEmployee.getSalary()
                ));
            }
            return oblist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

      return null;

    }

    @Override
    public void loadData() {
        tblEmployee.getItems().clear();
        tblEmployee.setItems(loadDataTable()
        );
    }

    public void SearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(!txtEmpID.getText().isEmpty()){
            Employee rst = new EmployeeController().getEmployee(txtEmpID.getText());

          if(rst==null){
              new AlertTypes().noDataFound(MyStackPane);
              return;
          }
            txtEmpName.setText(rst.getName());
            txtEmpRole.setText(rst.getRole());
            txtEmpContact.setText(rst.getContact());
            txtEmpAddress.setText(rst.getAddress());
            txtEmpSalary.setText(String.valueOf(rst.getSalary())+"0");
            txtEmpPassword.setText(rst.getPassword());
        }else{
            new AlertTypes().noDataFound(MyStackPane);
        }
    }

    public void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(txtEmpName.getText().isEmpty()||txtEmpPassword.getText().isEmpty()||txtEmpAddress.getText().isEmpty()
                ||txtEmpContact.getText().isEmpty()||txtEmpSalary.getText().isEmpty()||txtEmpRole.getText().isEmpty()){
            new AlertTypes().noDataFound(MyStackPane);
        }else{
            Employee e1=new Employee(txtEmpRole.getText(),txtEmpID.getText(),txtEmpName.getText(),txtEmpAddress.getText(),
                    txtEmpContact.getText(),Double.parseDouble(txtEmpSalary.getText()),txtEmpPassword.getText());

            if(new EmployeeController().updateEmployee(e1)){
                new AlertTypes().SuccessAlert(MyStackPane);
                tblEmployee.getItems().clear();
                clear();
                tblEmployee.setItems(loadDataTable());
                btnUpdate.setDisable(true);
            }else{
                new AlertTypes().failAlert(MyStackPane);
            }
        }
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
    ButtonType Yes=new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType No=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);
    public void deleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException {
       EmoloyeeTM temp=tblEmployee.getSelectionModel().getSelectedItem();

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove the item",Yes,No);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.orElse(Yes)==No){
            return;
        }

       if(temp==null){
           new AlertTypes().selectRow(MyStackPane);
       }else{
            if(new EmployeeController().deleteEmployee(temp)){
                new AlertTypes().SuccessAlert(MyStackPane);
                tblEmployee.getItems().clear();
                tblEmployee.setItems(loadDataTable());
            }else{
                new AlertTypes().failAlert(MyStackPane);
            }
       }
    }

    public void textFields_Key_Changed(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map2,btnUpdate);

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
