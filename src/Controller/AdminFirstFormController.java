package Controller;

import Model.DatePrice;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AdminFirstFormController implements Initializable {
    public Label lblDate;
    public Label lblTime;
    public JFXButton btnManageEmployee;
    public Pane mystackPane;
    public JFXButton btnLogOut;
    public JFXComboBox<String> cmbReports;
    int index;
List<DatePrice> datePrices=new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Date date=new Date();
        SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

        setDataToCmb();



        cmbReports.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            index= (int) newValue;
           switch (index){
               case 0:

                   try {
                       JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/DailyService.jrxml"));
                       JasperReport compileReport = JasperCompileManager.compileReport(load);
                       JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null,DbConnection.getInstance().getConnection());
                       JasperViewer.viewReport(jasperPrint,false);

                   } catch (JRException e) {
                       e.printStackTrace();
                   } catch (SQLException throwables) {
                       throwables.printStackTrace();
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   }

                   break;
               case 1:
                   try {
                       JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/dailyItem.jrxml"));
                       JasperReport compileReport = JasperCompileManager.compileReport(load);
                       JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null,DbConnection.getInstance().getConnection());
                       JasperViewer.viewReport(jasperPrint,false);

                   } catch (JRException e) {
                       e.printStackTrace();
                   } catch (SQLException throwables) {
                       throwables.printStackTrace();
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   }

                   break;
               case 2:
                   try {
                       JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/MonthlyService.jrxml"));
                       JasperReport compileReport = JasperCompileManager.compileReport(load);
                       JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null,DbConnection.getInstance().getConnection());
                       JasperViewer.viewReport(jasperPrint,false);

                   } catch (JRException e) {
                       e.printStackTrace();
                   } catch (SQLException throwables) {
                       throwables.printStackTrace();
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   }
                   break;
               case 3:
                   try {
                       JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/monthlyItem.jrxml"));
                       JasperReport compileReport = JasperCompileManager.compileReport(load);
                       JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null,DbConnection.getInstance().getConnection());
                       JasperViewer.viewReport(jasperPrint,false);

                   } catch (JRException e) {
                       e.printStackTrace();
                   } catch (SQLException throwables) {
                       throwables.printStackTrace();
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   }
                   break;
               case 4:
                   try {
                       JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/YearlyService.jrxml"));
                       JasperReport compileReport = JasperCompileManager.compileReport(load);
                       JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null,DbConnection.getInstance().getConnection());
                       JasperViewer.viewReport(jasperPrint,false);


                   } catch (JRException e) {
                       e.printStackTrace();
                   } catch (SQLException throwables) {
                       throwables.printStackTrace();
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   }
                   break;
               case 5:
                   try {
                       JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/YearlyItem.jrxml"));
                       JasperReport compileReport = JasperCompileManager.compileReport(load);
                       JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport,null,DbConnection.getInstance().getConnection());
                       JasperViewer.viewReport(jasperPrint,false);

                   } catch (JRException e) {
                       e.printStackTrace();
                   } catch (SQLException throwables) {
                       throwables.printStackTrace();
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   }
                   break;
               default:
                   break;
           }


        });

    }
    static String[] stringsList={"Service wise Daily Income Report","Item Wise Daily Income Report","Service wise Monthly Income Report","Item wise Monthly Income Report","Service wise Yearly Income Report","Item wise Yearly Income Reports"
            };

    private void setDataToCmb(){
        ObservableList<String> stringObservableList = FXCollections.observableArrayList(stringsList);
        cmbReports.setItems(stringObservableList);
    }

    public void MangeEmployeeOnAction(ActionEvent event) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("../View/EmployeeManageForm.fxml"));
        mystackPane.getChildren().setAll(pane);
    }


    public void ManageServiceOnAction(ActionEvent event) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("../View/ServiceManageForm.fxml"));
        mystackPane.getChildren().setAll(pane);
    }

    public void ManageItemsOnAction(ActionEvent event) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("../View/ItemManageForm.fxml"));
        mystackPane.getChildren().setAll(pane);
    }

    public void LohOutOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("../View/LogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) btnLogOut.getScene().getWindow();
        window.setScene(new Scene(load));
    }

}
