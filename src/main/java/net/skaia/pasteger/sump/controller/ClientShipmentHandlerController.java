package net.skaia.pasteger.sump.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.skaia.pasteger.sump.service.ClientShipmentHandlerService;

public class ClientShipmentHandlerController extends Controller {
    ClientShipmentHandlerService service = ClientShipmentHandlerService.getInstance();

    @FXML
    private Label shipmentStatusLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox<String> clientListComboBox;
    @FXML
    private Button deleteButton;
    @FXML
    private ComboBox<String> productListComboBox;
    @FXML
    private TextField productQuantityTextField;
    @FXML
    private ComboBox<String> providerListComboBox;
    @FXML
    private Button rejectButton;
    @FXML
    private Button resendButton;
    @FXML
    private Button sendButton;

    @FXML
    void initialize() {
        if (service.getShipmentStatus().equals("arrivals")) {
            shipmentStatusLabel.setText("ПРИБЫВШАЯ ПОСТАВКА");
            deleteButton.setVisible(false);
            cancelButton.setVisible(false);
            resendButton.setVisible(false);
            sendButton.setVisible(false);
        } else if (service.getShipmentStatus().equals("accepted")) {
            shipmentStatusLabel.setText("ПРИНЯТАЯ ПОСТАВКА");
            deleteButton.setVisible(false);
            cancelButton.setVisible(false);
            resendButton.setVisible(false);
            sendButton.setVisible(false);
            rejectButton.setVisible(false);
            acceptButton.setVisible(false);
        } else if (service.getShipmentStatus().equals("requested")) {
            shipmentStatusLabel.setText("ЗАПРОШЕННАЯ ПОСТАВКА");
            deleteButton.setVisible(false);
            resendButton.setVisible(false);
            sendButton.setVisible(false);
            rejectButton.setVisible(false);
            acceptButton.setVisible(false);
        } else if (service.getShipmentStatus().equals("rejected")) {
            shipmentStatusLabel.setText("ЗАБРАКОВАННАЯ ПОСТАВКА");
            sendButton.setVisible(false);
            rejectButton.setVisible(false);
            acceptButton.setVisible(false);
            cancelButton.setVisible(false);
        } else {
            shipmentStatusLabel.setText("НОВАЯ ПОСТАВКА");
            deleteButton.setVisible(false);
            cancelButton.setVisible(false);
            resendButton.setVisible(false);
            rejectButton.setVisible(false);
            acceptButton.setVisible(false);

            clientListComboBox.setValue(service.getClient().getName());
            productListComboBox.setItems(service.getProductStringList());
            providerListComboBox.setItems(service.getProviderStringList());
        }

        backButton.setOnAction(actionEvent -> openOtherWindow("client_room", backButton));

        sendButton.setOnAction(actionEvent -> {
            String response = service.sendShipment(
                    productQuantityTextField.getText(), providerListComboBox.getValue(), productListComboBox.getValue());
            if (response.equals("success")) {
                openOtherWindow("client_room", sendButton);
            } else {
                shipmentStatusLabel.setText("Ошибка");
            }
        });
    }
}
