package Constants;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

import static Constants.Paths.MEDIA;

public abstract class MediaPlayers {

    //Constant Media Players

    public static double MediaVolume = 0.5;
    public static double EffectVolume = 0.5;
    public static final Media LOADING = new Media(new File(MEDIA +"LOADING.mp4").toURI().toString());
    public static final MediaPlayer LOADING_PLAYER = new MediaPlayer(LOADING);

    public static final Media MENU = new Media(new File(MEDIA +"MENU.mp4").toURI().toString());
    public static final MediaPlayer MENU_PLAYER = new MediaPlayer(MENU);

    public static final Media OPTIONS = new Media(new File(MEDIA +"OPTION.mp4").toURI().toString());
    public static final MediaPlayer OPTIONS_PLAYER = new MediaPlayer(OPTIONS);

    public static final Media OPTIONS_FRONT = new Media(new File(MEDIA +"OPTION_FRONT.mp4").toURI().toString());
    public static final MediaPlayer OPTIONS_FRONT_PLAYER = new MediaPlayer(OPTIONS_FRONT);

    public static final Media MAIN_STORY = new Media(new File(MEDIA + "LOADING.mp4").toURI().toString());
    public static final MediaPlayer MAIN_STORY_PLAYER = new MediaPlayer(MAIN_STORY);

    public static final Media CHOOSE_MOD = new Media(new File(MEDIA + "CHOOSE_MOD.mp4").toURI().toString());
    public static final MediaPlayer CHOOSE_MOD_PLAYER = new MediaPlayer(CHOOSE_MOD);

    public static final Media TIC = new Media(new File(MEDIA + "TICTACTOEDISPLAY.mp4").toURI().toString());
    public static final MediaPlayer TIC_PLAYER = new MediaPlayer(TIC);

    public static final Media BATTLE = new Media(new File(MEDIA + "2.mp4").toURI().toString());
    public static final MediaPlayer BATTLE_PLAYER = new MediaPlayer(BATTLE);

    public static final Media WIN = new Media(new File(MEDIA + "WIN.mp4").toURI().toString());
    public static final MediaPlayer WIN_PLAYER = new MediaPlayer(WIN);

    public static final Media LOSE = new Media(new File(MEDIA + "LOSE.mp4").toURI().toString());
    public static final MediaPlayer LOSE_PLAYER = new MediaPlayer(LOSE);

    public static final Media DRAW = new Media(new File(MEDIA + "DRAW.mp4").toURI().toString());
    public static final MediaPlayer DRAW_PLAYER = new MediaPlayer(DRAW);


    public static final Media MENU_MUSIC = new Media(new File(MEDIA +"menuMusic.mp3").toURI().toString());
    public static final MediaPlayer MENU_MEDIA_PLAYER = new MediaPlayer(MENU_MUSIC);

    public static final Media GAME_MUSIC = new Media(new File(MEDIA +"gameMusic.m4a").toURI().toString());
    public static final MediaPlayer GAME_MEDIA_PLAYER = new MediaPlayer(GAME_MUSIC);

    public static final AudioClip BUTTON_EFFECT = new AudioClip("file:src//Resources/Media/click.wav");
    public static final AudioClip TYPE_EFFECT = new AudioClip("file:src//Resources/Media/type.wav");
    public static final AudioClip ALARM_EFFECT = new AudioClip("file:src//Resources/Media/type.wav");
    public static final AudioClip BROKE_GLASS_EFFECT = new AudioClip("file:src//Resources/Media/glass.wav");
    public static final AudioClip ARRANGE_EFFECT = new AudioClip("file:src//Resources/Media/ArrangePiece.wav");
    public static final AudioClip WIN_EFFECT = new AudioClip("file:src//Resources/Media/win.wav");
    public static final AudioClip LOSE_EFFECT = new AudioClip("file:src//Resources/Media/lose.wav");
    public static final AudioClip DRAW_EFFECT = new AudioClip("file:src//Resources/Media/draw.wav");


    public static void PlayMedia(MediaPlayer mediaPlayer){
        mediaPlayer.setCycleCount(1000);
        mediaPlayer.setAutoPlay(true);
    }

    public static double volIN = 0;
    public static double volOUT = 0;
    public static void PlayMainMU(){
        MENU_MEDIA_PLAYER.play();
        MENU_MEDIA_PLAYER.setVolume(MediaVolume);
        MENU_MEDIA_PLAYER.setCycleCount(1000);
    }

    public static void PlayGameMU(){
        GAME_MEDIA_PLAYER.play();
        GAME_MEDIA_PLAYER.setVolume(MediaVolume);
        GAME_MEDIA_PLAYER.setCycleCount(1000);
    }

    public static void FadeIn(MediaPlayer in, MediaPlayer out){
        volIN = 0;
        volOUT = MediaVolume;
        in.setVolume(volIN);
        in.play();
        in.setCycleCount(1000);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(500), ae ->{
            out.setVolume(volOUT);
            volOUT -= 0.1;
            if (volOUT<0)volOUT = 0;

            in.setVolume(volIN);
            volIN += 0.1;
            if (volIN> MediaVolume)volIN = MediaVolume;
        }));
        timeline.setOnFinished(event -> out.pause());
        timeline.setCycleCount(10);
        timeline.play();
    }

    public static void PlayEffect(AudioClip audio){
        audio.setVolume(EffectVolume);
        audio.play();
    }

    public static void setSFX(){
        BUTTON_EFFECT.setVolume(EffectVolume);
        TYPE_EFFECT.setVolume(EffectVolume);
        ALARM_EFFECT.setVolume(EffectVolume);
        BROKE_GLASS_EFFECT.setVolume(EffectVolume);
        ARRANGE_EFFECT.setVolume(EffectVolume);
    }
}
