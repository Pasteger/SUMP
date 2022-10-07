package net.skaia.pasteger.sump.service;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import net.skaia.pasteger.sump.database.DatabaseHandler;
import java.sql.SQLException;

public class AuthorizationService {
    private static final AuthorizationService AUTHORIZATION_SERVICE = new AuthorizationService();
    private AuthorizationService(){}
    public static AuthorizationService getInstance(){return  AUTHORIZATION_SERVICE;}
    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    public boolean authorization(String login, String password, Label errorLabel, ImageView fh){
        try {
            String result = databaseHandler.authorization(login, password);
            if (result.equals("login not found")){
                errorLabel.setText("Пользователь с таким логином не найден");
                return false;
            }
            if (result.equals("incorrect password")){
                errorLabel.setText("О нет! Неверный пароль!");
                return false;
            }
            errorLabel.setText("Успешно!");
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
