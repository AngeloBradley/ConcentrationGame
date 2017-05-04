/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Concentration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.CacheHint;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javax.swing.JOptionPane;

/**
 *
 * @author abrad
 */
public class CardGridPane extends GridPane {

    //FIELDS
    //**External Class Objects and Containers
    private Card[][] cards;
    private Card card;

    //**Class Variables
    private ArrayList<String> cardList;
    private ArrayList<String> trackList;
    private ArrayList<String[]> artistInfo;
    private static final int MAXROWS = 8, MAXCOLS = 8;
    private int currentRows = MAXROWS;
    private int currentCols = MAXCOLS;

    private StackPane sPane;

    //CONSTRUCTORS
    public CardGridPane() {
        sPane = new StackPane();
        sPane.setStyle(
                "-fx-effect: dropshadow(gaussian, Orange, " + 50 + ", 0, 0, 0);"
                + "-fx-background-insets: " + 50 + ";"
        );
        sPane.setVisible(true);
        setGridPane();
        sPane.getChildren().add(this);
        this.setDisable(true);

    }

    public CardGridPane(int size) {
        sPane = new StackPane();
        sPane.setStyle(
                "-fx-effect: dropshadow(gaussian, Blue, " + 50 + ", 0, 0, 0);"
                + "-fx-background-insets: " + 50 + ";"
        );
        setGridPane();
        sPane.getChildren().add(this);

    }

    //METHODS
    private void setGridPane() {
        createCardImageList((currentRows * currentCols) / 2);
        setCardImages();
        this.setVisible(true);
        this.setMinSize(750, 750);

    }

    private void setCardImages() {
        shuffleImages();
        cards = new Card[MAXROWS][MAXCOLS];
        int counter = 0;

        for (int i = 0; i < MAXROWS; i++) {
            for (int x = 0; x < MAXCOLS; x++) {
                if (i < currentRows && x < currentCols) {

                    card = new Card(cardList.get(counter), trackList.get(counter), artistInfo.get(counter));
                    card.setDisable(false);
                    counter++;

                } else {
                    card = new Card();
                    card.setDisable(true);
                }
                cards[x][i] = card;
                this.add(cards[x][i], x, i);
            }

        }

    }

    private void shuffleImages() {
        long seed = System.nanoTime();
        Collections.shuffle(cardList, new Random(seed));
        Collections.shuffle(trackList, new Random(seed));
        Collections.shuffle(artistInfo, new Random(seed));
    }

    private void createCardImageList(int size) { //size should be (col * row)/2
        cardList = new ArrayList<String>();
        trackList = new ArrayList<String>();
        artistInfo = new ArrayList<String[]>();

        int counter = 0;
        int i = 0;
        try {
            File myFile = new File("_assets/_Audio/OuterSpaceAudio/trackInfo.txt");
            Scanner myData = new Scanner(myFile);
            for (i = 0; i < size; i++) {
                cardList.add("file:_assets/_Images/_OuterSpaceTheme/_100x100/image_" + counter + ".png");
                cardList.add("file:_assets/_Images/_OuterSpaceTheme/_100x100/image_" + counter + ".png");
                trackList.add("file:_assets/_Audio/OuterSpaceAudio/audio_" + counter + ".mp3");
                trackList.add("file:_assets/_Audio/OuterSpaceAudio/audio_" + counter + ".mp3");
                String[] info = new String[3];

                for (int p = 0; p < 3; p++) {
                    info[p] = myData.nextLine().trim();
                }

                artistInfo.add(info);
                artistInfo.add(info);
                counter++;
            }
            myData.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Not Found");
        }

    }

    public void initCards(int rows, int columns) {
        this.getChildren().clear();
        currentRows = rows;
        currentCols = columns;
        createCardImageList((currentRows * currentCols) / 2);
        setCardImages();

        this.setDisable(false);
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);

    }

    //**Getters and Setters
    public void setRows(int num) {
        currentRows = num;
    }

    public int getRows() {
        return currentRows;
    }

    public void setColumns(int num) {
        currentCols = num;
    }

    public int getColumns() {
        return currentCols;
    }

    public Card getCard(int row, int column) {
        return cards[row][column];
    }

    public Card[][] getArrayOfCards() {
        return cards;
    }

    public StackPane getSPane() {
        return sPane;
    }

}
