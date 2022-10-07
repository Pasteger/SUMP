module net.skaia.pasteger.sump {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;
    requires javafx.media;

    opens net.skaia.pasteger.sump to javafx.fxml;
    exports net.skaia.pasteger.sump;
    opens net.skaia.pasteger.sump.controller to javafx.fxml;
    exports net.skaia.pasteger.sump.controller;
}