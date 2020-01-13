package fr.isep.robotturtles;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args){
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("resources/scenes/Menu.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setOnHidden(e -> System.exit(0));
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("resources/images/logo.png")));
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}
