package net.skaia.pasteger.sump.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;

import static net.skaia.pasteger.sump.Constants.*;

public class Controller {
    private static Stage stage;
    protected final MediaPlayer mediaPlayer = new MediaPlayer(START_MUSIC);
    private static final String PATH_TO_LAYOUT = "/net/skaia/pasteger/sump/layout/";

    public static void openOtherWindow(String layoutName, Button button) {
        button.getScene().getWindow().hide();
        openNewWindow(layoutName);
    }

    public static void openOtherWindow(String layoutName, ImageView imageView) {
        imageView.getScene().getWindow().hide();
        openNewWindow(layoutName);
    }

    public static void openOtherWindow(String layoutName, Label label) {
        label.getScene().getWindow().hide();
        openNewWindow(layoutName);
    }

    public static void openOtherWindow(String layoutName, TextField textField) {
        textField.getScene().getWindow().hide();
        openNewWindow(layoutName);
    }

    public static void openNewWindow(String window) {
        String path = PATH_TO_LAYOUT + window + ".fxml";
        FXMLLoader loader = new FXMLLoader(Controller.class.getResource(path));
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
}
