package net.skaia.pasteger.sump.database;

import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Provider;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.Arrays;

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

    public String registration(Long id, String name, String address, String phone,
                               String entity, String login, String password) {
        try {
            String request = "insert into authorization_data (id, login, password, user_type, user_id) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, getNewId());
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

    public boolean checkExistenceRegistrationNumber(String entity, Long id){
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

    private long getNewId() throws SQLException {
        String request = "select * from authorization_data";
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
