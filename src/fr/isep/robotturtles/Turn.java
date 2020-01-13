package fr.isep.robotturtles;

import java.util.LinkedList;
import java.util.List;

public class Turn {
    private LinkedList<Player> players;
    private Player player;
    private boolean hasPlayed = true;
    private boolean hasWithdrawn = false;
    private boolean hasDraw = false;

    public Turn(List<Player> players){
        this.players = new LinkedList<>(players);
        next();
    }

    public boolean next(){
        if(hasPlayed){
            player = players.remove();
            players.add(player);

            hasPlayed = false;
            hasWithdrawn = false;
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

    public boolean hasWithdrawn() {
        return hasWithdrawn;
    }

    public void setHasWithdrawn(boolean hasWithdrawn) {
        this.hasWithdrawn = hasWithdrawn;
    }

    public Player getPlayer(){
        return player;
    }

    public boolean hasDraw() {
        return hasDraw;
    }

    public void setHasDraw() {
        player.draw();
        this.hasDraw = true;
    }
}
