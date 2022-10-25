package net.skaia.pasteger.sump.service;

import javafx.collections.ObservableList;
import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.entity.Provider;
import net.skaia.pasteger.sump.entity.Shipment;

public class ProviderRoomService {
    private static final ProviderRoomService providerRoomService = new ProviderRoomService();

    private ProviderRoomService() {
    }

    public static ProviderRoomService getInstance() {
        return providerRoomService;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    private Provider provider;
    private ObservableList<Shipment> shipmentList;


    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
