package net.skaia.pasteger.sump.service;

import net.skaia.pasteger.sump.database.DatabaseHandler;
import net.skaia.pasteger.sump.entity.Client;
import net.skaia.pasteger.sump.entity.Provider;
import net.skaia.pasteger.sump.utilities.PasswordHasher;

public class RegistrationService {
    private static final RegistrationService registrationService = new RegistrationService();

    private RegistrationService() {
    }

    public static RegistrationService getInstance() {
        return registrationService;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

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
        password = PasswordHasher.hashingPassword(password);
        if (databaseHandler.checkExistenceRegistrationNumber(entity, userId))
            return "Регистрационный номер занят";

        return databaseHandler.registration(userId, name, address, phone, entity, login, password);
    }
}
