package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class AlertTypes {

    public void SuccessAlert(StackPane myStackPane){
        String tittle = "Success";
        String content = "Operation Successful.";
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text(tittle));
        dialogLayout.setBody(new Text(content));
        JFXButton Ok = new JFXButton("Ok");
        Ok.setButtonType(JFXButton.ButtonType.RAISED);
        Ok.setStyle("-fx-background-color: cornsilk");
        dialogLayout.setActions(Ok);
        JFXDialog dialog = new JFXDialog(myStackPane, dialogLayout, JFXDialog.DialogTransition.BOTTOM);
        Ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        dialog.show();
    }
    public void failAlert(StackPane myStackPane){
        String tittle = "Fail";
        String content = "Operation Failure.Please check information and try again";
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
    }

    public void DataExistAlert(StackPane myStackPane){
        String tittle = "Fail";
        String content = "Data already Exist.Please check information and try again";
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
    }
    public void noDataFound(StackPane myStackPane){
        String tittle = "Warning";
        String content = "Enter correct data and try again";
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
    }
    public void selectRow(StackPane myStackPane){
        String tittle = "Error";
        String content = "Please select first and Try again";
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        dialogLayout.setHeading(new Text(tittle));
        dialogLayout.setBody(new Text(content));
        JFXButton close = new JFXButton("Okay");
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
    }
    public void CustomerExistAlert(StackPane myStackPane){
        String tittle = "Fail";
        String content = "You are already registered please proceed with the payment";
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
    }
    public void CustomerNotExistAlert(StackPane myStackPane){
        String tittle = "Fail";
        String content = "You are not registered please signup first and proceed with appointment";
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
    }
    public void EmployeeLoginSuccess(StackPane myStackPane){
        String tittle = "Success";
        String content = "Successfully logged in";
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
    }

    public void EmployeeLoginfai(StackPane myStackPane){
        String tittle = "fail";
        String content = "failed to login please try again";
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
    }
    public void EmployeeLogOUTSuccess(StackPane myStackPane){
        String tittle = "Success";
        String content = "Successfully logged out";
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
    }
    public void QuantityEcxceed(StackPane myStackPane){
        String tittle = "Warning";
        String content = "Enter correct item amount and try again";
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
    }
}
