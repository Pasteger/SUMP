package net.skaia.pasteger.sump.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import net.skaia.pasteger.sump.service.AuthorizationService;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class AuthorizationController extends Controller{
    AuthorizationService service = AuthorizationService.getInstance();

    @FXML
    private ImageView start;

    /**
     * Authorization UI objects
     * */
    @FXML
    private Label authorizationTitleLabel;
    @FXML
    private Label authorizationHeadLabel;
    @FXML
    private TextField authorizationLoginTextField;
    @FXML
    private TextField authorizationPasswordTextField;
    @FXML
    private ImageView authorizationButton;
    @FXML
    private Button authorizationRegistrationButton;
    @FXML
    private ImageView authorizationDecorImageView0;
    @FXML
    private ImageView authorizationDecorImageView1;
    @FXML
    private ImageView authorizationDecorImageView2;










    @FXML void initialize() {
        disableAuthorizationUIObjects();

        start.setVisible(true);

        String musicFile = "src\\main\\resources\\net\\skaia\\pasteger\\sump\\sound\\skaianet.mp3";
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        start.setOnMouseClicked(mouseEvent -> {
            mediaPlayer.play();
            start.setDisable(true);
            diskRotation();
        });

        authorizationRegistrationButton.setOnAction(actionEvent -> {
            hideAuthorizationUIObjects();
            showRegistrationUIObjects();
        });

        authorizationButton.setOnMouseClicked(mouseEvent -> {
            authorizationLoginTextField.setText("");
            authorizationPasswordTextField.setText("");
        });
    }

    private void diskRotation(){
        new Thread(() -> {
            float step;
            for (int time = 1000; time < 20000; time++){
                step = (float) time / 1000;
                start.setRotate(start.getRotate() + step);

                if (time > 3000){
                    start.setY(start.getY() - 0.2);
                    start.setOpacity(start.getOpacity() - 0.0005);
                }
                if (start.getOpacity() <= 0.01) {
                    start.setOpacity(0);
                    break;
                }
                threadSleep(1);
            }
            showAuthorizationUIObjects();
        }).start();
    }

    private void showAuthorizationUIObjects(){
        new Thread(() -> {
            enableAuthorizationUIObjects();

            double opacity = 0;
            while (opacity < 1) {
                authorizationTitleLabel.setOpacity(opacity += 0.0005);
                authorizationHeadLabel.setOpacity(opacity += 0.0005);
                authorizationLoginTextField.setOpacity(opacity += 0.0005);
                authorizationPasswordTextField.setOpacity(opacity += 0.0005);
                authorizationRegistrationButton.setOpacity(opacity += 0.0005);
                authorizationButton.setOpacity(opacity += 0.0005);
                authorizationDecorImageView0.setOpacity(opacity += 0.0005);
                authorizationDecorImageView1.setOpacity(opacity += 0.0005);
                authorizationDecorImageView2.setOpacity(opacity += 0.0005);

                threadSleep(10);
            }
        }).start();
    }

    private void hideAuthorizationUIObjects() {
        new Thread(() -> {
            double opacity = 1;
            while (opacity > 0) {
                authorizationTitleLabel.setOpacity(opacity -= 0.0005);
                authorizationHeadLabel.setOpacity(opacity -= 0.0005);
                authorizationLoginTextField.setOpacity(opacity -= 0.0005);
                authorizationPasswordTextField.setOpacity(opacity -= 0.0005);
                authorizationRegistrationButton.setOpacity(opacity -= 0.0005);
                authorizationButton.setOpacity(opacity -= 0.0005);
                authorizationDecorImageView0.setOpacity(opacity -= 0.0005);
                authorizationDecorImageView1.setOpacity(opacity -= 0.0005);
                authorizationDecorImageView2.setOpacity(opacity -= 0.0005);

                threadSleep(10);
            }
            disableAuthorizationUIObjects();
        }).start();
    }

    private void enableAuthorizationUIObjects() {
        authorizationTitleLabel.setVisible(true);
        authorizationHeadLabel.setVisible(true);
        authorizationLoginTextField.setVisible(true);
        authorizationPasswordTextField.setVisible(true);
        authorizationRegistrationButton.setVisible(true);
        authorizationButton.setVisible(true);
        authorizationDecorImageView0.setVisible(true);
        authorizationDecorImageView1.setVisible(true);
        authorizationDecorImageView2.setVisible(true);
    }
    private void disableAuthorizationUIObjects() {
        authorizationTitleLabel.setOpacity(0);
        authorizationHeadLabel.setOpacity(0);
        authorizationLoginTextField.setOpacity(0);
        authorizationPasswordTextField.setOpacity(0);
        authorizationRegistrationButton.setOpacity(0);
        authorizationButton.setOpacity(0);
        authorizationDecorImageView0.setOpacity(0);
        authorizationDecorImageView1.setOpacity(0);
        authorizationDecorImageView2.setOpacity(0);

        authorizationTitleLabel.setVisible(false);
        authorizationHeadLabel.setVisible(false);
        authorizationLoginTextField.setVisible(false);
        authorizationPasswordTextField.setVisible(false);
        authorizationRegistrationButton.setVisible(false);
        authorizationButton.setVisible(false);
        authorizationDecorImageView0.setVisible(false);
        authorizationDecorImageView1.setVisible(false);
        authorizationDecorImageView2.setVisible(false);
    }

    private void showRegistrationUIObjects(){}

    private void threadSleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
