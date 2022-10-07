package net.skaia.pasteger.sump.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import net.skaia.pasteger.sump.service.AuthorizationService;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class AuthorizationController extends Controller{
    AuthorizationService service = AuthorizationService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label headLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button registrationButton;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView start;

    String musicFile = "src\\main\\resources\\net\\skaia\\pasteger\\sump\\sound\\skaianet.mp3";
    Media sound = new Media(new File(musicFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);



    @FXML void initialize() {
        titleLabel.setVisible(false);
        headLabel.setVisible(false);
        loginTextField.setVisible(false);
        passwordTextField.setVisible(false);
        registrationButton.setVisible(false);

        stage.setHeight(155);
        stage.setWidth(155);


        start.setOnMouseClicked(mouseEvent -> {
            //mediaPlayer.play();
            start.setDisable(true);

            new Thread(() -> {
                boolean achievedWidth = false;
                boolean achievedHeight = false;
                int maxWidth = 640;
                int maxHeight = 480;
                int height;
                int width;

                boolean achievedImageX = false;
                boolean achievedImageY = false;
                int maxImageX = 261;
                int maxImageY = 181;
                int imageX;
                int imageY;

                int stageX;
                int stageY;

                float step;


                for (int time = 1000; time < 10000; time++){
                    step = (float) time / 1000;

                    start.setRotate(start.getRotate() + step);

                    try {
                        Thread.sleep(0, 1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    if (time > 2000){

                        width = (int) stage.getWidth();
                        height = (int) stage.getHeight();

                        stageX = (int) stage.getX();
                        stageY = (int) stage.getY();


                        imageX = (int) start.getX();
                        imageY = (int) start.getY();

                        if (time % 5 == 0) {
                            width++;
                            height++;
                            if (width % 2 == 0){
                                stageX--;
                                imageX++;
                            }
                            if (height % 2 == 0){
                                stageY--;
                                imageY++;
                            }
                        }

                        if (width > maxWidth && !achievedWidth){
                            System.out.println("achievedWidth");
                            achievedWidth = true;
                            stage.setWidth(maxWidth);
                        }
                        if (height > maxHeight && !achievedHeight){
                            System.out.println("achievedHeight");
                            achievedHeight = true;
                            stage.setHeight(maxHeight);
                        }
                        if (imageX > maxImageX){
                            imageX = maxImageX;
                        }
                        if (imageY > maxImageY){
                            imageY = maxImageY;
                        }

                        if (!achievedHeight) {
                            stage.setHeight(height);
                            stage.setY(stageY);
                        }
                        if (!achievedWidth) {
                            stage.setWidth(width);
                            stage.setX(stageX);
                        }


                        if (!achievedImageX) {
                            start.setX(imageX);
                        }
                        if (!achievedImageY) {
                            start.setY(imageY);
                        }
                    }
                }
                System.out.println(start.getOpacity());
                while (start.getOpacity() > 0){
                    System.out.println(start.getOpacity());
                    start.setOpacity(start.getOpacity() - 0.01);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }


            }).start();
        });
    }
}
