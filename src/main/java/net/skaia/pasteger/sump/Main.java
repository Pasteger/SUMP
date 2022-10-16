package net.skaia.pasteger.sump;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;

import net.skaia.pasteger.sump.controller.Controller;

import static net.skaia.pasteger.sump.Constants.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(START_LAYOUT));
        try {
            fxmlLoader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Controller.setStage(stage);
        Controller.setShowStage(fxmlLoader);
    }

    public static void main(String[] args) {
        launch();
    }
}
