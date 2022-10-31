package net.skaia.pasteger.sump.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import net.skaia.pasteger.sump.service.ProviderRoomService;

import static net.skaia.pasteger.sump.Constants.*;

public class ProviderRoomController extends Controller {
    ProviderRoomService service = ProviderRoomService.getInstance();

    @FXML
    private ImageView arrivalsRequestedShipmentsImageView;

    @FXML
    private Button exitButton;
    @FXML
    private ComboBox<String> freeShipmentsComboBox;
    @FXML
    private ComboBox<String> requestedShipmentsComboBox;
    @FXML
    private ComboBox<String> responsedShipmentsComboBox;
    @FXML
    private ImageView updateShipmentsImageView;

    @FXML
    void initialize() {
        requestedShipmentsComboBox.setValue(DEFAULT_VALUE_REQUESTED_SHIPMENT_COMBO_BOX);
        responsedShipmentsComboBox.setValue(DEFAULT_VALUE_PROVIDER_RESPONSED_SHIPMENT_COMBO_BOX);

        updateShipmentsComboBoxValue();

        responsedShipmentsComboBox.setOnAction(event ->
                Platform.runLater(() -> {
                    if (responsedShipmentsComboBox.getValue() == null ||
                            responsedShipmentsComboBox.getValue().equals(DEFAULT_VALUE_PROVIDER_RESPONSED_SHIPMENT_COMBO_BOX))
                        return;
                    String value = responsedShipmentsComboBox.getValue();

                    responsedShipmentsComboBox.setValue(DEFAULT_VALUE_PROVIDER_RESPONSED_SHIPMENT_COMBO_BOX);

                    service.injectShipment(value);
                    openOtherWindow("provider_shipment_handler", responsedShipmentsComboBox);
                })
        );

        requestedShipmentsComboBox.setOnAction(event ->
                Platform.runLater(() -> {
                    if (requestedShipmentsComboBox.getValue() == null ||
                            requestedShipmentsComboBox.getValue().equals(DEFAULT_VALUE_REQUESTED_SHIPMENT_COMBO_BOX))
                        return;
                    String value = requestedShipmentsComboBox.getValue();

                    requestedShipmentsComboBox.setValue(DEFAULT_VALUE_REQUESTED_SHIPMENT_COMBO_BOX);

                    service.injectShipment(value);
                    openOtherWindow("provider_shipment_handler", requestedShipmentsComboBox);
                })
        );

        updateShipmentsImageView.setOnMouseClicked(mouseEvent -> updateShipmentsComboBoxValue());
        exitButton.setOnAction(actionEvent -> openOtherWindow("authorization", exitButton));
    }

    private void updateShipmentsComboBoxValue() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> handler = requestedShipmentsComboBox.getOnAction();
            requestedShipmentsComboBox.setOnAction(null);

            requestedShipmentsComboBox.setItems(service.getRequestedShipments());
            responsedShipmentsComboBox.setItems(service.getResponsedShipments());

            requestedShipmentsComboBox.setOnAction(handler);

            arrivalsRequestedShipmentsImageView.setVisible(
                    !(requestedShipmentsComboBox.getItems() == null ||
                            requestedShipmentsComboBox.getItems().isEmpty()));
        });
    }
}
