package fr.isep.robotturtles.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MenuController {
    public Text counter = null;

    @FXML
    public void increase(Event e){
        int count = Integer.parseInt(counter.getText()) + 1;
        counter.setText(Integer.toString(count));
    }

    @FXML
    public void decrease(Event e){
        int count = Integer.parseInt(counter.getText()) - 1;
        counter.setText(Integer.toString(count));
    }

    @FXML
    public void quit(Event e){
        System.exit(0);
    }
}
