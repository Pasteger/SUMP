package net.skaia.pasteger.sump.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import net.skaia.pasteger.sump.service.RegistrationService;

import static net.skaia.pasteger.sump.Constants.DEFAULT_VALUE_WHO_ARE_YOU;
import static net.skaia.pasteger.sump.Constants.ENTITIES_FOR_REGISTRATION_ENTITY_CHOICE_COMBO_BOX;

public class RegistrationController extends Controller {
    RegistrationService service = RegistrationService.getInstance();

    @FXML
    private ComboBox<String> entityChoiceComboBox;
    @FXML
    private Button backButton;
    @FXML
    public TextField idTextField;
    @FXML
    public TextField loginTextField;
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField addressTextField;
    @FXML
    public TextField phoneTextField;
    @FXML
    public PasswordField passwordPasswordField;
    @FXML
    public PasswordField confirmationPasswordField;
    @FXML
    public ImageView registrationButton;
    @FXML
    public Label errorLabel;

    @FXML
    void initialize() {
        entityChoiceComboBox.setItems(ENTITIES_FOR_REGISTRATION_ENTITY_CHOICE_COMBO_BOX);
        entityChoiceComboBox.setValue(DEFAULT_VALUE_WHO_ARE_YOU);


        backButton.setOnAction(actionEvent ->
                openOtherWindow("authorization", backButton));

        registrationButton.setOnMouseClicked(mouseEvent -> {
            String response;
            if (!confirmationPasswordField.getText().equals(passwordPasswordField.getText())) {
                errorLabel.setText("Пароли не совпадают");
                return;
            }
            response = service.registration(
                    idTextField.getText(), nameTextField.getText(),
                    addressTextField.getText(), phoneTextField.getText(),
                    entityChoiceComboBox.getValue(),
                    loginTextField.getText(), passwordPasswordField.getText());

            if (!response.equals("success")) {
                errorLabel.setText(response);
                return;
            }

            openOtherWindow("authorization", registrationButton);
        });
    }
}
