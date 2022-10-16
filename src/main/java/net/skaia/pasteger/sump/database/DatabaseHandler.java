package net.skaia.pasteger.sump.database;

import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Provider;

import java.sql.*;

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
            dbConnection = DriverManager.getConnection(connectionString, "postgres", "890123890123");
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

    private long getNewId() throws SQLException {
        String request = "SELECT * FROM users";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
        ResultSet resultSet = preparedStatement.executeQuery();
        long id = 0;
        try {
            while (resultSet.next()) {
                long thisId = Long.parseLong(resultSet.getString("id"));
                if (id < thisId) {
                    id = thisId;
                }
            }
        } catch (Exception ignored) {
        }
        return ++id;
    }
}
