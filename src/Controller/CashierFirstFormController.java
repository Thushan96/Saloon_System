package Controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class CashierFirstFormController implements Initializable {
    public JFXButton btnAppoinment;
    public JFXButton btnItem;
    public JFXButton btnLoGout;
    public Label lblDate;
    public Label lblTime;
    public JFXButton bttnOnDuity;
    public JFXButton btnOffDuity;
    public Pane myPane;

    public void AppoinmentOnAction(ActionEvent event) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("../View/CashierMainForm.fxml"));
        myPane.getChildren().setAll(pane);
    }

    public void ItemsOnAction(ActionEvent event) throws IOException {
        AnchorPane pane= FXMLLoader.load(getClass().getResource("../View/CashierItemForm.fxml"));
        myPane.getChildren().setAll(pane);
    }

    public void logOutOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("../View/LogInForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) btnLoGout.getScene().getWindow();
        window.setScene(new Scene(load));
    }
    private void loadDate(){
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDate();
        btnOffDuity.setVisible(false);
        bttnOnDuity.setVisible(false);

    }
}
