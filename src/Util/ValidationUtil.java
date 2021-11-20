package Util;

import animatefx.animation.BounceIn;
import animatefx.animation.Flash;
import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static Object validate(LinkedHashMap<JFXTextField, Pattern> map, JFXButton btn) {
        btn.setDisable(true);
        for (JFXTextField textFieldKey : map.keySet()) {
            Pattern patternValue = map.get(textFieldKey);
            if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                if (!textFieldKey.getText().isEmpty()) {
                    //textFieldKey.setStyle("-fx-border-color: red");
                    textFieldKey.setStyle("-fx-text-fill: red");

                }
                return textFieldKey;

            }else
            textFieldKey.setStyle("-fx-text-fill: green");
            //textFieldKey.setStyle("-fx-border-color: green");
        }
        btn.setDisable(false);
        return true;
    }
}
