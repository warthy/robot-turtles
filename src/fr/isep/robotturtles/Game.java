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
        scene.getStylesheets().addAll(Game.class.getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        Button play = new Button("Jouer");
        play.setLayoutX(50);
        play.setLayoutY(50);
        play.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                play.setText("Cliqué !");
            }
        });
        root.getChildren().add(play);
        Button leave = new Button("Quitter");
        leave.setLayoutX(100);
        leave.setLayoutY(100);
        leave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
        root.getChildren().add(leave);



        primaryStage.setScene(scene);
        primaryStage.show();
    }



    private boolean isOver(){
        return false;
    }

}
