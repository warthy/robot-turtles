package fr.isep.robotturtles;

import fr.isep.robotturtles.constants.PlayerColor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Game  extends Application {
    static boolean gameOn = true;
    static int playerNumber = 2;
    static Board board;

    public static void main(String[] args){
        Application.launch(Game.class, args);
        do{
            List<Player> players = new ArrayList<>();
            for(int i=0; i < playerNumber; i++){
                players.add(new Player(PlayerColor.values()[i]));
            }
            board = new Board(players);
        }while(gameOn = true);
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setId("pane");
        Scene scene = new Scene(root, 300, 250);
        scene.getStylesheets().addAll(Game.class.getResource("resources/css/style.css").toExternalForm());

        Button play = new Button("Start");
        play.setLayoutX(0);
        play.setLayoutY(0);
        play.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                play.setText("Cliqué !");
            }
        });

        Button exit = new Button("Quitter");
        exit.setLayoutX(100);
        exit.setLayoutY(100);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                primaryStage.close();
                System.exit(0);
            }
        });

        root.getChildren().add(play);
        root.getChildren().add(exit);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Game.class.getResourceAsStream("resources/images/logo.png")));
        primaryStage.show();
    }

    private boolean isOver(){
        return false;
    }

}
