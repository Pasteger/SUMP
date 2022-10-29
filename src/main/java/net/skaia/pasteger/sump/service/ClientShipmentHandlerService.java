package net.skaia.pasteger.sump.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Product;
import net.skaia.pasteger.sump.entity.Provider;
import net.skaia.pasteger.sump.entity.Shipment;

import java.util.Objects;
import java.util.Optional;

public class ClientShipmentHandlerService {
    private static final ClientShipmentHandlerService clientShipmentHandlerService = new ClientShipmentHandlerService();

    private ClientShipmentHandlerService() {
    }

    public static ClientShipmentHandlerService getInstance() {
        return clientShipmentHandlerService;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private ObservableList<Provider> providerList;
    private ObservableList<Product> productList;

    private Shipment shipment;
    private Client client;

    public ObservableList<String> getProductStringList() {
        ObservableList<String> productStringList = FXCollections.observableArrayList();

        setProductList();
        productList.forEach(product ->
                productStringList.add(
                        product.getName() + " " +
                                product.getType() + " " +
                                product.getPrice() + " " +
                                product.getWeight() + " " +
                                product.getProductKey()
                )
        );

        return productStringList;
    }

    public ObservableList<String> getProviderStringList() {
        ObservableList<String> providerStringList = FXCollections.observableArrayList();

        setProviderList();
        providerList.forEach(provider ->
                providerStringList.add(
                        provider.getName() + " " +
                                provider.getAddress() + " " +
                                provider.getProviderRegistrationNumber()
                )
        );

        return providerStringList;
    }

    public String sendShipment(String productQuantity, String provider, String product) {
        Shipment shipment = new Shipment();
        shipment.setClientRegistrationNumber(client.getClientRegistrationNumber());
        shipment.setProductQuantity(Integer.parseInt(productQuantity));
        shipment.setProviderRegistrationNumber(findProvider(provider).getProviderRegistrationNumber());
        shipment.setProductKey(findProduct(product).getProductKey());
        shipment.setStatus("requested");
        return databaseHandler.insertShipment(shipment);
    }

    public String acceptShipment() {
        try {
            databaseHandler.updateShipmentStatus(shipment.getNumber(), "accepted");
            return "success";
        } catch (Exception exception) {
            return "error";
        }
    }

    public String rejectShipment() {
        try {
            databaseHandler.updateShipmentStatus(shipment.getNumber(), "rejected");
            return "success";
        } catch (Exception exception) {
            return "error";
        }
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public Client getClient() {
        return client;
    }

    public Shipment getShipment() {
        return shipment;
    }

    private Provider findProvider(String providerString) {
        String[] providerStringArray = providerString.split(" ");
        Long id = Long.parseLong(providerStringArray[providerStringArray.length - 1]);
        return getProvider(id);
    }

    private Product findProduct(String productString) {
        String[] productStringArray = productString.split(" ");
        Long id = Long.parseLong(productStringArray[productStringArray.length - 1]);
        Optional<Product> productOptional = productList.stream().filter(product ->
                Objects.equals(product.getProductKey(), id)).findFirst();
        return productOptional.orElse(null);
    }

    private void setProviderList() {
        this.providerList = databaseHandler.getProviderList();
    }

    private void setProductList() {
        this.productList = databaseHandler.getProductList();
    }

    public Product getProduct(Long key) {
        setProductList();
        Optional<Product> productOptional = productList.stream().filter(product ->
                Objects.equals(product.getProductKey(), key)).findFirst();
        return productOptional.orElse(null);
    }

    public Provider getProvider(Long providerRegistrationNumber) {
        setProviderList();
        Optional<Provider> providerOptional = providerList.stream().filter(provider ->
                Objects.equals(provider.getProviderRegistrationNumber(), providerRegistrationNumber)).findFirst();
        return providerOptional.orElse(null);
    }
}
