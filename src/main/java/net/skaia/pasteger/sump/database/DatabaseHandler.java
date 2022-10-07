package net.skaia.pasteger.sump.database;

import java.sql.*;

public class DatabaseHandler {
    private static final DatabaseHandler databaseHandler = new DatabaseHandler();

    public static DatabaseHandler getInstance() {
        return databaseHandler;
    }

    private DatabaseHandler() {
        String connectionString = "jdbc:postgresql://localhost:5432/professional";
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
        /*public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
        protected String dbHost = "localhost";
        protected String dbPort = "3306";
        protected String dbUser = "root";
        protected String dbPass = "root";
        protected String dbName = "chef";
        }*/
    }

    private final Connection dbConnection;

    public String authorization(String login, String password) throws SQLException {
        String request = "SELECT * FROM users where login = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
        preparedStatement.setString(1, login);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            return "login not found";
        }
        String request2 = "SELECT * FROM users where login = ? and password = ?";
        preparedStatement = dbConnection.prepareStatement(request2);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();
        return resultSet.next() ? resultSet.getString("id") : "incorrect password";
    }

    /*public User findUserById(Long id){
        try {
            String request = "SELECT * FROM users where id = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return convertResultSetToUser(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean registration(String name, String lastname, String secondname, String birthdayDate,
                             String login, String password, String registrationDate) {
        String request = "INSERT INTO users (id, name, lastname, secondname, birthday_date, registration_date," +
                "role, login, password) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, getNewId());
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, lastname);
            preparedStatement.setString(4, secondname);
            preparedStatement.setString(5, birthdayDate);
            preparedStatement.setString(6, registrationDate);
            preparedStatement.setBoolean(7, false);
            preparedStatement.setString(8, login);
            preparedStatement.setString(9, password);

            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean findByLogin(String login) {
        String request = "SELECT * FROM users WHERE login = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<User> getUsers(){
        ObservableList<User> users = FXCollections.observableArrayList();
        String request = "SELECT * FROM users";
        try {
            PreparedStatement statement = dbConnection.prepareStatement(request);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                User user = convertResultSetToUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public Congratulation getCongratulation(String date) throws SQLException {
        Congratulation congratulation = new Congratulation();
        String request = "SELECT * FROM congratulations WHERE congratulations.date = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
        preparedStatement.setString(1, date);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        congratulation.setDate(resultSet.getString("date"));
        congratulation.setImage(resultSet.getString("image"));
        congratulation.setMessage(resultSet.getString("message"));
        return congratulation;
    }
    public List<Congratulation> getCongratulations(){
        List<Congratulation> congratulations = new ArrayList<>();
        String request = "SELECT * FROM congratulations";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Congratulation congratulation = new Congratulation();
                congratulation.setDate(resultSet.getString("date"));
                congratulation.setImage(resultSet.getString("image"));
                congratulation.setMessage(resultSet.getString("message"));
                congratulations.add(congratulation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return congratulations;
    }

    public boolean saveCongratulation(Congratulation congratulation) {
        String request = "INSERT INTO congratulations (date, image, message) VALUES(?,?,?)";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setString(1, congratulation.getDate());
            preparedStatement.setString(2, congratulation.getImage());
            preparedStatement.setString(3, congratulation.getMessage());
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean updateCongratulation(Congratulation congratulation){
        String request = "UPDATE congratulations SET image = ?, message = ? WHERE date = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setString(1, congratulation.getImage());
            preparedStatement.setString(2, congratulation.getMessage());
            preparedStatement.setString(3, congratulation.getDate());
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public boolean deleteCongratulation(Congratulation congratulation){
        String request = "DELETE FROM congratulations where date = ?";
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setString(1, congratulation.getDate());
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    private User convertResultSetToUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setName(resultSet.getString("name"));
        user.setLastname(resultSet.getString("lastname"));
        user.setSecondname(resultSet.getString("secondname"));
        user.setBirthdayDate(resultSet.getString("birthday_date"));
        user.setRegistrationDate(resultSet.getString("registration_date"));
        user.setRole(resultSet.getBoolean("role"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        return user;
    }*/

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
