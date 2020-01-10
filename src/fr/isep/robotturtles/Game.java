package fr.isep.robotturtles;

import fr.isep.robotturtles.constants.PlayerColor;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends Application {
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
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().addAll(Game.class.getResource("resources/css/style.css").toExternalForm());

        primaryStage.setOnHidden(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Game.class.getResourceAsStream("resources/images/logo.png")));
        primaryStage.show();
    }

    private boolean isOver(){
        return false;
    }

}
