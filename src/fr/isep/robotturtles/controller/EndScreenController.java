package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.Main;
import fr.isep.robotturtles.model.Player;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class EndScreenController {

    public GridPane grid = null;

    public static void initEndScreen(Player[] players){

    }

    @FXML
    public void switchToMenu(Event e) throws IOException {
        Scene menu = grid.getScene();
        Window window = menu.getWindow();
        Stage stage = (Stage) window;

        Parent root = FXMLLoader.load(Main.class.getResource("view/Menu.fxml"));
        Scene menuScene = new Scene(root);

        stage.setScene(menuScene);
    }

    @FXML
    public void quit(Event e){
        System.exit(0);
    }

}
