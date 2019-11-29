import tiles.ObstacleTile;

import javax.smartcardio.Card;
import java.util.List;

public class Player extends Pawn {
    private Card[] deck = new Card[5];
    private List<Card> pile;
    private ObstacleTile[] obstacleDeck = new ObstacleTile[5];
    private Boolean hasUsedBug = false;
    private List<Card> instructionsList;

    Player(){

    }

    void generatePile(){

    }
}
