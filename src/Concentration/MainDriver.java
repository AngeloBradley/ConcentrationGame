/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Concentration;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.TRANSPARENT;
import static javafx.scene.paint.Color.YELLOW;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author abrad
 */
public class MainDriver extends Application {

    private Scene scene;
    private Stage primaryStage;
    private GamePane gamePane;

    public static void main(String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        generateSplashScreen();

    }

    private Stage splashStage;
    private Scene splashScene;
    private Image image;
    private ImageView imageView;
    private BorderPane bPane;
    private StackPane sPane;
    private AudioClip audio;
    private Text splashText;
    private Timeline flasher;
    private int i = 0;

    public void generateSplashScreen() {

        audio = new AudioClip("file:_assets/_Audio/intro_audio_0.wav");
        audio.play();

        bPane = new BorderPane();
        sPane = new StackPane();
        splashStage = new Stage();
        splashScene = new Scene(bPane);
        splashText = new Text("");
        image = new Image("file:_assets/_Images/_SplashScreen/splashScreen_0.png");
        imageView = new ImageView(image);

        flasher = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> {
                    splashText.setFill(TRANSPARENT);
                    splashText.setText("");
                }),
                new KeyFrame(Duration.seconds(1), e -> {

                    splashText.setFill(YELLOW);
                    splashText.setText("Click Here!");
                })
        );

        flasher.setCycleCount(Animation.INDEFINITE);
        flasher.playFromStart();

        splashText.setFont(Font.font("font-family:Palatino Linotype’, ‘Book Antiqua’, Palatino, serif;", FontWeight.BOLD, 25));

        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                splashStage.close();
                audio.stop();

                primaryStage = new Stage();
                gamePane = new GamePane();
                scene = new Scene(gamePane);

                scene.setFill(Color.TRANSPARENT);
                primaryStage.setScene(scene);
                primaryStage.initStyle(StageStyle.TRANSPARENT);

                primaryStage.show();

            }
        });

        imageView.setFitHeight(800);
        imageView.setFitWidth(800);
        imageView.setVisible(true);

        sPane.getChildren().addAll(imageView, splashText);

        bPane.setCenter(sPane);

        splashScene.setFill(TRANSPARENT);
        splashStage.initStyle(StageStyle.TRANSPARENT);
        splashStage.setScene(splashScene);
        splashStage.initModality(Modality.APPLICATION_MODAL);
        splashStage.showAndWait();

    }
    
    

}
