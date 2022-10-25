package net.skaia.pasteger.sump.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Shipment;

public class ClientRoomService {
    private static final ClientRoomService clientRoomService = new ClientRoomService();

    private ClientRoomService() {
    }

    public static ClientRoomService getInstance() {
        return clientRoomService;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    private Client client;
    private ObservableList<Shipment> shipmentList;

    public ObservableList<String> getRequestedShipments() {
        setShipmentList();

        ObservableList<String> requestedShipmentStringList = FXCollections.observableArrayList();

        shipmentList.filtered(shipment -> shipment.getStatus().equals("requested")).forEach(shipment ->
                requestedShipmentStringList.add(
                        shipment.getNumber() + " " +
                                shipment.getProductKey() + " " +
                                shipment.getProductQuantity() + " "
                )
        );

        return requestedShipmentStringList;
    }

    public ObservableList<String> getAcceptedShipments() {
        ObservableList<String> requestedShipmentStringList = FXCollections.observableArrayList();

        shipmentList.filtered(shipment -> shipment.getStatus().equals("accepted")).forEach(shipment ->
                requestedShipmentStringList.add(
                        shipment.getNumber() + " " +
                                shipment.getProductKey() + " " +
                                shipment.getProductQuantity() + " "
                )
        );

        return requestedShipmentStringList;
    }

    public ObservableList<String> getArrivalsShipments() {
        ObservableList<String> requestedShipmentStringList = FXCollections.observableArrayList();

        shipmentList.filtered(shipment -> shipment.getStatus().equals("arrivals")).forEach(shipment ->
                requestedShipmentStringList.add(
                        shipment.getNumber() + " " +
                                shipment.getProductKey() + " " +
                                shipment.getProductQuantity() + " "
                )
        );

        return requestedShipmentStringList;
    }

    public ObservableList<String> getRejectedShipments() {
        ObservableList<String> requestedShipmentStringList = FXCollections.observableArrayList();

        shipmentList.filtered(shipment -> shipment.getStatus().equals("rejected")).forEach(shipment ->
                requestedShipmentStringList.add(
                        shipment.getNumber() + " " +
                                shipment.getProductKey() + " " +
                                shipment.getProductQuantity() + " "
                )
        );

        return requestedShipmentStringList;
    }


    public void acceptShipment(String value) {
        try {
            Long number = Long.parseLong(value.split(" ")[0]);

            databaseHandler.acceptShipment(number);
        } catch (Exception ignored) {
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private void setShipmentList() {
        shipmentList = databaseHandler.getShipmentStringList(client.getClientRegistrationNumber());
    }
}
