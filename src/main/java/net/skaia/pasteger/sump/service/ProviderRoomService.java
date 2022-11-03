package net.skaia.pasteger.sump.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.entity.Provider;
import net.skaia.pasteger.sump.entity.Shipment;

import java.util.Objects;

public class ProviderRoomService {
    private static final ProviderRoomService providerRoomService = new ProviderRoomService();

    private ProviderRoomService() {
    }

    public static ProviderRoomService getInstance() {
        return providerRoomService;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private final ProviderShipmentHandlerService providerShipmentHandlerService = ProviderShipmentHandlerService.getInstance();

    private Provider provider;
    private ObservableList<Shipment> shipmentList;
    private Long refreshTime;

    public ObservableList<String> getShipmentsForType(String type) {
        ObservableList<String> shipmentStringList = FXCollections.observableArrayList();

        if (System.currentTimeMillis() - refreshTime > 10000) {
            setShipmentList();
            refreshTime = System.currentTimeMillis();
        }

        shipmentList.filtered(shipment ->
                type.equals("requested") == shipment.getStatus().equals("requested")).forEach(shipment ->
                shipmentStringList.add(
                        shipment.getNumber() + " " +
                                shipment.getProductKey() + " " +
                                shipment.getProductQuantity() + " "
                )
        );

        return shipmentStringList;
    }

    private void setShipmentList() {
        shipmentList = databaseHandler.getShipmentList(
                provider.getProviderRegistrationNumber(), "provider_registration_number");
    }

    public void injectShipment(String stringShipment) {
        Long id = Long.parseLong(stringShipment.split(" ")[0]);
        Shipment shipment = shipmentList.stream().filter(shipmentInList ->
                Objects.equals(shipmentInList.getNumber(), id)).findFirst().orElse(null);

        providerShipmentHandlerService.setProvider(provider);
        providerShipmentHandlerService.setShipment(shipment);
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
