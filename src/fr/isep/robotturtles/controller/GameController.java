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
import javafx.scene.Node;
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
                                movePlayer(player, coordinates[0] + 1, coordinates[1]);
                                break;
                            case RIGHT:
                                movePlayer(player, coordinates[0], coordinates[1] + 1);
                                break;
                            case LEFT:
                                movePlayer(player, coordinates[0], coordinates[1] - 1);
                                break;
                            case UP:
                                movePlayer(player, coordinates[0] - 1, coordinates[1]);
                                break;
                        }
                        break;
                    case YELLOW:
                        player.setOrientation(orientation.getLeft());
                        placePawn(player, coordinates[0], coordinates[1]);
                        break;
                    case PURPLE:
                        player.setOrientation((orientation.getRight()));
                        placePawn(player, coordinates[0], coordinates[1]);
                        break;
                    case LASER:
                        switch (orientation) {
                            case DOWN:
                                if (!(coordinates[0] + 1 > 7))
                                    useLaser(player, coordinates[0], coordinates[1] + 1);
                                break;
                            case RIGHT:
                                if (!(coordinates[1] + 1 > 0))
                                    useLaser(player, coordinates[0], coordinates[1] + 1);
                                break;
                            case LEFT:
                                if (!(coordinates[1] - 1 < 0))
                                    useLaser(player, coordinates[0], coordinates[1] - 1);
                                break;
                            case UP:
                                if (!(coordinates[0] - 1 < 0))
                                    useLaser(player, coordinates[0] - 1, coordinates[1]);
                                break;
                        }
                        break;
                }
            });

            player.emptyInstructions();
            displayProgramStack();
            hasPlay(false);
            turn.next();
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
        //stage.setFullScreen(true);
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

    private void useLaser(Player player, int row, int col) {
        Pawn pawn = board.getGridElement(row, col);
        PawnType pawntype = pawn.getPawnType();
        int[] coord;
        switch (pawntype) {
            case OBSTACLE:
                if (((Obstacle) pawn).getType() == ICE)
                    placePawn(null, row, col);
                break;
            case PLAYER:
                Player p2 = (Player) pawn;
                coord = p2.getCoordinates();
                if (PLAYER_COUNT == 2) {
                    p2.setOrientation(p2.getOrientation().getRight().getRight());
                    //TODO va poser probleme
                    placePawn(p2, coord[0], coord[1]);
                } else {
                    p2.returnStartPosition();
                    placePawn(null, coord[0], coord[1]);
                    placePawn(p2, Player.PLAYER_START_ROW, p2.getStartCoordinate());
                }
                break;
            case JEWEL:
                coord = player.getCoordinates();
                player.returnStartPosition();
                placePawn(null, coord[0], coord[1]);
                placePawn(player, Player.PLAYER_START_ROW, player.getStartCoordinate());
                break;
        }
    }

    private void movePlayer(Player player, int row, int col) {
        int[] coord = player.getCoordinates();
        try {
            Pawn pawn = board.getGridElement(row, col);
            if (pawn == null) {
                placePawn(null, coord[0], coord[1]);
                placePawn(player, row, col);
                player.setCoordinates(row, col);
            } else {
                switch (pawn.getPawnType()) {
                    case PLAYER:
                        Player facingPlayer = (Player) pawn;
                        //TODO va poser probleme
                        player.setOrientation(player.getOrientation().getRight().getRight());
                        facingPlayer.setOrientation(facingPlayer.getOrientation().getRight().getRight());
                        break;
                    case OBSTACLE:
                        //TODO va poser probleme
                        player.setOrientation(player.getOrientation().getRight().getRight());
                        break;
                    case JEWEL:
                        board.removePawn(coord[0], coord[1]);
                        placePawn(null, coord[0], coord[1]);
                        player.setJewelpoint(board.getJewelMax());
                        //TODO decrease JewelMax
                        break;
                }

            }
        } catch (IndexOutOfBoundsException e) {
            player.returnStartPosition();
            placePawn(null, coord[0], coord[1]);
            placePawn(player, Player.PLAYER_START_ROW, player.getStartCoordinate());

        }
    }

    private boolean buildWall(int deckIndex, ObstacleType type, int row, int col) {
        Obstacle obstacle = new Obstacle(type);
        if (board.setPawn(obstacle, row, col)) {
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
            board.removePawn(row, col);
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
        } else if (pawn == board.getGridElement(row, col) || board.setPawn(pawn, row, col)) {
            switch (pawn.getPawnType()) {
                case PLAYER:
                    Player p = (Player) pawn;
                    pane.setRotate(p.getOrientation().getAngle());
                    pane.setId("turtle-" + p.getColor().name().toLowerCase());
                    break;
                case OBSTACLE:
                    pane.getStyleClass().add("obstacle-" + ((Obstacle) pawn).getType().name().toLowerCase());
                    break;
                case JEWEL:
                    pane.getStyleClass().add("jewel");
                    break;
            }
        }
        for(Node node : grid.getChildren()) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                grid.getChildren().remove(node);
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
