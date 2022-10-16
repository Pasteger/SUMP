package net.skaia.pasteger.sump.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

import static net.skaia.pasteger.sump.Constants.*;

public class Controller {
    private static Stage stage;

    public static void openOtherWindow(String window, Button button) {
        button.getScene().getWindow().hide();
        openNewWindow(window);
    }

    public static void openOtherWindow(String window, ImageView imageView) {
        imageView.getScene().getWindow().hide();
        openNewWindow(window);
    }

    public static void openOtherWindow(String window, Label label) {
        label.getScene().getWindow().hide();
        openNewWindow(window);
    }

    public static void openOtherWindow(String window, TextField textField) {
        textField.getScene().getWindow().hide();
        openNewWindow(window);
    }

    public static void openNewWindow(String window) {
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource(window));
        try {
            loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        setShowStage(loader);
    }

    public static void setShowStage(FXMLLoader loader) {
        stage.setTitle("SUMP");
        stage.getIcons().add(new Image(STRING_FILE_TO_LOGO));
        Parent root = loader.getRoot();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void setStage(Stage stage) {
        Controller.stage = stage;
    }

    public static Stage getStage() {
        return stage;
    }
}
