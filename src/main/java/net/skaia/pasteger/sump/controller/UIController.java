package net.skaia.pasteger.sump.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.skaia.pasteger.sump.service.UIService;
import javafx.scene.media.MediaPlayer;

import static net.skaia.pasteger.sump.Constants.*;

public class UIController extends Controller {
    UIService service = UIService.getInstance();

    @FXML
    private AnchorPane authorizationAnchorPane;
    @FXML
    private AnchorPane registrationAnchorPane;
    @FXML
    private AnchorPane providerRoomAnchorPane;
    @FXML
    private AnchorPane clientRoomAnchorPane;


    /**
     * Authorization UI objects
     */
    @FXML
    private TextField authorizationLoginTextField;
    @FXML
    private TextField authorizationPasswordTextField;
    @FXML
    private ImageView authorizationButton;
    @FXML
    private Button authorizationRegistrationButton;

    @FXML
    private Label authorizationHeadLabel;

    /**
     * Registration UI objects
     */
    @FXML
    private ComboBox<String> registrationEntityChoiceComboBox;
    @FXML
    private Button registrationBackButton;
    @FXML
    public TextField registrationLoginTextField;
    @FXML
    public TextField registrationPasswordTextField;
    @FXML
    public TextField registrationNameTextField;
    @FXML
    public TextField registrationAddressTextField;
    @FXML
    public TextField registrationPhoneTextField;
    @FXML
    public ImageView registrationButton;


    private final MediaPlayer mediaPlayer = new MediaPlayer(START_MUSIC);

    @FXML
    void initialize() {
        enableAuthorizationUIObjects();
        disableRegistrationUIObjects();
        disableProviderRoomUIObjects();
        disableClientRoomUIObjects();


        mediaPlayer.play();

        registrationEntityChoiceComboBox.setItems(ENTITIES_FOR_REGISTRATION_ENTITY_CHOICE_COMBO_BOX);
        registrationEntityChoiceComboBox.setValue(DEFAULT_VALUE_WHO_ARE_YOU);


        authorizationRegistrationButton.setOnAction(actionEvent -> {
            authorizationLoginTextField.setText("");
            authorizationPasswordTextField.setText("");
            authorizationHeadLabel.setText(DEFAULT_VALUE_WHO_ARE_YOU);

            disableAuthorizationUIObjects();
            enableRegistrationUIObjects();
        });
        registrationBackButton.setOnAction(actionEvent -> {
            registrationLoginTextField.setText("");
            registrationPasswordTextField.setText("");
            registrationNameTextField.setText("");
            registrationAddressTextField.setText("");
            registrationPhoneTextField.setText("");
            registrationEntityChoiceComboBox.setValue(DEFAULT_VALUE_WHO_ARE_YOU);

            disableRegistrationUIObjects();
            enableAuthorizationUIObjects();
        });

        authorizationButton.setOnMouseClicked(mouseEvent -> {
            String response = service.authorization(
                    authorizationLoginTextField.getText(), authorizationPasswordTextField.getText());
            authorizationHeadLabel.setText(response);
            if (!(response.equals("provider") || response.equals("client"))) {
                authorizationHeadLabel.setText(response);
                return;
            }
            authorizationLoginTextField.setText("");
            authorizationPasswordTextField.setText("");
            authorizationHeadLabel.setText(DEFAULT_VALUE_WHO_ARE_YOU);

            disableAuthorizationUIObjects();
            if (response.equals("provider")) {
                enableProviderRoomUIObjects();
            } else {
                enableClientRoomUIObjects();
            }
        });

        registrationButton.setOnMouseClicked(mouseEvent -> {
            registrationLoginTextField.setText("");
            registrationPasswordTextField.setText("");
            registrationNameTextField.setText("");
            registrationAddressTextField.setText("");
            registrationPhoneTextField.setText("");
            registrationEntityChoiceComboBox.setValue(DEFAULT_VALUE_WHO_ARE_YOU);
        });
    }


    private void enableAuthorizationUIObjects() {
        authorizationAnchorPane.setVisible(true);
    }

    private void disableAuthorizationUIObjects() {
        authorizationAnchorPane.setVisible(false);
    }

    private void enableRegistrationUIObjects() {
        registrationAnchorPane.setVisible(true);
    }

    private void disableRegistrationUIObjects() {
        registrationAnchorPane.setVisible(false);
    }

    private void enableProviderRoomUIObjects() {
        providerRoomAnchorPane.setVisible(true);
    }

    private void disableProviderRoomUIObjects() {
        providerRoomAnchorPane.setVisible(false);
    }


    private void enableClientRoomUIObjects() {
        clientRoomAnchorPane.setVisible(true);
    }

    private void disableClientRoomUIObjects() {
        clientRoomAnchorPane.setVisible(false);
    }
}
