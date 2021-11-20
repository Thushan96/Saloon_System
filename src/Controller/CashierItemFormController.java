package Controller;

import Model.Item;
import Model.ItemDetails;
import Model.Orders;
import Util.ValidationUtil;
import animatefx.animation.Flash;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.ItemSellTM;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.Pattern;

public class CashierItemFormController implements Initializable {
    public Label lblOrderId;
    public JFXTextField txtItemName;
    public JFXTextField txtItemQtyOnHand;
    public JFXTextField txtPrice;
    public JFXButton btnAddToList;
    public TableView<ItemSellTM> tblItem;
    public TableColumn<Object, Object> colItemCode;
    public TableColumn<Object, Object> colItemName;
    public TableColumn<Object, Object> colUnitPrice;
    public TableColumn<Object, Object> colQuantity;
    public TableColumn<Object, Object> colTotal;
    public TableColumn<Object, Object> colBtn;
    public JFXButton btnPay;
    public Label lblTotal;
    public JFXTextField txtQuantity;
    public JFXTextField txtItemCode;
    public JFXButton btnSearch;
    public StackPane MyStackPane;
    public JFXTextField txtAmount;

    LinkedHashMap<JFXTextField, Pattern> map = new LinkedHashMap();
    Pattern idPattern = Pattern.compile("^[I][0-9]{3}$");
    LinkedHashMap<JFXTextField, Pattern> map2 = new LinkedHashMap();
    Pattern numPattern = Pattern.compile("^[1-9][0-9]*$");
    String Addeddate=null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("ItemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("ItemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("ItemPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colBtn.setCellValueFactory(new PropertyValueFactory<>("btn"));

        btnSearch.setDisable(true);
        btnAddToList.setDisable(true);
        btnPay.setDisable(true);


        setOrderId();
        storeValidations();
        loadDate();
    }
    private void loadDate() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Addeddate = (f.format(date));

    }
    private void storeValidations() {
        map.put(txtItemCode,idPattern);
        map2.put(txtQuantity,numPattern);
        map3.put(txtAmount,amountPattern);

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



    public void searchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(txtItemCode.getText()==null){
            new AlertTypes().noDataFound(MyStackPane);
        }else{
            Item i1=new ItemController().getItems(txtItemCode.getText());
            if(i1==null){
                new AlertTypes().noDataFound(MyStackPane);
                return;
            }

            txtItemCode.setText(i1.getItemId());
            txtItemName.setText(i1.getItemName());
            txtItemQtyOnHand.setText(String.valueOf(i1.getQtyOnHand()));
            txtPrice.setText(String.valueOf(i1.getItemPrice())+0);
        }
    }


    ObservableList<ItemSellTM> obList= FXCollections.observableArrayList();
    public void AddToListOnAction(ActionEvent event) {
        String ItemCode = txtItemCode.getText();
        String ItemName=txtItemName.getText();
        int qtyOnHand = Integer.parseInt(txtItemQtyOnHand.getText());
        double unitPrice = Double.parseDouble(txtPrice.getText());
        int qtyOfCustomer = Integer.parseInt(txtQuantity.getText());
        double total = (qtyOfCustomer*unitPrice);

        if (qtyOnHand<qtyOfCustomer){
            new AlertTypes().QuantityEcxceed(MyStackPane);
            return;
        }
        Button btn=new Button("Delete");
        ItemSellTM tm = new ItemSellTM(
                ItemCode,
                ItemName,
                unitPrice,
                qtyOfCustomer,
                total,
                btn

        );

        int rowNumber=isExists(tm);

        if (rowNumber==-1){// new Add
            obList.add(tm);
        }else{
            // update
            ItemSellTM temp = obList.get(rowNumber);
            ItemSellTM newTm = new ItemSellTM(
                    temp.getItemId(),
                    temp.getItemName(),
                    unitPrice,
                    temp.getQuantity()+qtyOfCustomer,
                    total+temp.getTotal(),
                    btn
            );

            obList.remove(rowNumber);
            obList.add(newTm);

        }
        ButtonType Yes=new ButtonType("Yes",ButtonBar.ButtonData.OK_DONE);
        ButtonType No=new ButtonType("No",ButtonBar.ButtonData.CANCEL_CLOSE);


        for (ItemSellTM Delete:obList) {
            btn.setOnAction((e)->{
                Alert alert=new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to remove the item",Yes,No);
                Optional<ButtonType> result=alert.showAndWait();
                if (result.orElse(No)==Yes){
                    obList.remove(Delete);
                    calculateCost();
                }else{
                }


            });

        }
        tblItem.setItems(obList);
        calculateCost();

        clear();
        btnSearch.setDisable(true);
        btnAddToList.setDisable(true);
        btnPay.setDisable(false);
    }

    public void clear(){
    txtItemCode.clear();
    txtQuantity.clear();
    txtItemName.clear();
    txtPrice.clear();
    txtItemQtyOnHand.clear();
    }

    private int isExists(ItemSellTM tm){
        for (int i = 0; i < obList.size(); i++) {
            if (tm.getItemId().equals(obList.get(i).getItemId())){
                return i;
            }
        }
        return -1;
    }
    double ttl=0;
    void calculateCost(){
        ttl=0;
        for (ItemSellTM tm:obList
        ) {
            ttl+=tm.getTotal();
        }
        lblTotal.setText(ttl+"0/=");
    }

    private void setOrderId() {
        try {

            lblOrderId.setText(new ItemController().getOrderId());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    double balance;
    HashMap<String, Object> hashMap=new HashMap<String, Object>();
    public void PayOnAction(ActionEvent event) {
        if(Double.parseDouble(ttl+"0")<=Double.parseDouble(txtAmount.getText())){
            balance=ttl-Double.parseDouble(txtAmount.getText());
        }else{
            new AlertTypes().failAlert(MyStackPane);
            return;
        }
        ArrayList<ItemDetails> items= new ArrayList<>();
        double total=0;
        for (ItemSellTM tempTm:obList
        ) {
            total+=tempTm.getTotal();
            items.add(new ItemDetails(tempTm.getItemId(),tempTm.getItemPrice(),
                    tempTm.getQuantity()));
        }

        Orders order= new Orders(lblOrderId.getText(),
                total,
                items
        );

        if (new ItemController().placeOrder(order,Addeddate)){
            new AlertTypes().SuccessAlert(MyStackPane);
            hashMap.put("SearchId",lblOrderId.getText());
            hashMap.put("PayAmount",Double.parseDouble(txtAmount.getText()));
            hashMap.put("balance",balance);
            try {
                JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/ItemReceipt.jrxml"));
                JasperReport compileReport = JasperCompileManager.compileReport(load);
                JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,hashMap, DbConnection.getInstance().getConnection());
                JasperViewer.viewReport(jasperPrint,false);

            } catch (JRException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            setOrderId();
            tblItem.getItems().clear();
            lblTotal.setText(null);
            btnPay.setDisable(true);
            txtAmount.clear();

        }else{
            new AlertTypes().failAlert(MyStackPane);
        }


    }


    public void textKeyPressed(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map2,btnAddToList);

        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (response instanceof TextField) {
                JFXTextField errorText = (JFXTextField) response;
                errorText.requestFocus();
                new Flash(errorText).play();
            } else if (response instanceof Boolean) {

            }
        }
    }
    LinkedHashMap<JFXTextField,Pattern> map3=new LinkedHashMap<>();
    Pattern amountPattern = Pattern.compile("^[1-9][0-9]+(.00)?$");

    public void textFeildKeyChange(KeyEvent keyEvent) {
        Object response = ValidationUtil.validate(map3,btnPay);


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
