package fr.isep.robotturtles;

import fr.isep.robotturtles.constants.*;
import fr.isep.robotturtles.tiles.ObstacleTile;

import java.util.*;

public class Player implements Pawn {
    private Card[] deck = new Card[5];
    private LinkedList<Card> stack = new LinkedList<>();

    private ObstacleTile[] obstacleDeck = new ObstacleTile[5];
    private Boolean hasUsedBug = false;
    private List<Card> instructionsList = new LinkedList<>();
    private PlayerColor color;
    private Orientation orientation;
    // We only keep X coordinate as Y is always 0
    private int startCoordinate;
    private int[] coordinates = new int[2];

    public Player(PlayerColor color){
        this.color = color;
        orientation = Orientation.UP;
        stack.addAll(Collections.nCopies(18, new Card(CardType.BLUE)));
        stack.addAll(Collections.nCopies(8, new Card(CardType.YELLOW)));
        stack.addAll(Collections.nCopies(8, new Card(CardType.PURPLE)));
        stack.addAll(Collections.nCopies(3, new Card(CardType.LASER)));
        Collections.shuffle(stack);

        Arrays.fill(obstacleDeck, 0, 3, new ObstacleTile(ObstacleType.STONE));
        Arrays.fill(obstacleDeck, 3, 5, new ObstacleTile(ObstacleType.ICE));

        for(int i =0; i<5;i++){
            deck[i] = stack.remove();
        }
    }

    public void draw(){
        for(int i = 0; i<deck.length; i++){
            if(deck[i] == null){
               deck[i] = stack.remove();
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

    public void removeFromObstacleDeck(int index){
        this.obstacleDeck[index] = null;
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
