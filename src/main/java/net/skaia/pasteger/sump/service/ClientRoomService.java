package net.skaia.pasteger.sump.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Shipment;

import java.util.Objects;

public class ClientRoomService {
    private static final ClientRoomService clientRoomService = new ClientRoomService();

    private ClientRoomService() {
    }

    public static ClientRoomService getInstance() {
        return clientRoomService;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private final ClientShipmentHandlerService clientShipmentHandlerService = ClientShipmentHandlerService.getInstance();

    private Client client;
    private ObservableList<Shipment> shipmentList;
    private long refreshTime;

    public ObservableList<String> getShipmentsForType(String type) {
        ObservableList<String> shipmentStringList = FXCollections.observableArrayList();

        if (System.currentTimeMillis() - refreshTime > 10000) {
            setShipmentList();
            refreshTime = System.currentTimeMillis();
        }

        if (type.equals("requested")) {
            shipmentStringList.add("Новая");
        }

        shipmentList.filtered(shipment -> shipment.getStatus().equals(type)).forEach(shipment ->
                shipmentStringList.add(
                        shipment.getNumber() + " " +
                                shipment.getProductKey() + " " +
                                shipment.getProductQuantity() + " "
                )
        );

        return shipmentStringList;
    }

    public void injectShipment(String stringShipment) {
        Shipment shipment = new Shipment();

        if (!stringShipment.equals("Новая")) {
            Long id = Long.parseLong(stringShipment.split(" ")[0]);
            shipment = shipmentList.stream().filter(shipmentInList ->
                    Objects.equals(shipmentInList.getNumber(), id)).findFirst().orElse(null);
        } else {
            //Этот else важен, не удаляй его
            shipment.setStatus("new");
        }

        clientShipmentHandlerService.setClient(client);
        clientShipmentHandlerService.setShipment(shipment);
    }

    private void setShipmentList() {
        shipmentList = databaseHandler.getShipmentList(
                client.getClientRegistrationNumber(), "client_registration_number");
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
