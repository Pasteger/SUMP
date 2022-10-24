package net.skaia.pasteger.sump.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private PasswordField authorizationPasswordPasswordField;
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
    public TextField registrationIDTextField;
    @FXML
    public TextField registrationLoginTextField;
    @FXML
    public TextField registrationNameTextField;
    @FXML
    public TextField registrationAddressTextField;
    @FXML
    public TextField registrationPhoneTextField;
    @FXML
    public PasswordField registrationPasswordPasswordField;
    @FXML
    public PasswordField registrationConfirmationPasswordField;
    @FXML
    public ImageView registrationButton;
    @FXML
    public Label registrationErrorLabel;

    /**
     * Client Room UI objects
     */
    @FXML
    public ImageView clientUpdateShipmentsImageView;
    @FXML
    public ImageView clientArrivalsRequestedShipmentsImageView;
    @FXML
    private Button clientExitButton;
    @FXML
    private ComboBox<String> clientRequestedShipmentsComboBox;
    @FXML
    private ComboBox<String> clientArrivalsShipmentsComboBox;
    @FXML
    private ComboBox<String> clientAcceptedShipmentsComboBox;
    @FXML
    private ComboBox<String> clientRejectedShipmentsComboBox;


    private final MediaPlayer mediaPlayer = new MediaPlayer(START_MUSIC);

    @FXML
    void initialize() {
        clientRequestedShipmentsComboBox.setValue("Запрошенные");
        clientArrivalsShipmentsComboBox.setValue("Прибывшие");
        clientRejectedShipmentsComboBox.setValue("Забракованные");
        clientAcceptedShipmentsComboBox.setValue("Принятые");

        enableAuthorizationUIObjects();
        disableRegistrationUIObjects();
        disableProviderRoomUIObjects();
        disableClientRoomUIObjects();

        mediaPlayer.play();

        registrationEntityChoiceComboBox.setItems(ENTITIES_FOR_REGISTRATION_ENTITY_CHOICE_COMBO_BOX);
        registrationEntityChoiceComboBox.setValue(DEFAULT_VALUE_WHO_ARE_YOU);

        authorizationRegistrationButton.setOnAction(actionEvent -> {
            authorizationLoginTextField.setText("");
            authorizationPasswordPasswordField.setText("");
            authorizationHeadLabel.setText(DEFAULT_VALUE_WHO_ARE_YOU);

            disableAuthorizationUIObjects();
            enableRegistrationUIObjects();
        });

        registrationBackButton.setOnAction(actionEvent -> {
            resetRegistrationUI();

            disableRegistrationUIObjects();
            enableAuthorizationUIObjects();
        });

        authorizationButton.setOnMouseClicked(mouseEvent -> {
            String response = service.authorization(
                    authorizationLoginTextField.getText(), authorizationPasswordPasswordField.getText());
            authorizationHeadLabel.setText(response);
            if (!(response.equals("provider") || response.equals("client"))) {
                authorizationHeadLabel.setText(response);
                return;
            }
            authorizationLoginTextField.setText("");
            authorizationPasswordPasswordField.setText("");
            authorizationHeadLabel.setText(DEFAULT_VALUE_WHO_ARE_YOU);

            disableAuthorizationUIObjects();
            if (response.equals("provider")) {
                enableProviderRoomUIObjects();
            } else {
                enableClientRoomUIObjects();
            }
        });

        registrationButton.setOnMouseClicked(mouseEvent -> {
            String response;
            if (!registrationConfirmationPasswordField.getText().equals(registrationPasswordPasswordField.getText())) {
                registrationErrorLabel.setText("Пароли не совпадают");
                return;
            }
            response = service.registration(
                    registrationIDTextField.getText(), registrationNameTextField.getText(),
                    registrationAddressTextField.getText(), registrationPhoneTextField.getText(),
                    registrationEntityChoiceComboBox.getValue(),
                    registrationLoginTextField.getText(), registrationPasswordPasswordField.getText());

            if (!response.equals("success")) {
                registrationErrorLabel.setText(response);
                return;
            }

            disableRegistrationUIObjects();
            enableAuthorizationUIObjects();

            resetRegistrationUI();
        });

        clientExitButton.setOnAction(actionEvent -> {
            disableClientRoomUIObjects();
            enableAuthorizationUIObjects();
        });


        clientArrivalsShipmentsComboBox.setOnAction(event -> {
            if (clientArrivalsShipmentsComboBox.getValue() == null ||
                    clientArrivalsShipmentsComboBox.getValue().equals("Прибывшие")) return;
            String value = clientArrivalsShipmentsComboBox.getValue();
            service.acceptShipment(value);
            updateClientRoomUIObjectValue();
        });

        clientRejectedShipmentsComboBox.setOnAction(event ->
                Platform.runLater(() -> {
                    if (clientRejectedShipmentsComboBox.getValue() == null ||
                            clientRejectedShipmentsComboBox.getValue().equals("Забракованные")) return;
                    String value = clientRejectedShipmentsComboBox.getValue();

                    clientRejectedShipmentsComboBox.setValue("Забракованные");
                })
        );

        clientAcceptedShipmentsComboBox.setOnAction(event ->
                Platform.runLater(() -> {
                    if (clientAcceptedShipmentsComboBox.getValue() == null ||
                            clientAcceptedShipmentsComboBox.getValue().equals("Принятые")) return;
                    String value = clientAcceptedShipmentsComboBox.getValue();

                    clientAcceptedShipmentsComboBox.setValue("Принятые");
                })
        );

        clientRequestedShipmentsComboBox.setOnAction(event ->
                Platform.runLater(() -> {
                    if (clientRequestedShipmentsComboBox.getValue() == null ||
                            clientRequestedShipmentsComboBox.getValue().equals("Запрошенные")) return;
                    String value = clientRequestedShipmentsComboBox.getValue();

                    clientRequestedShipmentsComboBox.setValue("Запрошенные");
                })
        );

        clientUpdateShipmentsImageView.setOnMouseClicked(mouseEvent -> updateClientRoomUIObjectValue());
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
        updateClientRoomUIObjectValue();
    }

    private void disableClientRoomUIObjects() {
        clientRoomAnchorPane.setVisible(false);
        resetClientRoomUIObjectValue();
    }

    private void resetRegistrationUI() {
        registrationIDTextField.setText("");
        registrationLoginTextField.setText("");
        registrationPasswordPasswordField.setText("");
        registrationConfirmationPasswordField.setText("");
        registrationNameTextField.setText("");
        registrationAddressTextField.setText("");
        registrationPhoneTextField.setText("");
    }

    private void updateClientRoomUIObjectValue() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> handler = clientArrivalsShipmentsComboBox.getOnAction();
            clientArrivalsShipmentsComboBox.setOnAction(null);

            clientRequestedShipmentsComboBox.setItems(service.getRequestedShipments());
            clientAcceptedShipmentsComboBox.setItems(service.getAcceptedShipments());
            clientArrivalsShipmentsComboBox.setItems(service.getArrivalsShipments());
            clientRejectedShipmentsComboBox.setItems(service.getRejectedShipments());

            clientArrivalsShipmentsComboBox.setOnAction(handler);

            clientArrivalsRequestedShipmentsImageView.setVisible(
                    !(clientArrivalsShipmentsComboBox.getItems() == null || clientArrivalsShipmentsComboBox.getItems().isEmpty()));


            clientArrivalsShipmentsComboBox.setValue("Прибывшие");
        });
    }

    private void resetClientRoomUIObjectValue() {
        clientRequestedShipmentsComboBox.setItems(null);
        clientAcceptedShipmentsComboBox.setItems(null);
        clientArrivalsShipmentsComboBox.setItems(null);
        clientRejectedShipmentsComboBox.setItems(null);
    }
}
