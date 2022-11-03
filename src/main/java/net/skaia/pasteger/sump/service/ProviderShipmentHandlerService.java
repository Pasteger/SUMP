package net.skaia.pasteger.sump.service;

import javafx.collections.ObservableList;
import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Product;
import net.skaia.pasteger.sump.entity.Provider;
import net.skaia.pasteger.sump.entity.Shipment;

import java.util.Objects;
import java.util.Optional;

public class ProviderShipmentHandlerService {
    private static final ProviderShipmentHandlerService providerShipmentHandlerService = new ProviderShipmentHandlerService();

    private ProviderShipmentHandlerService() {
    }

    public static ProviderShipmentHandlerService getInstance() {
        return providerShipmentHandlerService;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private ObservableList<Product> productList;
    private ObservableList<Client> clientList;

    private Shipment shipment;
    private Provider provider;

    public String sendShipment() {
        try {
            databaseHandler.updateShipmentStatus(shipment.getNumber(), "arrivals");
            return "success";
        } catch (Exception exception) {
            return "error";
        }
    }

    public Product getProduct(Long key) {
        setProductList();
        Optional<Product> productOptional = productList.stream().filter(product ->
                Objects.equals(product.getProductKey(), key)).findFirst();
        return productOptional.orElse(null);
    }

    public Client getClient(Long clientRegistrationNumber) {
        setClientList();
        Optional<Client> clientOptional = clientList.stream().filter(client ->
                Objects.equals(client.getClientRegistrationNumber(), clientRegistrationNumber)).findFirst();
        return clientOptional.orElse(null);
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public Provider getProvider() {
        return provider;
    }

    private void setClientList() {
        this.clientList = databaseHandler.getClientList();
    }

    private void setProductList() {
        this.productList = databaseHandler.getProductList();
    }
}
