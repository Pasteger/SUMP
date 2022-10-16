package net.skaia.pasteger.sump.service;

import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Provider;

import java.sql.SQLException;

public class UIService {
    private static final UIService UI_SERVICE = new UIService();

    private UIService() {
    }

    public static UIService getInstance() {
        return UI_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    private Provider provider;
    private Client client;

    public String authorization(String login, String password) {
        try {
            String response = databaseHandler.authorization(login, password);
            if (response.equals("login not found")) {
                return "Пользователь не найден";
            }
            if (response.equals("incorrect password")) {
                return "Неверный пароль";
            }

            String entity = response.split(":")[0];
            Long id = Long.parseLong(response.split(":")[1]);

            if (entity.equals("provider")) {
                provider = databaseHandler.getProvider(id);
            } else {
                client = databaseHandler.getClient(id);
            }

            return entity;
        } catch (SQLException e) {
            return "Неизвестная ошибка";
        }
    }
}
