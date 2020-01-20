package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.Main;
import fr.isep.robotturtles.model.Player;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class EndScreenController implements Initializable {
    static Player[] ranking;


    public GridPane endGrid = null;
    public VBox scoreboard = null;

    public static void initEndScreen(Player[] players){
        Arrays.sort(players);
        ranking = players;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int count = 1;
        for(Player p: ranking){
            Text scoreline = new Text(count+"-"+p.getColor().name()+"\t\t\t\t\t\t"+ p.getJewelPoint()+"pts");
            scoreline.getStyleClass().add("scoreline");
            scoreboard.getChildren().add(scoreline);
            count++;
        }
    }

    @FXML
    public void switchToMenu(Event e) throws IOException {
        Scene menu = endGrid.getScene();
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
