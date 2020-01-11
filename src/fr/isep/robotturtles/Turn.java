package fr.isep.robotturtles;

import java.util.LinkedList;
import java.util.List;

public class Turn {
    private LinkedList<Player> players;
    private Player player;
    private boolean hasPlayed = false;
    private boolean hasWithdrawn = false;

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

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
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
}
