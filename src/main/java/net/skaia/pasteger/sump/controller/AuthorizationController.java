package net.skaia.pasteger.sump.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import net.skaia.pasteger.sump.service.AuthorizationService;

import static net.skaia.pasteger.sump.Constants.DEFAULT_VALUE_WHO_ARE_YOU;

public class AuthorizationController extends Controller {
    AuthorizationService service = AuthorizationService.getInstance();

    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private ImageView authorizationButton;
    @FXML
    private Button registrationButton;
    @FXML
    private Label headLabel;

    @FXML
    void initialize() {
        //mediaPlayer.play();

        registrationButton.setOnAction(actionEvent -> {
            loginTextField.setText("");
            passwordPasswordField.setText("");
            headLabel.setText(DEFAULT_VALUE_WHO_ARE_YOU);

            openOtherWindow("registration", registrationButton);
        });

        authorizationButton.setOnMouseClicked(mouseEvent -> {
            String response = service.authorization(
                    loginTextField.getText(), passwordPasswordField.getText());
            headLabel.setText(response);
            if (!(response.equals("provider") || response.equals("client"))) {
                headLabel.setText(response);
                return;
            }
            loginTextField.setText("");
            passwordPasswordField.setText("");
            headLabel.setText(DEFAULT_VALUE_WHO_ARE_YOU);

            if (response.equals("provider")) {
                openOtherWindow("provider_room", authorizationButton);
            } else {
                openOtherWindow("client_room", authorizationButton);
            }
        });
    }
}
