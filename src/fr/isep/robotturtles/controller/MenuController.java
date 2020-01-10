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
}
