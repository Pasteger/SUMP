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
        requestedShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_REQUESTED_SHIPMENT_COMBO_BOX);
        arrivalsShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_ARRIVALS_SHIPMENT_COMBO_BOX);
        rejectedShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_REJECTED_ARRIVALS_SHIPMENT_COMBO_BOX);
        acceptedShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_ACCEPTED_SHIPMENT_COMBO_BOX);

        updateShipmentsComboBoxValue();

        exitButton.setOnAction(actionEvent ->
                openOtherWindow("authorization", exitButton));


        arrivalsShipmentsComboBox.setOnAction(event -> {
            if (arrivalsShipmentsComboBox.getValue() == null ||
                    arrivalsShipmentsComboBox.getValue().equals(DEFAULT_VALUE_CLIENT_ARRIVALS_SHIPMENT_COMBO_BOX))
                return;
            String value = arrivalsShipmentsComboBox.getValue();
            service.acceptShipment(value);
            updateShipmentsComboBoxValue();
        });

        rejectedShipmentsComboBox.setOnAction(event ->
                Platform.runLater(() -> {
                    if (rejectedShipmentsComboBox.getValue() == null ||
                            rejectedShipmentsComboBox.getValue().equals(DEFAULT_VALUE_CLIENT_REJECTED_ARRIVALS_SHIPMENT_COMBO_BOX))
                        return;
                    String value = rejectedShipmentsComboBox.getValue();

                    rejectedShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_REJECTED_ARRIVALS_SHIPMENT_COMBO_BOX);
                })
        );

        acceptedShipmentsComboBox.setOnAction(event ->
                Platform.runLater(() -> {
                    if (acceptedShipmentsComboBox.getValue() == null ||
                            acceptedShipmentsComboBox.getValue().equals(DEFAULT_VALUE_CLIENT_ACCEPTED_SHIPMENT_COMBO_BOX))
                        return;
                    String value = acceptedShipmentsComboBox.getValue();

                    acceptedShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_ACCEPTED_SHIPMENT_COMBO_BOX);
                })
        );

        requestedShipmentsComboBox.setOnAction(event ->
                Platform.runLater(() -> {
                    if (requestedShipmentsComboBox.getValue() == null ||
                            requestedShipmentsComboBox.getValue().equals(DEFAULT_VALUE_CLIENT_REQUESTED_SHIPMENT_COMBO_BOX))
                        return;
                    String value = requestedShipmentsComboBox.getValue();

                    requestedShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_REQUESTED_SHIPMENT_COMBO_BOX);
                })
        );

        updateShipmentsImageView.setOnMouseClicked(mouseEvent -> updateShipmentsComboBoxValue());
    }

    private void updateShipmentsComboBoxValue() {
        Platform.runLater(() -> {
            EventHandler<ActionEvent> handler = arrivalsShipmentsComboBox.getOnAction();
            arrivalsShipmentsComboBox.setOnAction(null);

            requestedShipmentsComboBox.setItems(service.getRequestedShipments());
            acceptedShipmentsComboBox.setItems(service.getAcceptedShipments());
            arrivalsShipmentsComboBox.setItems(service.getArrivalsShipments());
            rejectedShipmentsComboBox.setItems(service.getRejectedShipments());

            arrivalsShipmentsComboBox.setOnAction(handler);

            arrivalsRequestedShipmentsImageView.setVisible(
                    !(arrivalsShipmentsComboBox.getItems() == null ||
                            arrivalsShipmentsComboBox.getItems().isEmpty()));


            arrivalsShipmentsComboBox.setValue(DEFAULT_VALUE_CLIENT_ARRIVALS_SHIPMENT_COMBO_BOX);
        });
    }
}
