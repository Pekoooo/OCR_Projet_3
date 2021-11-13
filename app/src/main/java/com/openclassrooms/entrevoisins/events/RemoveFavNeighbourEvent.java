package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user removes a Neighbour from the favourite tab
 */

public class RemoveFavNeighbourEvent {

    /**
     * Neighbour to remove
     */

    public Neighbour neighbour;

    /**
     * Constructor.
     * @param neighbour
     */

    public RemoveFavNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}
