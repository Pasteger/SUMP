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
                actionEventForComboBoxes(responsedShipmentsComboBox, DEFAULT_VALUE_PROVIDER_RESPONSED_SHIPMENT_COMBO_BOX));

        requestedShipmentsComboBox.setOnAction(event ->
                actionEventForComboBoxes(requestedShipmentsComboBox, DEFAULT_VALUE_REQUESTED_SHIPMENT_COMBO_BOX));

        updateShipmentsImageView.setOnMouseClicked(mouseEvent -> updateShipmentsComboBoxValue());
        exitButton.setOnAction(actionEvent -> openOtherWindow("authorization", exitButton));
    }

    //Такой же метод находится в ClientRoomController и я не знаю, как это оптимизировать
    private void actionEventForComboBoxes(ComboBox<String> comboBox, String constant) {
        Platform.runLater(() -> {
            if (comboBox.getValue() == null ||
                    comboBox.getValue().equals(constant))
                return;
            String value = comboBox.getValue();

            comboBox.setValue(constant);

            service.injectShipment(value);
            openOtherWindow("provider_shipment_handler", comboBox);
        });
    }

    private void updateShipmentsComboBoxValue() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> handler = requestedShipmentsComboBox.getOnAction();
            requestedShipmentsComboBox.setOnAction(null);

            requestedShipmentsComboBox.setItems(service.getShipmentsForType("requested"));
            responsedShipmentsComboBox.setItems(service.getShipmentsForType("responsed"));

            requestedShipmentsComboBox.setOnAction(handler);

            arrivalsRequestedShipmentsImageView.setVisible(
                    !(requestedShipmentsComboBox.getItems() == null ||
                            requestedShipmentsComboBox.getItems().isEmpty()));
        });
    }
}
