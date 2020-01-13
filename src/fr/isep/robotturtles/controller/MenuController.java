package fr.isep.robotturtles.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;


public class MenuController {
    public Text counter = null;

    @FXML
    public void increase(Event e){
        int count = Integer.parseInt(counter.getText()) + 1;
        if (count < 5){
            counter.setText(Integer.toString(count));
        }
    }

    @FXML
    public void decrease(Event e){
        int count = Integer.parseInt(counter.getText()) - 1;
        if (count > 1){
            counter.setText(Integer.toString(count));
        }
    }

    @FXML
    public void quit(Event e){
        System.exit(0);
    }

    @FXML
    public void startGame(Event e) throws IOException {
        Scene menu = counter.getScene();
        Window window = menu.getWindow();
        Stage stage = (Stage)window;

        GameController.initGame(Integer.parseInt(counter.getText()));
        Parent root = FXMLLoader.load(getClass().getResource("../resources/scenes/Game.fxml"));
        Scene gameScene = new Scene(root);

        stage.setScene(gameScene);
        //stage.setFullScreen(true);
    }
}
