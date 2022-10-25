package net.skaia.pasteger.sump.service;

import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.utilities.PasswordHasher;

import java.sql.SQLException;

public class AuthorizationService {
    private static final AuthorizationService authorizationService = new AuthorizationService();

    private AuthorizationService() {
    }

    public static AuthorizationService getInstance() {
        return authorizationService;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
    private final ClientRoomService clientRoomService = ClientRoomService.getInstance();
    private final ProviderRoomService providerRoomService = ProviderRoomService.getInstance();

    public String authorization(String login, String password) {
        try {
            if (login.equals(""))
                return "Введи логин";
            if (password.equals(""))
                return "Введи пароль";

            password = PasswordHasher.hashingPassword(password);
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
                providerRoomService.setProvider(databaseHandler.getProvider(id));
            } else {
                clientRoomService.setClient(databaseHandler.getClient(id));
            }

            return entity;
        } catch (SQLException e) {
            return "Неизвестная ошибка";
        }
    }
}
