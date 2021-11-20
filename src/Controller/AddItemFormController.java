package Controller;

import Model.Item;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddItemFormController implements Initializable {
    public StackPane mystacPane;
    public JFXTextField txtItemId;
    public JFXTextField txtItemName;
    public JFXTextField txtItemPrice;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSave;
    public JFXButton btnClear;
    public AnchorPane MyStackPane;


    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^[I][0-9]{3}$");
    Pattern namePattern = Pattern.compile("^[A-z0-1 ]{3,30}$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]+([.]00)*$");
    Pattern QtyOnHandPattern = Pattern.compile("^[1-9][0-9]*$");

    private TableLoadEvent loadEvent;

    private void storeValidations() {
        map.put(txtItemId,idPattern);
        map.put(txtItemName,namePattern);
        map.put(txtItemPrice,pricePattern);
        map.put(txtQtyOnHand,QtyOnHandPattern);
    }


    public void ClearOnAction(ActionEvent event) {
        clear();
    }

    public void clear(){
        txtItemName.clear();
        txtItemId.clear();
        txtItemPrice.clear();
        txtQtyOnHand.clear();
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
    public void setEvent(TableLoadEvent loadEvent){
        this.loadEvent=loadEvent;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setDisable(true);
        storeValidations();
    }

    public void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException {
        Item i=new Item(txtItemId.getText(),txtItemName.getText(),
                Double.parseDouble(txtItemPrice.getText()),Integer.parseInt(txtQtyOnHand.getText()));

        ResultSet rst = DbConnection.getInstance().getConnection().prepareStatement("SELECT item_Id FROM item").executeQuery();
        while (rst.next()) {
            if (txtItemId.getText().equals(rst.getString(1))) {
                new AlertTypes().DataExistAlert(mystacPane);
                txtItemId.setStyle("-fx-text-fill: red");
                new FadeIn(txtItemId).play();
                return;
            }
        }

            if(new ItemController().SaveItem(i)){
                new AlertTypes().SuccessAlert(mystacPane);
                clear();
                loadEvent.loadData();
                Stage window=(Stage)MyStackPane.getScene().getWindow();
                window.close();
            }else{
                new AlertTypes().failAlert(mystacPane);
                clear();
            }


    }
}
