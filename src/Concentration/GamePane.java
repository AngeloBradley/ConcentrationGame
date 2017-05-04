/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Concentration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.DIMGREY;
import static javafx.scene.paint.Color.TRANSPARENT;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author abrad
 */
public class GamePane extends BorderPane {

    //FIELDS
    //**External Class Objects
    private CardGridPane cardGridPane;

    //**Class Variables
    private int numClicks = 0;
    private int numMatched = 0;
    private int numTurns = 0;
    private int rows;
    private int cols;
    private Card cardOne;
    private Card cardTwo;
    private BorderPane centerPane;
    private int gameTimer = 0;
    private BackgroundVideo backgroundVideo;
    private StackPane centerBackStack;
    private Label levelsLabel;
    private boolean timerRunning = false;
    private int gameLevel;

    //**Command Pane Fields
    private ComboBox comboBox;
    private ComboBox styleSelection;
    private Button[] buttons;

    //**VBox Test Node
    private VBox vbox;
    private Text numAttempts;

    //**Inner Class Object Declaration
    private MainButtonController mbController = new MainButtonController();
    private Timer timer = new Timer();
    private ArtistInfoPopUp popUp = new ArtistInfoPopUp();

    //**Artist Popup Stage
    private Stage newStage;
    private Card holder;
    private int stopperStarter = 0;
    public AudioClip audio;

    //CONSTRUCTORS
    /**
     *
     */
    public GamePane() {

        centerBackStack = new StackPane();
        Rectangle spaceHolder = new Rectangle();

        centerBackStack.getChildren().add(spaceHolder);
        centerBackStack.setBlendMode(BlendMode.MULTIPLY);

        //Node Creation
        createVBox();
        newGame();

        //**BorderPane for Center Position in GamePane
        centerPane = new BorderPane();
        centerPane.setStyle("-fx-background-color: transparent;");
        centerPane.setMinSize(600, 600);
        centerPane.setVisible(true);
        centerPane.setBlendMode(BlendMode.MULTIPLY);

        centerBackStack.getChildren().add(cardGridPane);
        centerBackStack.setMaxSize(600, 600);
        centerBackStack.setPadding(new Insets(30));

        //Populate Center's BorderPane
        centerPane.setCenter(centerBackStack);
        centerBackStack.setStyle("-fx-background-color: transparent;");

        //Add centerPane to GamePane's center
        this.setCenter(centerPane);

        //Add VBox to the Right side of the GamePane
        this.setRight(vbox);

        //Format GamePane
        this.setMinSize(800, 800);

        this.setVisible(true);

        this.setStyle("-fx-background-color: transparent;");

    }

    //METHODS
    /**
     *
     */
    private void newGame() {
        cardGridPane = new CardGridPane();
    }

    //NODE CREATION
    private void generateMainButtons() {
        //Style Selector DropDown
        ObservableList<String> option = FXCollections.observableArrayList();
        option.addAll("Astronaut");
        styleSelection = new ComboBox(option);
        styleSelection.getSelectionModel().selectFirst();
        styleSelection.setStyle("-fx-background-color: WHITE");

        //Level Selector
        String[] levels = new String[7];
        ObservableList<String> options = FXCollections.observableArrayList();
        for (int i = 0; i < 7; i++) {
            levels[i] = String.valueOf(++i);
            options.add(levels[--i]);
        }
        levelsLabel = new Label("Levels: ");
        comboBox = new ComboBox(options);
        comboBox.setMinWidth(300);
        comboBox.setOnAction(mbController);

        buttons = new Button[2];

        for (int i = 0; i < buttons.length; i++) {
            switch (i) {
                case 0:
                    buttons[i] = new Button("New Game");
                    buttons[i].setDisable(true);
                    break;
                case 1:
                    buttons[i] = new Button("Exit");
                    break;
            }

            buttons[i].setMaxWidth(Double.MAX_VALUE);
            buttons[i].setOnAction(mbController);

        }

    }

    private void makeNeon(Node node) {
        Blend blend = new Blend();
        blend.setMode(BlendMode.MULTIPLY);

        DropShadow ds = new DropShadow();
        ds.setColor(Color.rgb(254, 235, 66, 0.3));
        ds.setOffsetX(5);
        ds.setOffsetY(5);
        ds.setRadius(5);
        ds.setSpread(0.2);

        blend.setBottomInput(ds);

        DropShadow ds1 = new DropShadow();
        ds1.setColor(Color.web("#f13a00"));
        ds1.setRadius(20);
        ds1.setSpread(0.2);

        Blend blend2 = new Blend();
        blend2.setMode(BlendMode.MULTIPLY);

        InnerShadow is = new InnerShadow();
        is.setColor(Color.web("#feeb42"));
        is.setRadius(9);
        is.setChoke(0.8);
        blend2.setBottomInput(is);

        InnerShadow is1 = new InnerShadow();
        is1.setColor(Color.web("#f13a00"));
        is1.setRadius(5);
        is1.setChoke(0.4);
        blend2.setTopInput(is1);

        Blend blend1 = new Blend();
        blend1.setMode(BlendMode.MULTIPLY);
        blend1.setBottomInput(ds1);
        blend1.setTopInput(blend2);

        blend.setTopInput(blend1);

        node.setEffect(blend);
    }

    private void styleText(Text text) {
        text.setFont(Font.font("font-family:Palatino Linotype’, ‘Book Antiqua’, Palatino, serif;", FontWeight.BOLD, 25));
        text.setFill(DIMGREY);
        text.autosize();
    }

    //**VBox Creation
    private void createVBox() {
        generateMainButtons();

        vbox = new VBox();
        BorderPane vBoxBorderPane = new BorderPane();

        numAttempts = new Text("Number Of Attempts");
        styleText(numAttempts);
        makeNeon(numAttempts);

        vBoxBorderPane.setMinSize(300, 400);

        numAttempts.setVisible(true);
        vBoxBorderPane.setVisible(true);
        vbox.setVisible(true);

        vBoxBorderPane.setTop(styleSelection);
        vBoxBorderPane.setCenter(numAttempts);
        vbox.getChildren().add(vBoxBorderPane);

        vbox.setVgrow(numAttempts, Priority.NEVER);
        vbox.setMaxWidth(300);
        vbox.setMaxHeight(400);

        vBoxBorderPane.setStyle("-fx-background-image: url(file:_assets/_Images/_Backgrounds/BackgroundGold.jpg)");
        vbox.getChildren().addAll(levelsLabel, comboBox, buttons[0], buttons[1]);
    }

    private void registerCardListeners() {

        Card[][] cards = cardGridPane.getArrayOfCards();

        for (int i = 0; i < cards.length; i++) {
            for (int x = 0; x < cards[i].length; x++) {
                cards[i][x].setOnMouseClicked(new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        if (!timerRunning) {
                            if (numClicks == 1) {
                                cardGridPane.setDisable(true);
                            }
                            Card c = (Card) event.getSource();
                            c.flipCard();
                            captureFlippedCards(c);
                        }

                    }

                });
            }
        }

    }

    private void captureFlippedCards(Card card) {
        if (numClicks == 0) {
            cardOne = card;
            cardOne.setDisable(true);
            numClicks++;
        } else {
            cardOne.setDisable(false);
            cardTwo = card;
            timer.start();
            numClicks = 0;
            String numAttemptsPrint = "Number Of Attempts: ";
            numAttempts.setText(numAttemptsPrint + ++numTurns);

        }
    }

    //private MP3 mp3 = new MP3();
    private class MainButtonController implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent e) {

            if (e.getSource() instanceof ComboBox) {
                centerBackStack.getChildren().remove(0);
                backgroundVideo = null;

                backgroundVideo = new BackgroundVideo();
                centerBackStack.getChildren().add(0, backgroundVideo.generateVideoBackground());
                centerBackStack.setBlendMode(BlendMode.MULTIPLY);
                centerBackStack.setCache(true);
                centerBackStack.setCacheHint(CacheHint.SPEED);
                ComboBox b = (ComboBox) e.getSource();
                gameLevel = Integer.parseInt(b.getSelectionModel().getSelectedItem().toString());

                numMatched = 0;
                numTurns = 0;
                switch (gameLevel) {
                    case 1:
                        cardGridPane.initCards(2, 3);
                        rows = 2;
                        cols = 3;
                        break;
                    case 2:
                        cardGridPane.initCards(2, 4);
                        rows = 2;
                        cols = 4;
                        break;
                    case 3:
                        cardGridPane.initCards(4, 4);
                        rows = 4;
                        cols = 4;
                        break;
                    case 4:
                        cardGridPane.initCards(4, 6);
                        rows = 4;
                        cols = 6;
                        break;
                    case 5:
                        cardGridPane.initCards(6, 6);
                        rows = 6;
                        cols = 6;
                        break;
                    case 6:

                        cardGridPane.initCards(8, 8);
                        rows = 8;
                        cols = 8;
                        break;
                    case 7:
                        Random var = new Random();
                        boolean checker = true;

                        while (checker) {
                            int r = var.nextInt(9);
                            int c = var.nextInt(9);

                            if ((r * c) % 2 == 0 && r * c != 0) {
                                cardGridPane.initCards(r, c);
                                rows = r;
                                cols = c;
                                checker = false;
                            }
                        }

                        break;
                }
                registerCardListeners();
                buttons[0].setDisable(false);
            } else {

                Button b = (Button) e.getSource();
                String button = b.getText().trim();

                switch (button) {
                    case "New Game":
                        numMatched = 0;
                        cardGridPane.initCards(rows, cols);
                        registerCardListeners();
                        numAttempts.setText("Number Of Attempts");
                        numTurns = 0;

                        break;
                    case "Exit":
                        System.exit(0);
                        break;
                }
            }
        }

    }

    private class Timer extends AnimationTimer {

        @Override
        public void handle(long now) {
            timerRunning = true;
            gameTimer++;
            cardGridPane.setDisable(false);
            if (gameTimer == 15) {
                timer.stop();
                gameTimer = 0;

                if (cardOne.getPath().equals(cardTwo.getPath())) {

                    if (stopperStarter >= 1) {
                        holder.getAudioClip().stop();
                    } else {
                        stopperStarter++;
                    }

                    newStage = new Stage();
                    popUp.start(newStage);

                    cardOne.setMatched();
                    cardTwo.setMatched();
                    cardOne.getAudioClip().play();
                    holder = cardOne;

                    numMatched++;
                    timerRunning = false;
                    if (numMatched == (rows * cols) / 2) {

                        try {
                            File myFile = new File("_assets/_Scores/HighScores.txt");
                            Scanner myData = new Scanner(myFile);
                            int[][] pastScores = new int[7][2];
                            int oldScore = 0;
                            int newScore = numTurns;
                            int r = 0;
                            int c = 0;

                            for (r = 0; r < 7; r++) {
                                for (c = 0; c < 2; c++) {
                                    pastScores[r][c] = Integer.parseInt(myData.nextLine());
                                }
                            }

                            oldScore = pastScores[gameLevel - 1][1];

                            if (newScore < oldScore || oldScore == 0) {
                                Alert newHighScoreAlert = new Alert(AlertType.INFORMATION);
                                newHighScoreAlert.setTitle("New High Score");
                                newHighScoreAlert.setHeaderText(null);
                                newHighScoreAlert.setContentText("!!Congratulations!!\nYour new score of " + newScore + " is the new HIGH SCORE\nOld Score: " + oldScore + "\n\n\nLevel Complete!\nPress New Game or \nSelect New Difficulty Level");
                                newHighScoreAlert.show();
                                pastScores[gameLevel - 1][1] = numTurns;
                            } else {
                                Alert newHighScoreAlert = new Alert(AlertType.INFORMATION);
                                newHighScoreAlert.setTitle("Your Score: ");
                                newHighScoreAlert.setHeaderText(null);
                                newHighScoreAlert.setContentText("Your new Score: " + newScore + "\nOld Score: " + oldScore + "\n\n\nLevel Complete!\nPress New Game or \nSelect New Difficulty Level");
                                newHighScoreAlert.show();
                            }

                            FileWriter scoreSaver = new FileWriter(myFile);

                            for (r = 0; r < 7; r++) {
                                for (c = 0; c < 2; c++) {
                                    scoreSaver.write(String.valueOf(pastScores[r][c]) + System.getProperty("line.separator"));
                                }
                            }

                            scoreSaver.close();
                        } catch (IOException e) {
                            Alert fnfAlert = new Alert(AlertType.INFORMATION);
                            fnfAlert.setTitle("Error!");
                            fnfAlert.setHeaderText(null);
                            fnfAlert.setContentText("File Not Found");
                            fnfAlert.show();
                        }
                    }

                } else {
                    cardOne.flipCard();
                    cardTwo.flipCard();
                    timerRunning = false;
                }
            }

        }

    }

    private class ArtistInfoPopUp extends Application {

        String artistName;
        String albumTitle;
        String songTitle;
        Image image;
        ImageView imageView;
        Scene scene;
        BorderPane bPane;
        Label artistNameLabel;
        TextField artistNameField;
        Label songTitleLabel;
        TextField songTitleField;
        Label albumTitleLabel;
        TextField albumTitleField;
        HBox artistInfoBox;
        Button okButton;

        @Override
        public void start(Stage primaryStage) {
            bPane = new BorderPane();
            scene = new Scene(bPane);

            gatherSongInfo();
            okButtonGenerator();
            HBoxGenerator();

            bPane.setCenter(imageView);
            bPane.setBottom(artistInfoBox);
            bPane.setCache(true);
            bPane.setCacheHint(CacheHint.SPEED);
            bPane.setStyle("-fx-background-color: transparent");
            scene.setFill(TRANSPARENT);
            primaryStage.setScene(scene);
            primaryStage.show();

        }

        private void okButtonGenerator() {
            okButton = new Button("OK");

            okButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event) {

                    newStage.close();
                }

            });
            okButton.setVisible(true);
        }

        private void gatherSongInfo() {
            artistName = cardOne.getArtistName();
            albumTitle = cardOne.getAlbumTitle();
            songTitle = cardOne.getSongTitle();

            //Get the original picture of the artist
            image = new Image(newPathGenerator(cardOne.getPath()));
            imageView = new ImageView(image);
            imageView.setFitHeight(800);
            imageView.setFitWidth(800);

        }

        private void HBoxGenerator() {
            artistInfoBox = new HBox();
            artistNameLabel = new Label("Artist: ");
            songTitleLabel = new Label("Song: ");
            albumTitleLabel = new Label("Album: ");
            artistNameField = new TextField();
            artistNameField.setText(artistName);
            songTitleField = new TextField();
            songTitleField.setText(songTitle);
            albumTitleField = new TextField();
            albumTitleField.setText(albumTitle);

            Label[] labelList = {artistNameLabel, songTitleLabel, albumTitleLabel,};
            TextField[] textFieldList = {artistNameField, songTitleField, albumTitleField};

            for (int i = 0; i < labelList.length; i++) {

                labelList[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                textFieldList[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                textFieldList[i].setDisable(true);
                textFieldList[i].setStyle("-fx-background-color: White");

                artistInfoBox.setHgrow(labelList[i], Priority.ALWAYS);
                artistInfoBox.setHgrow(textFieldList[i], Priority.ALWAYS);

                artistInfoBox.getChildren().addAll(labelList[i], textFieldList[i]);

            }
            artistInfoBox.setHgrow(okButton, Priority.ALWAYS);
            artistInfoBox.getChildren().add(okButton);
            artistInfoBox.setStyle("-fx-background-color: White");
        }

        private String newPathGenerator(String originalPathName) {
            String newPath = "file:_assets/_Images/_OuterSpaceTheme/_Original Image Files/";

            newPath += originalPathName.substring(47);

            return newPath;
        }

    }
}
