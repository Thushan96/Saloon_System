package Controller;

import Model.Item;
import Model.Services;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddServiceFormController implements Initializable {
    public StackPane MyStackPane;
    public JFXTextField txtScode;
    public JFXTextField txtSname;
    public JFXTextField txtSprice;
    public JFXTextField txtSdescription;
    public JFXButton btnSave;
    public JFXButton btnClear;
    public AnchorPane MyAnchorPane;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^[S][0-9]{3}$");
    Pattern namePattern = Pattern.compile("^[A-z0-1 ]{3,30}$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]+([.]00)*$");
    Pattern descriptionPattern = Pattern.compile("^[A-z ]+[.]*$");

    private TableLoadEvent loadEvent;


    public void SaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException {
        Services s=new Services(txtScode.getText(),txtSname.getText(),
                Double.parseDouble(txtSprice.getText()),txtSdescription.getText());

        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT service_Id FROM service").executeQuery();
        while (rst.next()) {
            if (txtScode.getText().equals(rst.getString(1))) {
                new AlertTypes().DataExistAlert(MyStackPane);
                txtScode.setStyle("-fx-text-fill: red");
                new FadeIn(txtScode).play();
                return;
            }
        }

        if(new ServiceController().SaveItem(s)){
            new AlertTypes().SuccessAlert(MyStackPane);
            clear();
            loadEvent.loadData();
            Stage window=(Stage)MyAnchorPane.getScene().getWindow();
            window.close();
        }else{
            new AlertTypes().failAlert(MyStackPane);
            clear();
        }


    }

    public void ClearOnAction(ActionEvent event) {
        clear();

    }
    public void clear(){
        txtScode.clear();
        txtSname.clear();
        txtSprice.clear();
        txtSdescription.clear();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtScode,idPattern);
        map.put(txtSname,namePattern);
        map.put(txtSprice,pricePattern);
        map.put(txtSdescription,descriptionPattern);
    }


    public void setEvent(TableLoadEvent loadEvent){
        this.loadEvent=loadEvent;
    }



}
