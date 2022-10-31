package net.skaia.pasteger.sump.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import net.skaia.pasteger.sump.service.ProviderShipmentHandlerService;

public class ProviderShipmentHandlerController extends Controller {
    ProviderShipmentHandlerService service = ProviderShipmentHandlerService.getInstance();

    @FXML
    private Label shipmentStatusLabel;
    @FXML
    private Button backButton;
    @FXML
    private ComboBox<String> clientListComboBox;
    @FXML
    private ComboBox<String> productListComboBox;
    @FXML
    private TextField productQuantityTextField;
    @FXML
    private ComboBox<String> providerListComboBox;
    @FXML
    private Button sendButton;

    @FXML
    void initialize() {
        sendButton.setVisible(false);

        switch (service.getShipment().getStatus()) {
            case "arrivals" -> {
                shipmentStatusLabel.setText("ОТПРАВЛЕННАЯ ПОСТАВКА");
                fillShipment();
            }
            case "requested" -> {
                shipmentStatusLabel.setText("ЗАПРОШЕННАЯ ПОСТАВКА");
                sendButton.setVisible(true);
                fillShipment();
            }
        }

        backButton.setOnAction(actionEvent -> openOtherWindow("provider_room", backButton));

        sendButton.setOnAction(actionEvent -> handleResponse(service.sendShipment()));
    }

    private void handleResponse(String response) {
        if (response.equals("success")) {
            openOtherWindow("provider_room", sendButton);
        } else {
            shipmentStatusLabel.setText("Ошибка");
        }
    }

    private void fillShipment() {
        clientListComboBox.setValue(
                service.getClient(service.getShipment().getClientRegistrationNumber()).getName());
        clientListComboBox.setDisable(true);
        productListComboBox.setValue(
                service.getProduct(service.getShipment().getProductKey()).getName());
        productListComboBox.setDisable(true);
        providerListComboBox.setValue(service.getProvider().getName());
        providerListComboBox.setDisable(true);
        productQuantityTextField.setText(service.getShipment().getProductQuantity().toString());
        productQuantityTextField.setDisable(true);
    }
}
