package net.skaia.pasteger.sump.service;

import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Provider;

import java.security.MessageDigest;
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
            if (login.equals(""))
                return "Введи логин";
            if (password.equals(""))
                return "Введи пароль";

            password = passwordHashing(password);
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

    public String registration(String id, String name, String address, String phone,
                               String entity, String login, String password) {
        if (entity.equals("Поставщик")) {
            entity = "provider";
        } else if (entity.equals("Закупщик")) {
            entity = "client";
        } else {
            return "Выбери роль";
        }

        if (id.equals(""))
            return "Введи регистрационный номер";
        if (name.equals(""))
            return "Введи название";
        if (address.equals(""))
            return "Введи адрес";
        if (phone.equals(""))
            return "Введи номер телефона";
        if (login.equals(""))
            return "Введи логин";
        if (login.length() < 5)
            return "Логин короче 5 символов";
        if (password.equals(""))
            return "Введи пароль";
        if (password.length() < 5)
            return "Пароль короче 5 символов";

        Long userId = Long.parseLong(id);
        password = passwordHashing(password);
        if (databaseHandler.checkExistenceRegistrationNumber(entity, userId))
            return "Регистрационный номер занят";

        return databaseHandler.registration(userId, name, address, phone, entity, login, password);
    }

    private String passwordHashing(String password) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] hash = md5.digest(password.getBytes());
            password = new String(hash);
        } catch (Exception exception) {
            return password;
        }
        return password;
    }
}
