package fr.isep.robotturtles;

import fr.isep.robotturtles.constants.CardType;

public class Card {

    CardType type;

    public Card(CardType type){
        this.type = type;
    }

    public CardType getType(){
        return type;
    }

}
