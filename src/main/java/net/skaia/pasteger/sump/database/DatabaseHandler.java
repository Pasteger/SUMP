package net.skaia.pasteger.sump.database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Product;
import net.skaia.pasteger.sump.entity.Provider;
import net.skaia.pasteger.sump.entity.Shipment;

import java.sql.*;

import static net.skaia.pasteger.sump.Constants.*;

public class DatabaseHandler {
    private static final DatabaseHandler databaseHandler = new DatabaseHandler();

    public static DatabaseHandler getInstance() {
        return databaseHandler;
    }

    private DatabaseHandler() {
        String connectionString = "jdbc:postgresql://localhost:5432/sump";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            dbConnection = DriverManager.getConnection(connectionString, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private final Connection dbConnection;

    public String authorization(String login, String password) throws SQLException {
        String request = "select * from authorization_data where login = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return "login not found";
        }
        String request2 = "select * from authorization_data where login = ? and password = ?";
        preparedStatement = dbConnection.prepareStatement(request2);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();

        return resultSet.next() ?
                resultSet.getString("user_type") + ":" + resultSet.getInt("user_id") :
                "incorrect password";
    }

    public Provider getProvider(Long id) {
        Provider provider = new Provider();
        try {
            String request = "select * from provider where provider_registration_number = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            provider.setProviderRegistrationNumber(resultSet.getLong("provider_registration_number"));
            provider.setName(resultSet.getString("name"));
            provider.setAddress(resultSet.getString("address"));
            provider.setPhoneNumber(resultSet.getString("phone_number"));

            return provider;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Client getClient(Long id) {
        Client client = new Client();
        try {
            String request = "select * from client where client_registration_number = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            client.setClientRegistrationNumber(resultSet.getLong("client_registration_number"));
            client.setName(resultSet.getString("name"));
            client.setAddress(resultSet.getString("address"));
            client.setPhoneNumber(resultSet.getString("phone_number"));

            return client;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String registration(Long id, String name, String address, String phone,
                               String entity, String login, String password) {
        try {
            String request = "insert into authorization_data (id, login, password, user_type, user_id) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, generateNewId("authorization_data", "id"));
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, entity);
            preparedStatement.setLong(5, id);
            preparedStatement.executeUpdate();

            request = "insert into " + entity + " (" + entity + "_registration_number, name, address, phone_number) values(?,?,?,?)";
            preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, address);
            preparedStatement.setString(4, phone);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            return "error";
        }
        return "success";
    }

    public boolean checkExistenceRegistrationNumber(String entity, Long id) {
        try {
            String request = "select * from " + entity + " where " + entity + "_registration_number = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            return true;
        }
    }


    public ObservableList<Shipment> getShipmentList(Long registrationNumber, String entityRegistrationNumberString) {
        ObservableList<Shipment> shipments = FXCollections.observableArrayList();
        try {
            String request = "select * from shipment where " + entityRegistrationNumberString + " = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, registrationNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Shipment shipment = new Shipment();
                shipment.setNumber(resultSet.getLong("number"));
                shipment.setStatus(resultSet.getString("status"));
                shipment.setProductKey(resultSet.getLong("product_key"));
                shipment.setProductQuantity(resultSet.getInt("product_quantity"));
                shipment.setProviderRegistrationNumber(resultSet.getLong("provider_registration_number"));
                shipment.setClientRegistrationNumber(resultSet.getLong("client_registration_number"));

                shipments.add(shipment);
            }
            return shipments;
        } catch (SQLException e) {
            return shipments;
        }
    }

    public ObservableList<Product> getProductList() {
        ObservableList<Product> productList = FXCollections.observableArrayList();
        try {
            String request = "select * from product";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductKey(resultSet.getLong("product_key"));
                product.setName(resultSet.getString("name"));
                product.setType(resultSet.getString("type"));
                product.setPrice(resultSet.getInt("price"));
                product.setWeight(resultSet.getInt("weight"));
                product.setPackageType(resultSet.getString("package_type"));

                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            return productList;
        }
    }

    public ObservableList<Provider> getProviderList() {
        ObservableList<Provider> providerList = FXCollections.observableArrayList();
        try {
            String request = "select * from provider";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Provider provider = new Provider();
                provider.setProviderRegistrationNumber(resultSet.getLong("provider_registration_number"));
                provider.setName(resultSet.getString("name"));
                provider.setAddress(resultSet.getString("address"));
                provider.setPhoneNumber(resultSet.getString("phone_number"));

                providerList.add(provider);
            }
            return providerList;
        } catch (SQLException e) {
            return providerList;
        }
    }
    public ObservableList<Client> getClientList() {
        ObservableList<Client> clientList = FXCollections.observableArrayList();
        try {
            String request = "select * from client";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setClientRegistrationNumber(resultSet.getLong("client_registration_number"));
                client.setName(resultSet.getString("name"));
                client.setAddress(resultSet.getString("address"));
                client.setPhoneNumber(resultSet.getString("phone_number"));

                clientList.add(client);
            }
            return clientList;
        } catch (SQLException e) {
            return clientList;
        }
    }

    public void updateShipmentStatus(Long number, String status) throws SQLException {
        String request = "update shipment set status = ? where number = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
        preparedStatement.setString(1, status);
        preparedStatement.setLong(2, number);
        preparedStatement.executeUpdate();
    }

    public void deleteShipment(Long number) throws SQLException {
        String request = "delete from shipment where number = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
        preparedStatement.setLong(1, number);
        preparedStatement.executeUpdate();
    }

    public String insertShipment(Shipment shipment) {
        try {
            shipment.setNumber(generateNewId("shipment", "number"));
            String request = "insert into shipment" +
                    "(number, status, product_key, product_quantity, provider_registration_number, client_registration_number)" +
                    "values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, shipment.getNumber());
            preparedStatement.setString(2, shipment.getStatus());
            preparedStatement.setLong(3, shipment.getProductKey());
            preparedStatement.setInt(4, shipment.getProductQuantity());
            preparedStatement.setLong(5, shipment.getProviderRegistrationNumber());
            preparedStatement.setLong(6, shipment.getClientRegistrationNumber());
            preparedStatement.executeUpdate();

            return "success";
        } catch (SQLException e) {
            return "error";
        }
    }

    private long generateNewId(String table, String id) throws SQLException {
        String request = "select " + id + " from " + table;
        PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
        ResultSet resultSet = preparedStatement.executeQuery();
        long newId = 0;
        try {
            while (resultSet.next()) {
                long thisId = Long.parseLong(resultSet.getString(id));
                if (newId < thisId) {
                    newId = thisId;
                }
            }
        } catch (Exception ignored) {
        }
        return ++newId;
    }
}
