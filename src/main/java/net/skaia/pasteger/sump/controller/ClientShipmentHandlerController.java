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
        deleteButton.setVisible(false);
        cancelButton.setVisible(false);
        resendButton.setVisible(false);
        sendButton.setVisible(false);
        rejectButton.setVisible(false);
        acceptButton.setVisible(false);

        switch (service.getShipment().getStatus()) {
            case "arrivals" -> {
                shipmentStatusLabel.setText("ПРИБЫВШАЯ ПОСТАВКА");
                acceptButton.setVisible(true);
                rejectButton.setVisible(true);
                fillShipment();
            }
            case "accepted" -> {
                shipmentStatusLabel.setText("ПРИНЯТАЯ ПОСТАВКА");
                fillShipment();
            }
            case "requested" -> {
                shipmentStatusLabel.setText("ЗАПРОШЕННАЯ ПОСТАВКА");
                cancelButton.setVisible(true);
                fillShipment();
            }
            case "rejected" -> {
                shipmentStatusLabel.setText("ЗАБРАКОВАННАЯ ПОСТАВКА");
                deleteButton.setVisible(true);
                resendButton.setVisible(true);
                fillShipment();
            }
            default -> {
                shipmentStatusLabel.setText("НОВАЯ ПОСТАВКА");
                sendButton.setVisible(true);
                clientListComboBox.setValue(service.getClient().getName());
                productListComboBox.setItems(service.getProductStringList());
                providerListComboBox.setItems(service.getProviderStringList());
            }
        }

        backButton.setOnAction(actionEvent -> openOtherWindow("client_room", backButton));

        sendButton.setOnAction(actionEvent ->
                handleResponse(service.sendShipment(
                        productQuantityTextField.getText(), providerListComboBox.getValue(), productListComboBox.getValue())
                )
        );

        acceptButton.setOnAction(actionEvent -> handleResponse(service.updateShipmentStatus("accepted")));
        rejectButton.setOnAction(actionEvent -> handleResponse(service.updateShipmentStatus("rejected")));
        resendButton.setOnAction(actionEvent -> handleResponse(service.updateShipmentStatus("requested")));
        deleteButton.setOnAction(actionEvent -> handleResponse(service.deleteShipment()));
        cancelButton.setOnAction(actionEvent -> handleResponse(service.deleteShipment()));
    }

    private void handleResponse(String response) {
        if (response.equals("success")) {
            openOtherWindow("client_room", sendButton);
        } else {
            shipmentStatusLabel.setText("Ошибка");
        }
    }

    private void fillShipment() {
        clientListComboBox.setValue(service.getClient().getName());
        clientListComboBox.setDisable(true);
        productListComboBox.setValue(
                service.getProduct(service.getShipment().getProductKey()).getName());
        productListComboBox.setDisable(true);
        providerListComboBox.setValue(
                service.getProvider(service.getShipment().getProviderRegistrationNumber()).getName());
        providerListComboBox.setDisable(true);
        productQuantityTextField.setText(service.getShipment().getProductQuantity().toString());
        productQuantityTextField.setDisable(true);
    }
}
