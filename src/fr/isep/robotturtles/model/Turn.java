package fr.isep.robotturtles.model;

import java.util.LinkedList;
import java.util.List;

public class Turn {
    private LinkedList<Player> players;
    private Player player;
    private boolean hasPlayed = true;
    private boolean hasCompleteProgram = true;
    private boolean hasDiscard = false;

    public Turn(List<Player> players){
        this.players = new LinkedList<>(players);
        next();
    }

    public boolean next(){
        if(hasPlayed){
            player = players.remove();
            players.add(player);

            hasPlayed = false;
            hasDiscard = false;
            hasCompleteProgram = false;
            return true;
        }
        return false;
    }

    public boolean hasPlayed() {
        return hasPlayed;
    }

    public void setHasPlayed() {
        this.hasPlayed = true;
    }

    public boolean hasDiscard() {
        return hasDiscard;
    }

    public void setHasDiscard(boolean hasDiscard) {
        this.hasDiscard = hasDiscard;
    }

    public Player getPlayer(){
        return player;
    }

    public boolean hasCompleteProgram() {
        return hasCompleteProgram;
    }

    public void setHasCompleteProgram() {
        this.hasCompleteProgram = true;
    }
}
