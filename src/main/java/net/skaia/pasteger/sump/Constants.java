package net.skaia.pasteger.sump;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;

import java.io.File;

public class Constants {
    public static final ObservableList<String> ENTITIES_FOR_REGISTRATION_ENTITY_CHOICE_COMBO_BOX = FXCollections.observableArrayList();
    public static final String DATABASE_USER = "postgres";
    public static final String DATABASE_PASSWORD = "890123890123";
    public static final String DEFAULT_VALUE_WHO_ARE_YOU = "Кто ты?";
    public static final String DEFAULT_VALUE_CLIENT_ARRIVALS_SHIPMENT_COMBO_BOX = "Прибывшие";
    public static final String DEFAULT_VALUE_CLIENT_REJECTED_ARRIVALS_SHIPMENT_COMBO_BOX = "Забракованные";
    public static final String DEFAULT_VALUE_CLIENT_ACCEPTED_SHIPMENT_COMBO_BOX = "Принятые";
    public static final String DEFAULT_VALUE_CLIENT_REQUESTED_SHIPMENT_COMBO_BOX = "Запрошенные";
    public static final String STRING_FILE_TO_LOGO =
            "file:src\\main\\resources\\net\\skaia\\pasteger\\sump\\image\\scratch_disc.png";
    public static final String START_LAYOUT = "layout/authorization.fxml";
    public static final Media START_MUSIC = new Media(new File(
            "src\\main\\resources\\net\\skaia\\pasteger\\sump\\sound\\skaianet.mp3").toURI().toString());

    static {
        ENTITIES_FOR_REGISTRATION_ENTITY_CHOICE_COMBO_BOX.add("Поставщик");
        ENTITIES_FOR_REGISTRATION_ENTITY_CHOICE_COMBO_BOX.add("Закупщик");
    }
}
