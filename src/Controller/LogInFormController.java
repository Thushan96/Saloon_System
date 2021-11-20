package Controller;

import animatefx.animation.AnimationFX;
import animatefx.animation.Flash;
import animatefx.animation.Shake;
import com.jfoenix.controls.*;
import db.DbConnection;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogInFormController {
    public StackPane myStackPane;
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;
    public JFXButton btnBack;
    public JFXButton btnLogin;

    public void loginOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {



            PreparedStatement stm = DbConnection.getInstance().getConnection().prepareStatement("SELECT emp_Role FROM employee WHERE emp_Id=? AND emp_Password=?");
            stm.setObject(1, txtUsername.getText());
            stm.setObject(2, txtPassword.getText());

            String role = null;

            ResultSet rst = stm.executeQuery();
            while (rst.next()) {
                role = rst.getString(1);
            }

            if (role != (null)) {
                if (role.equalsIgnoreCase("admin")) {
                    txtUsername.clear();
                    txtPassword.clear();
                    URL resource = getClass().getResource("../View/AdminFirstForm.fxml");
                    Parent load = FXMLLoader.load(resource);
                    Stage window = (Stage) btnLogin.getScene().getWindow();
                    window.setScene(new Scene(load));
                } else if (role.equalsIgnoreCase("cashier")) {
                    txtUsername.clear();
                    txtPassword.clear();
                    URL resource = getClass().getResource("../View/CashierFirstForm.fxml");
                    Parent load = FXMLLoader.load(resource);
                    Stage window = (Stage) btnLogin.getScene().getWindow();
                    window.setScene(new Scene(load));
                } else if (role.equalsIgnoreCase("barber")) {

                    FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../View/BarberForm.fxml"));
                    Parent parent = loader.load();
                    BarberFormController controller = loader.<BarberFormController>getController();
                    controller.setbId(txtUsername.getText());
                    Stage stage=new Stage();
                    stage.setScene(new Scene(parent));
                    stage.show();
                    txtUsername.clear();
                    txtPassword.clear();

                }
            } else {
                String tittle = "Wrong Username or Password";
                String content = "Please Input Correct Username and password and Try again";
                JFXDialogLayout dialogLayout = new JFXDialogLayout();
                dialogLayout.setHeading(new Text(tittle));
                dialogLayout.setBody(new Text(content));
                JFXButton close = new JFXButton("Close");
                close.setButtonType(JFXButton.ButtonType.RAISED);
                close.setStyle("-fx-background-color: cornsilk");
                dialogLayout.setActions(close);
                JFXDialog dialog = new JFXDialog(myStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
                close.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        dialog.close();
                    }
                });
                dialog.show();

                txtUsername.clear();
                txtPassword.clear();
            }



    }

    public void BackOnAction(ActionEvent event) throws IOException {
        URL resource = getClass().getResource("../View/MainForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage) btnBack.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}
