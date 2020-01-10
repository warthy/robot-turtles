package fr.isep.robotturtles;

import fr.isep.robotturtles.constants.PlayerColor;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        primaryStage.setTitle("Hello World");
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250, Color.LIGHTGREEN);
        Image bg = new Image("robot-turtles-menu.png");
        ImageView iv1 = new ImageView();
        iv1.setImage(bg);
        iv1.setFitWidth(100);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);
        root.getChildren().add(iv1);

        Button play = new Button("Jouer");
        play.setLayoutX(0);
        play.setLayoutY(0);
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
