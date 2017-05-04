/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Concentration;

import java.util.Random;
import javafx.scene.CacheHint;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioClip;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.BURLYWOOD;
import static javafx.scene.paint.Color.DIMGREY;
import static javafx.scene.paint.Color.GREY;
import static javafx.scene.paint.Color.TRANSPARENT;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author abrad
 */
public class Card extends StackPane {

    //FIELDS
    private boolean flipped = false; //true if face is showing, false if back is showing (face hidden)
    private String path;
    private Image image;
    private ImageView imageView;
    private Rectangle backOfCard;
    private ImageView unusedCard;
    private Image unusedImage;
    private DropShadow ds;

    //**Audio
    private AudioClip audio;
    private String artist;
    private String album;
    private String songTitle;
    private String youtubePlayList;

    //CONSTRUCTORS
    public Card() {

        //Add Rectangle Object for Unused Card
        Random var = new Random();
        int k = var.nextInt(3);

        unusedImage = new Image("file:_assets/_Images/_Backgrounds/BackgroundStars.png");
        unusedCard = new ImageView(unusedImage);
        unusedCard.setFitHeight(92);
        unusedCard.setFitWidth(92);
        unusedCard.setVisible(true);
        unusedCard.setCache(true);
        unusedCard.setCacheHint(CacheHint.SPEED);

        switch (k) {
            case 0:
                this.setStyle("-fx-border-color: grey;");
                break;
            case 1:
                this.setStyle("-fx-border-color: black;");
                break;
            case 2:
                this.setStyle("-fx-border-color: white;");
                break;
        }

        this.setMinHeight(92);
        this.setMinWidth(92);
        this.getChildren().add(unusedCard);

        //Set Visibility
        this.setVisible(true);
        ds = new DropShadow();
        ds.setOffsetY(20);
        ds.setOffsetX(20);
        ds.setColor(BURLYWOOD);
        this.setEffect(ds);

        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
    }

    public Card(String imagePath, String songName, String[] artistInfo) {

        //Create Back of Card
        backOfCard = new Rectangle();
        backOfCard.setHeight(92);
        backOfCard.setWidth(92);

        backOfCard.setVisible(true);

        //Load Image (Front of Card)
        setPath(imagePath);
        setSong(songName);
        setArtistInfo(artistInfo);
        setCardAndImageSize(92, 92);

        //Add nodes to StackPane
        this.getChildren().add(backOfCard);

        //Set Visibility
        this.setVisible(true);

        Random var = new Random();
        int k = var.nextInt(3);
        switch (k) {
            case 0:
                this.setStyle("-fx-border-color: grey;");
                break;
            case 1:
                this.setStyle("-fx-border-color: black;");
                break;
            case 2:
                this.setStyle("-fx-border-color: white;");
                break;
        }
        k = var.nextInt(3);
        switch (k) {
            case 0:
                backOfCard.setFill(GREY);
                break;
            case 1:
                backOfCard.setFill(BLACK);
                break;
            case 2:
                backOfCard.setFill(DIMGREY);
                break;
        }

    }

    //METHODS
    //**Audio
    public AudioClip getAudioClip() {
        return audio;
    }

    public void setAudioClip(String songPath) {
        audio = new AudioClip(songPath);

    }

    public String getArtistName() {
        return artist;
    }

    public void setArtistName(String artistName) {
        artist = artistName;
    }

    public String getAlbumTitle() {
        return album;
    }

    public void setAlbumTitle(String albumName) {
        album = albumName;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String title) {
        songTitle = title;
    }

    public String getYoutubePlayList() {
        return youtubePlayList;
    }

    public void setYoutubePlayList(String webAddress) {
        youtubePlayList = webAddress;
    }

    public void setArtistInfo(String[] info) {
        artist = info[0];
        songTitle = info[1];
        album = info[2];
        //youtubePlayList = info[3];

    }

    //**Card
    public void flipCard() {
        if (flipped == false) {
            this.getChildren().add(imageView);
            flipped = true;
        } else {
            this.getChildren().clear();
            this.getChildren().add(backOfCard);
            flipped = false;
        }

    }

    public void setCardAndImageSize(double width, double height) {
        this.minHeight(height);
        this.minWidth(width);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        backOfCard.setHeight(height);
        backOfCard.setWidth(width);
    }

    public void changeBackOfCardColor(javafx.scene.paint.Color backgroundColor) {
        backOfCard.setFill(backgroundColor);
    }

    public void setPath(String path) {
        this.path = path;
        image = new Image(path);
        imageView = new ImageView(image);
        imageView.setVisible(true);
    }

    public void setSong(String songPath) {
        audio = new AudioClip(songPath);
    }

    public String getPath() {
        return path;
    }

    public void setMatched() {
        this.getChildren().clear();

        Rectangle spaceHolder = new Rectangle();
        spaceHolder.setWidth(92);
        spaceHolder.setHeight(92);
        spaceHolder.setFill(TRANSPARENT);

        this.getChildren().add(spaceHolder);
        this.setDisable(true);

    }

    public DropShadow getCardDropShadow() {
        return ds;
    }

}
