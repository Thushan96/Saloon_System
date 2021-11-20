package Controller;

import Model.Employee;
import Model.Item;
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
import tm.ItemTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ItemManageFormController implements Initializable, TableLoadEvent {
    public JFXTextField txtItem;
    public JFXTextField txtIName;
    public JFXTextField txtPrice;
    public JFXTextField txtQtyOnHand;
    public JFXButton btnSearch;
    public JFXButton btnUpdate;
    public TableView<ItemTM> tblItem;
    public TableColumn<Object, Object> ColItemCode;
    public TableColumn<Object, Object> ColItemName;
    public TableColumn<Object, Object> ColIQtyOnHand;
    public TableColumn<Object, Object> ColItemPrice;
    public JFXButton btnAdd;
    public JFXButton btnDelete;
    public StackPane MyStackPane;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap<>();
    LinkedHashMap<JFXTextField, Pattern> map2 = new LinkedHashMap<>();
    Pattern idPattern = Pattern.compile("^[I][0-9]{3}$");
    Pattern namePattern = Pattern.compile("^[A-z0-1 ]{3,30}$");
    Pattern pricePattern = Pattern.compile("^[1-9][0-9]+([.]00)*$");
    Pattern QtyOnHandPattern = Pattern.compile("^[1-9][0-9]*$");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ColItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        ColItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        ColItemPrice.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));
        ColIQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));

        tblItem.setItems(loadDataTable());

        btnUpdate.setDisable(true);
        storeValidations();
    }

    private void storeValidations() {
        map.put(txtItem,idPattern);
        map2.put(txtIName,namePattern);
        map2.put(txtPrice,pricePattern);
        map2.put(txtQtyOnHand,QtyOnHandPattern);
    }
    public void clear(){
        txtIName.clear();
        txtItem.clear();
        txtPrice.clear();
        txtQtyOnHand.clear();
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

    public void UpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException {
        if(txtIName.getText().isEmpty()||txtPrice.getText().isEmpty()||txtQtyOnHand.getText().isEmpty()) {
            new AlertTypes().noDataFound(MyStackPane);
        }else{
            Item i1=new Item(txtItem.getText(),txtIName.getText(),Double.parseDouble(txtPrice.getText()),Integer.parseInt(txtQtyOnHand.getText()));

            if(new ItemController().updateItem(i1)){
                new AlertTypes().SuccessAlert(MyStackPane);
                clear();
                tblItem.getItems().clear();
                tblItem.setItems(loadDataTable());
                btnUpdate.setDisable(true);
            }else{
                new AlertTypes().failAlert(MyStackPane);
            }
        }
    }

    ButtonType Yes=new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
    ButtonType No=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);

    public void DeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, InterruptedException {
        ItemTM temp=tblItem.getSelectionModel().getSelectedItem();

        Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove the item",Yes,No);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.orElse(Yes)==No){
            return;
        }

        if(temp==null){
            new AlertTypes().selectRow(MyStackPane);
        }else{
            if(new ItemController().deleteItem(temp)){
                new AlertTypes().SuccessAlert(MyStackPane);
                tblItem.getItems().clear();
                tblItem.setItems(loadDataTable());
            }else{
                new AlertTypes().failAlert(MyStackPane);
            }
        }
    }

    public void AddOnAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../View/AddItemForm.fxml"));
        Parent parent = loader.load();
        AddItemFormController controller = loader.<AddItemFormController>getController();
        controller.setEvent(this);
        Stage stage=new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void SearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(!txtItem.getText().isEmpty()){
            Item rst = new ItemController().getItems(txtItem.getText());

            if(rst==null){
                new AlertTypes().noDataFound(MyStackPane);
                return;
            }
            txtItem.setText(rst.getItemId());
            txtIName.setText(rst.getItemName());
            txtQtyOnHand.setText(String.valueOf(rst.getQtyOnHand()));
            txtPrice.setText(String.valueOf(rst.getItemPrice())+"0");
        }else{
            new AlertTypes().noDataFound(MyStackPane);
        }
    }


    @Override
    public void loadData() {
        tblItem.getItems().clear();
        tblItem.setItems(loadDataTable());
    }

    ObservableList<ItemTM> oblist= FXCollections.observableArrayList();
    public ObservableList<ItemTM> loadDataTable(){
        try {
            List<ItemTM> allItems = ItemController.getAllItems();

            for (ItemTM allItem : allItems) {
                oblist.add(new ItemTM(allItem.getItemId(),allItem.getItemName(),allItem.getQtyOnHand(),allItem.getItemPrice()));
            }
            return oblist;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;

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
