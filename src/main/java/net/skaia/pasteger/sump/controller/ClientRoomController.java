package net.skaia.pasteger.sump.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import net.skaia.pasteger.sump.service.ClientRoomService;

import static net.skaia.pasteger.sump.Constants.*;
import static net.skaia.pasteger.sump.Constants.DEFAULT_VALUE_CLIENT_ACCEPTED_SHIPMENT_COMBO_BOX;

public class ClientRoomController extends Controller {
    ClientRoomService service = ClientRoomService.getInstance();

    @FXML
    public ImageView updateShipmentsImageView;
    @FXML
    public ImageView arrivalsRequestedShipmentsImageView;
    @FXML
    private Button exitButton;
    @FXML
    private ComboBox<String> requestedShipmentsComboBox;
    @FXML
    private ComboBox<String> arrivalsShipmentsComboBox;
    @FXML
    private ComboBox<String> acceptedShipmentsComboBox;
    @FXML
    private ComboBox<String> rejectedShipmentsComboBox;

    @FXML
    void initialize() {
        requestedShipmentsComboBox.setValue(DEFAULT_VALUE_REQUESTED_SHIPMENT_COMBO_BOX);
        arrivalsShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_ARRIVALS_SHIPMENT_COMBO_BOX);
        rejectedShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_REJECTED_ARRIVALS_SHIPMENT_COMBO_BOX);
        acceptedShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_ACCEPTED_SHIPMENT_COMBO_BOX);

        updateShipmentsComboBoxValue();

        arrivalsShipmentsComboBox.setOnAction(actionEvent ->
                actionEventForComboBoxes(arrivalsShipmentsComboBox, DEFAULT_VALUE_CLIENT_ARRIVALS_SHIPMENT_COMBO_BOX));

        rejectedShipmentsComboBox.setOnAction(actionEvent ->
                actionEventForComboBoxes(rejectedShipmentsComboBox, DEFAULT_VALUE_CLIENT_REJECTED_ARRIVALS_SHIPMENT_COMBO_BOX));

        acceptedShipmentsComboBox.setOnAction(actionEvent ->
                actionEventForComboBoxes(acceptedShipmentsComboBox, DEFAULT_VALUE_CLIENT_ACCEPTED_SHIPMENT_COMBO_BOX));

        requestedShipmentsComboBox.setOnAction(actionEvent ->
                actionEventForComboBoxes(requestedShipmentsComboBox, DEFAULT_VALUE_REQUESTED_SHIPMENT_COMBO_BOX));

        updateShipmentsImageView.setOnMouseClicked(mouseEvent -> updateShipmentsComboBoxValue());
        exitButton.setOnAction(actionEvent -> openOtherWindow("authorization", exitButton));
    }

    //Такой же метод находится в ProviderRoomController и я не знаю, как это оптимизировать
    private void actionEventForComboBoxes(ComboBox<String> comboBox, String constant) {
        Platform.runLater(() -> {
            if (comboBox.getValue() == null ||
                    comboBox.getValue().equals(constant))
                return;
            String value = comboBox.getValue();

            comboBox.setValue(constant);

            service.injectShipment(value);
            openOtherWindow("client_shipment_handler", comboBox);
        });
    }

    private void updateShipmentsComboBoxValue() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> handler = arrivalsShipmentsComboBox.getOnAction();
            arrivalsShipmentsComboBox.setOnAction(null);

            requestedShipmentsComboBox.setItems(service.getShipmentsForType("requested"));
            acceptedShipmentsComboBox.setItems(service.getShipmentsForType("accepted"));
            arrivalsShipmentsComboBox.setItems(service.getShipmentsForType("arrivals"));
            rejectedShipmentsComboBox.setItems(service.getShipmentsForType("rejected"));

            arrivalsShipmentsComboBox.setOnAction(handler);

            arrivalsRequestedShipmentsImageView.setVisible(
                    !(arrivalsShipmentsComboBox.getItems() == null ||
                            arrivalsShipmentsComboBox.getItems().isEmpty()));
        });
    }
}
