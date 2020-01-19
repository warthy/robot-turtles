package fr.isep.robotturtles.controller;

import fr.isep.robotturtles.*;
import fr.isep.robotturtles.constants.*;
import fr.isep.robotturtles.model.*;
import fr.isep.robotturtles.model.Obstacle;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static fr.isep.robotturtles.constants.ObstacleType.ICE;

public class GameController implements Initializable {
    static int PLAYER_COUNT = 0;
    static Board board;
    private Turn turn;

    public GridPane grid = null;
    public Button passTurn = null;
    public Text labelTurn = null;
    public GridPane deck = null;
    public GridPane obstacleDeck = null;
    public HBox programStack = null;

    static void initGame(int playerSize) {
        PLAYER_COUNT = playerSize;
        board = new Board(playerSize);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderBoard();
        turn = new Turn(board.getPlayers());
        labelTurn.setText("Tour: tortue " + turn.getPlayer().getColor().name());
        displayDeck();
        displayObstacleDeck();
        displayProgramStack();
    }

    @FXML
    public void nextTurn(Event e) {
        if (turn.next()) {
            labelTurn.setText("Tour: tortue " + turn.getPlayer().getColor().name());
            passTurn.setCursor(Cursor.WAIT);

            turn.getPlayer().draw();
            displayDeck();
            displayObstacleDeck();
            displayProgramStack();
        }
    }

    @FXML
    public void discardCard(DragEvent e) {
        boolean success = false;
        if (turn.hasPlayed()) {
            Dragboard db = e.getDragboard();
            String[] data = db.getString().split(";");
            if (data[0].equals("CARD")) {
                turn.getPlayer().discardCard(Integer.parseInt(data[1]));
                success = true;
            }

            hasPlay(true);
            turn.setHasDiscard(true);
        }
        e.setDropCompleted(success);
        e.consume();
    }

    @FXML
    public void executeProgram(Event e) {
        if (!turn.hasPlayed()) {
            Player player = turn.getPlayer();
            player.getInstructionsList().forEach(card -> {
                Orientation orientation = turn.getPlayer().getOrientation();
                int[] coordinates = turn.getPlayer().getCoordinates();
                switch (card.getType()) {
                    case BLUE:
                        switch (orientation) {
                            case DOWN:
                                if (coordinates[0] + 1 > 7) {
                                    player.returnStartPosition();
                                } else {
                                    Pawn pawn = board.getGridElement(coordinates[0] + 1, coordinates[1]);
                                    if (pawn == null) {
                                        player.setCoordinates(coordinates[0] + 1, coordinates[1]);
                                    } else {
                                        switch (pawn.getPawnType()) {
                                            case PLAYER:
                                                Player p2 = (Player) pawn;
                                                Orientation orientation2 = p2.getOrientation();
                                                player.setOrientation(orientation.getRight().getRight());
                                                p2.setOrientation(orientation2.getRight().getRight());
                                                break;
                                            case OBSTACLE:
                                                player.setOrientation(orientation.getRight().getRight());
                                                break;
                                            case JEWEL:
                                                int jewelMax = board.getJewelmax();
                                                player.setJewelpoint(jewelMax);
                                                board.setJewelmax(jewelMax - 1);
                                                break;
                                        }
                                    }
                                }
                                break;
                            case RIGHT:
                                if (coordinates[1] + 1 > 7) {
                                    player.returnStartPosition();
                                } else {
                                    Pawn pawn = board.getGridElement(coordinates[0], coordinates[1] + 1);
                                    if (pawn == null) {
                                        player.setCoordinates(coordinates[0], coordinates[1] + 1);
                                    } else {
                                        switch (pawn.getPawnType()) {
                                            case PLAYER:
                                                Player p2 = (Player) pawn;
                                                Orientation orientation2 = p2.getOrientation();
                                                player.setOrientation((orientation.getRight().getRight()));
                                                p2.setOrientation(orientation2.getRight().getRight());
                                                break;
                                            case OBSTACLE:
                                                player.setOrientation(orientation.getRight().getRight());
                                                break;
                                            case JEWEL:
                                                int jewelMax = board.getJewelmax();
                                                player.setJewelpoint(jewelMax);
                                                board.setJewelmax(jewelMax - 1);
                                                break;
                                        }
                                    }
                                }
                                break;
                            case LEFT:
                                if (coordinates[1] - 1 < 0) {
                                    player.returnStartPosition();
                                } else {
                                    Pawn pawn = board.getGridElement(coordinates[0], coordinates[1] - 1);
                                    if (pawn == null) {
                                        player.setCoordinates(coordinates[0], coordinates[1] - 1);

                                    } else {
                                        switch (pawn.getPawnType()) {
                                            case PLAYER:
                                                Player p2 = (Player) pawn;
                                                Orientation orientation2 = p2.getOrientation();
                                                player.setOrientation((orientation.getRight().getRight()));
                                                p2.setOrientation(orientation2.getRight().getRight());
                                                break;
                                            case OBSTACLE:
                                                player.setOrientation(orientation.getRight().getRight());
                                                break;
                                            case JEWEL:
                                                int jewelMax = board.getJewelmax();
                                                player.setJewelpoint(jewelMax);
                                                board.setJewelmax(jewelMax - 1);
                                                break;
                                        }
                                    }
                                }
                                break;
                            case UP:
                                if (coordinates[0] - 1 < 0) {
                                    player.returnStartPosition();
                                } else {
                                    Pawn pawn = board.getGridElement(coordinates[0] - 1, coordinates[1]);
                                    if (pawn == null) {
                                        player.setCoordinates(coordinates[0] - 1, coordinates[1]);
                                    } else {
                                        switch (pawn.getPawnType()) {
                                            case PLAYER:
                                                Player p2 = (Player) pawn;
                                                Orientation orientation2 = p2.getOrientation();
                                                player.setOrientation((orientation.getRight().getRight()));
                                                p2.setOrientation(orientation2.getRight().getRight());
                                                break;
                                            case OBSTACLE:
                                                player.setOrientation(orientation.getRight().getRight());
                                                break;
                                            case JEWEL:
                                                int jewelMax = board.getJewelmax();
                                                player.setJewelpoint(jewelMax);
                                                board.setJewelmax(jewelMax - 1);
                                                break;
                                        }
                                    }
                                }
                                break;
                        }
                        break;
                    case YELLOW:
                        player.setOrientation(orientation.getLeft());
                        break;
                    case PURPLE:
                        player.setOrientation((orientation.getRight()));
                        break;
                    case LASER:
                        switch (orientation) {
                            case DOWN:
                                if (!(coordinates[0] + 1 > 7)) {
                                    Pawn pawn = board.getGridElement(coordinates[0] + 1, coordinates[1]);
                                    PawnType pawntype = pawn.getPawnType();
                                    switch (pawntype) {
                                        case OBSTACLE:
                                            if (((Obstacle) pawn).getType() == ICE)
                                                board.set(null, coordinates[0] + 1, coordinates[1]);
                                            break;
                                        case PLAYER:
                                            Player p2 = (Player) pawn;
                                            if (PLAYER_COUNT == 2) {
                                                p2.setOrientation(p2.getOrientation().getRight().getRight());
                                            } else {
                                                p2.returnStartPosition();
                                            }
                                            break;
                                        case JEWEL:
                                            player.returnStartPosition();
                                            break;
                                    }
                                }
                                break;
                            case RIGHT:
                                if (!(coordinates[1] + 1 > 7)) {
                                    Pawn pawn = board.getGridElement(coordinates[0], coordinates[1] + 1);
                                    PawnType pawntype = pawn.getPawnType();
                                    switch (pawntype) {
                                        case OBSTACLE:
                                            if (((Obstacle) pawn).getType() == ICE)
                                                board.set(null, coordinates[0], coordinates[1] + 1);
                                            break;
                                        case PLAYER:
                                            Player p2 = (Player) pawn;
                                            if (PLAYER_COUNT == 2) {
                                                p2.setOrientation(p2.getOrientation().getRight().getRight());
                                            } else {
                                                p2.returnStartPosition();
                                            }
                                            break;
                                        case JEWEL:
                                            player.returnStartPosition();
                                            break;
                                    }
                                }
                                break;
                            case LEFT:
                                if (!(coordinates[1] - 1 < 0)) {
                                    Pawn pawn = board.getGridElement(coordinates[0], coordinates[1] - 1);
                                    PawnType pawntype = pawn.getPawnType();
                                    switch (pawntype) {
                                        case OBSTACLE:
                                            if (((Obstacle) pawn).getType() == ICE)
                                                board.set(null, coordinates[0], coordinates[1] - 1);
                                            break;
                                        case PLAYER:
                                            Player p2 = (Player) pawn;
                                            if (PLAYER_COUNT == 2) {
                                                p2.setOrientation(p2.getOrientation().getRight().getRight());
                                            } else {
                                                p2.returnStartPosition();
                                            }
                                            break;
                                        case JEWEL:
                                            player.returnStartPosition();
                                            break;
                                    }
                                    break;
                                }
                                break;
                            case UP:
                                if (!(coordinates[0] - 1 < 0)) {
                                    Pawn pawn = board.getGridElement(coordinates[0] - 1, coordinates[1]);
                                    PawnType pawntype = pawn.getPawnType();
                                    switch (pawntype) {
                                        case OBSTACLE:
                                            if (((Obstacle) pawn).getType() == ICE)
                                                board.set(null, coordinates[0] - 1, coordinates[1]);
                                            break;
                                        case PLAYER:
                                            Player p2 = (Player) pawn;
                                            if (PLAYER_COUNT == 2) {
                                                p2.setOrientation(p2.getOrientation().getRight().getRight());
                                            } else {
                                                p2.returnStartPosition();
                                            }
                                            break;
                                        case JEWEL:
                                            player.returnStartPosition();
                                            break;
                                    }
                                }
                                break;
                        }
                        break;
                }
            });
            hasPlay(false);
        }
    }

    @FXML
    public void completeProgram(DragEvent e) {
        Dragboard db = e.getDragboard();
        boolean success = false;
        String[] data = db.getString().split(";");
        if ((turn.hasPlayed() && turn.hasCompleteProgram() || (!turn.hasPlayed())) && !turn.hasDiscard() && data[0].equals("CARD")) {
            turn.getPlayer().getDeck()[Integer.parseInt(data[1])] = null;
            turn.getPlayer().getInstructionsList().add(new Card(CardType.valueOf(data[2])));

            displayProgramStack();
            turn.setHasCompleteProgram();
            hasPlay(true);
            success = true;
        }
        e.setDropCompleted(success);
        e.consume();
    }

    @FXML
    public void allowCardDrop(DragEvent e) {
        EventTarget target = e.getTarget();

        Dragboard db = e.getDragboard();
        String[] data = db.getString().split(";");
        //(target.equals(stack) && )
        if (data[0].equals("CARD")) {
            e.acceptTransferModes(TransferMode.MOVE);
        }
        e.consume();
    }

    @FXML
    public void leaveGame(Event e) throws IOException {
        Scene menu = grid.getScene();
        Window window = menu.getWindow();
        Stage stage = (Stage) window;

        Parent root = FXMLLoader.load(Main.class.getResource("view/Menu.fxml"));
        Scene menuScene = new Scene(root);

        stage.setScene(menuScene);
        stage.setFullScreen(true);
    }

    @FXML
    public void quit(Event e) {
        System.exit(0);
    }

    private void hasPlay(boolean usedCard) {
        turn.setHasPlayed();

        passTurn.setCursor(Cursor.HAND);
        if (usedCard) {
            displayDeck();
        }
    }

    private boolean buildWall(int deckIndex, ObstacleType type, int row, int col) {
        Obstacle obstacle = new Obstacle(type);
        if (board.set(obstacle, row, col)) {
            turn.getPlayer().removeFromObstacleDeck(deckIndex);
            displayObstacleDeck();
            placePawn(obstacle, row, col);

            hasPlay(false);
            return true;
        } else {
            return false;
        }
    }

    private void displayDeck() {
        AnchorPane pane;
        int col = 0;
        deck.getChildren().removeIf(node -> !(node instanceof GridPane));
        for (Card card : turn.getPlayer().getDeck()) {
            int finalCol = col;
            pane = new AnchorPane();
            if (card != null) {
                pane.setCursor(Cursor.OPEN_HAND);
                pane.getStyleClass().addAll("card", "card-" + card.getType().name().toLowerCase());
                pane.setOnDragDetected(event -> {
                    AnchorPane source = (AnchorPane) event.getTarget();

                    Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    String data = "CARD;" + finalCol + ";" + card.getType();
                    content.putString(data);
                    db.setContent(content);

                });
            }
            deck.add(pane, col, 0);
            col++;
        }
    }

    private void displayObstacleDeck() {
        int row = 0;
        int col = 0;
        AnchorPane pane;
        obstacleDeck.getChildren().clear();
        for (Obstacle obstacle : turn.getPlayer().getObstacleDeck()) {
            pane = new AnchorPane();
            if (obstacle != null) {
                int finalRow = row;
                int finalCol = col;
                pane.getStyleClass().addAll("pawn", "obstacle-" + obstacle.getType().name().toLowerCase());
                pane.setCursor(Cursor.OPEN_HAND);
                pane.setOnDragDetected(event -> {
                    if (!turn.hasPlayed()) {
                        AnchorPane source = (AnchorPane) event.getTarget();

                        Dragboard db = source.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        String data = "OBSTACLE;" + (2 * finalRow + finalCol) + ";" + obstacle.getType();
                        content.putString(data);
                        db.setContent(content);
                    }
                    event.consume();
                });
            }

            obstacleDeck.add(pane, col, row);

            row = col == 1 ? row + 1 : row;
            col = col == 1 ? 0 : col + 1;
        }
    }

    private void displayProgramStack() {
        programStack.getStyleClass().remove("stack");
        if (turn.getPlayer().getInstructionsList().size() > 0) {
            programStack.getStyleClass().add("stack");
        }
    }

    private void placePawn(Pawn pawn, int row, int col) {
        AnchorPane pane = new AnchorPane();
        pane.getStyleClass().add("pawn");
        pane.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            String[] data = db.getString().split(";");
            AnchorPane target = (AnchorPane) event.getTarget();

            if (board.getGridElement(GridPane.getRowIndex(target), GridPane.getColumnIndex(target)) == null && data[0].equals("OBSTACLE")) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        pane.setOnDragEntered(event -> {
            Dragboard db = event.getDragboard();
            String[] data = db.getString().split(";");

            AnchorPane target = (AnchorPane) event.getTarget();
            if (data[0].equals("OBSTACLE")) {
                if (board.getGridElement(GridPane.getRowIndex(target), GridPane.getColumnIndex(target)) == null) {
                    target.getStyleClass().add("hover-allowed");
                } else {
                    target.getStyleClass().add("hover-refused");
                }
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });
        pane.setOnDragExited(event -> {
            ((AnchorPane) event.getTarget()).getStyleClass().remove("hover-allowed");
            ((AnchorPane) event.getTarget()).getStyleClass().remove("hover-refused");
            event.acceptTransferModes(TransferMode.MOVE);
        });
        if (pawn == null) {
            pane.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    String[] data = db.getString().split(";");

                    if (data[0].equals("OBSTACLE")) {
                        AnchorPane target = (AnchorPane) event.getTarget();
                        target.getStyleClass().add(db.getString());

                        success = buildWall(
                                Integer.parseInt(data[1]),
                                ObstacleType.valueOf(data[2]),
                                GridPane.getRowIndex(target),
                                GridPane.getColumnIndex(target)
                        );
                    }

                }
                event.setDropCompleted(success);
                event.consume();
            });

        } else {
            switch (pawn.getPawnType()) {
                case PLAYER:
                    pane.setId("turtle-" + ((Player) pawn).getColor().name().toLowerCase());
                    break;
                case OBSTACLE:
                    pane.getStyleClass().add("obstacle-" + ((Obstacle) pawn).getType().name().toLowerCase());
                    break;
                case JEWEL:
                    pane.getStyleClass().add("jewel");
                    break;
            }
        }
        grid.add(pane, col, row);
    }

    private void renderBoard() {
        //Clear grid by removing all children
        grid.getChildren().clear();
        int row = 0;
        for (Pawn[] rows : board.getGrid()) {
            int col = 0;
            for (Pawn pawn : rows) {
                placePawn(pawn, row, col);
                col++;
            }
            row++;
        }
    }
}
