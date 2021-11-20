package Controller;

import Model.Item;
import Model.Services;
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
import tm.ItemTM;
import tm.ServiceTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ServiceManageFormController implements Initializable, TableLoadEvent {
    public StackPane MyStackPane;
    public JFXTextField txtScode;
    public JFXTextField txtSname;
    public JFXTextField txtSdescription;
    public JFXButton btnSearch;
    public JFXButton btnUpdate;
    public TableView<ServiceTM> tblService;
    public TableColumn<Object, Object> colCode;
    public TableColumn<Object, Object> colName;
    public TableColumn<Object, Object> colDescription;
    public TableColumn<Object, Object> colSprice;
    public JFXButton btnDelete;
    public JFXButton btnAdd;
    public JFXTextField txtSprice;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    LinkedHashMap<JFXTextField, Pattern> map2 = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^[S][0-9]{3}$");
    Pattern namePattern = Pattern.compile("^[A-z0-1 ]{3,30}$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]+([.]00)*$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]+[.]*$");

    public void SearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(!txtScode.getText().isEmpty()){
            Services rst = new ServiceController().getServices(txtScode.getText());

            if(rst==null){
                new AlertTypes().noDataFound(MyStackPane);
                return;
            }
            txtScode.setText(rst.getSId());
            txtSname.setText(rst.getSName());
            txtSprice.setText(String.valueOf(rst.getSprice())+"0");
            txtSdescription.setText(rst.getSdescription());
        }else{
            new AlertTypes().noDataFound(MyStackPane);
        }
    }

    public void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException {
        if(txtSname.getText().isEmpty()||txtSprice.getText().isEmpty()||txtScode.getText().isEmpty()) {
            new AlertTypes().noDataFound(MyStackPane);
        }else{
            Services s1=new Services(txtScode.getText(),txtSname.getText(),Double.parseDouble(txtSprice.getText()),txtSdescription.getText());

            if(new ServiceController().updateService(s1)){
                new AlertTypes().SuccessAlert(MyStackPane);
                clear();
                tblService.getItems().clear();
                tblService.setItems(loadDataTable());
            }else{
                new AlertTypes().failAlert(MyStackPane);
            }
        }
    }
    ButtonType Yes=new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType No=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);
    public void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException {
        ServiceTM temp=tblService.getSelectionModel().getSelectedItem();

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove the item",Yes,No);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.orElse(Yes)==No){
            return;
        }

        if(temp==null){
            new AlertTypes().selectRow(MyStackPane);
        }else{
            if(new ServiceController().deleteService(temp)){
                new AlertTypes().SuccessAlert(MyStackPane);
                tblService.getItems().clear();
                tblService.setItems(loadDataTable());
            }else{
                new AlertTypes().failAlert(MyStackPane);
            }
        }
    }

    public void AddOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../View/AddServiceForm.fxml"));
        Parent parent = loader.load();
        AddServiceFormController controller = loader.<AddServiceFormController>getController();
        controller.setEvent(this);
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
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

    @Override
    public void loadData() {
        tblService.getItems().clear();
        tblService.setItems(loadDataTable());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("Sid"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Sname"));
        colSprice.setCellValueFactory(new PropertyValueFactory<>("Sprice"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Sdescription"));

        tblService.setItems(loadDataTable());

        btnUpdate.setDisable(true);
        storeValidations();
    }

    ObservableList<ServiceTM> oblist= FXCollections.observableArrayList();
    public ObservableList<ServiceTM> loadDataTable(){
        try {
            List<ServiceTM> allService = ServiceController.getAllServices();

            for (ServiceTM allServices : allService) {
                oblist.add(new ServiceTM(allServices.getSid(),allServices.getSname(),allServices.getSprice(),allServices.getSdescription()));
            }
            return oblist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void storeValidations() {
        map.put(txtScode,idPattern);
        map2.put(txtSname,namePattern);
        map2.put(txtSprice,pricePattern);
        map2.put(txtSdescription,descriptionPattern);
    }
    public void clear(){
        txtScode.clear();
        txtSname.clear();
        txtSprice.clear();
        txtSdescription.clear();
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
