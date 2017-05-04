/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Concentration;

import java.io.File;
import javafx.scene.CacheHint;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;

/**
 *
 * @author abrad
 */
public class BackgroundVideo {

    private Media media;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private File sourceMaterial;
    private static int i = 0;

    public WebView generateWebView() {
        WebView webView = new WebView();
        webView.getEngine().load(
                "https://www.youtube.com/embed/z783n71_ONY?rel=0&amp;controls=1&amp;showinfo=0?ecver=1"
        );

        return webView;
    }

    public MediaView generateVideoBackground() {
        
        sourceMaterial = new File("_assets/_Video/OuterSpaceTheme/MoonLanding.mp4");
        
        
//        switch(i){
//            case 0:
//                sourceMaterial = new File("_assets/_Video/OuterSpaceTheme/AstronautCam.mp4");
//                i++;
//                break;
//            case 1:
//                sourceMaterial = new File("_assets/_Video/OuterSpaceTheme/MoonLanding.mp4");
//                i++;
//                break;
//            case 2:
//                sourceMaterial = new File("_assets/_Video/OuterSpaceTheme/NatureScenery.mp4");
//                i++;
//                break;
//            case 3:
//                sourceMaterial = new File("_assets/_Video/OuterSpaceTheme/SpaceWalk.mp4");
//                i = 0;
//                break;
//        }
        
        String path = sourceMaterial.toURI().toString();

        media = new Media(path);

        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        mediaView = new MediaView(mediaPlayer);
        mediaView.setStyle("-fx-background-color:rgba(0,0,0,0);");

        mediaView.setCache(true);
        mediaView.setCacheHint(CacheHint.SPEED);

        return mediaView;
    }
}
