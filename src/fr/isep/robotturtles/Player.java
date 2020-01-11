package fr.isep.robotturtles;

import fr.isep.robotturtles.constants.*;
import fr.isep.robotturtles.tiles.ObstacleTile;

import java.util.*;

public class Player implements Pawn {
    private Card[] deck = new Card[5];
    private LinkedList<Card> stack;
    private ObstacleTile[] obstacleDeck = new ObstacleTile[5];
    private Boolean hasUsedBug = false;
    private List<Card> instructionsList;
    private PlayerColor color;
    private Orientation orientation;
    // We only keep X coordinate as Y is always 0
    private int startCoordinate;

    public Player(PlayerColor color){
        this.color = color;
        generatePlayer();
    }

    void generatePlayer(){
        stack = new LinkedList<>();
        stack.addAll(Collections.nCopies(18, new Card(CardType.BLUE)));
        stack.addAll(Collections.nCopies(8, new Card(CardType.YELLOW)));
        stack.addAll(Collections.nCopies(8, new Card(CardType.PURPLE)));
        stack.addAll(Collections.nCopies(3, new Card(CardType.LASER)));
        Collections.shuffle(stack);

        Arrays.fill(obstacleDeck, 0, 2, new ObstacleTile(ObstacleType.STONE));
        Arrays.fill(obstacleDeck, 3, 4, new ObstacleTile(ObstacleType.ICE));
    }

    void draw(){
        for(int i = 0; i<deck.length; i++){
            if(deck[i] == null){
               deck[i] = stack.poll();
            }
        }
    }

    public Card[] getDeck() {
        return deck;
    }

    public List<Card> getStack() {
        return stack;
    }

    public ObstacleTile[] getObstacleDeck() {
        return obstacleDeck;
    }

    public void setObstacleDeck(ObstacleTile[] obstacleDeck) {
        this.obstacleDeck = obstacleDeck;
    }

    public Boolean getHasUsedBug() {
        return hasUsedBug;
    }

    public void setHasUsedBug(Boolean hasUsedBug) {
        this.hasUsedBug = hasUsedBug;
    }

    public List<Card> getInstructionsList() {
        return instructionsList;
    }

    public void setInstructionsList(List<Card> instructionsList) {
        this.instructionsList = instructionsList;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setColor(PlayerColor color) {
        this.color = color;
    }

    public void addInstruction(Card[] instructions){
        instructionsList.addAll(Arrays.asList(instructions));
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public int getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(int y) {
        this.startCoordinate =  y;
    }

    public PawnType getPawnType(){
        return PawnType.PLAYER;
    }
}
