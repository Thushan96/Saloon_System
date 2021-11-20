package Controller;

import com.jfoenix.controls.JFXButton;
import db.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class BarberFormController implements Initializable {

    public JFXButton btnOnDuity;
    public JFXButton btnOffDuity;
    public Label lblDate;
    public Label lblUserId;
    public StackPane MystackPane;
    public AnchorPane MyAnchorPane;

    String bId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        btnOffDuity.setDisable(false);
        btnOnDuity.setDisable(false);
    }

    public void OnDuityOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO attendance SET employee_Id=?,status=?,date=?");
        stm.setObject(1,bId);
        stm.setObject(2,true);
        stm.setObject(3,lblDate.getText());
        if (stm.executeUpdate()>0){

            new AlertTypes().EmployeeLoginSuccess(MystackPane);
            btnOnDuity.setDisable(true);
            Stage window=(Stage)MyAnchorPane.getScene().getWindow();
            window.close();
        }else{
            new Alert(Alert.AlertType.ERROR,"Please login back and try again").show();
        }

    }

    public void OffDuityOnAction(ActionEvent event) throws IOException {

        new AlertTypes().EmployeeLogOUTSuccess(MystackPane);
        Stage window=(Stage)MyAnchorPane.getScene().getWindow();
        window.close();
        btnOffDuity.setDisable(true);
    }
    public void setbId(String BID) throws SQLException, ClassNotFoundException {
        this.bId=BID;
        lblUserId.setText(BID);


        PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement(
                "SELECT EXISTS(SELECT status FROM attendance WHERE employee_Id=? AND date=? GROUP BY date)");

        stm.setObject(1,BID);
        stm.setObject(2,lblDate.getText());
        ResultSet rst = stm.executeQuery();
        while (rst.next()){
            if(rst.getString(1).equalsIgnoreCase("1")){
                btnOnDuity.setDisable(true);
                btnOffDuity.setDisable(false);
            }else{
                btnOffDuity.setDisable(true);
                btnOnDuity.setDisable(false);
            }
        }

    }


}
