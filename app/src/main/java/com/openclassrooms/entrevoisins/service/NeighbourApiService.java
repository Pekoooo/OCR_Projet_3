package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;


/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */

    List<Neighbour> getNeighbours();

    /**
     * Get all the Neighbours from favourite list
     */

    List<Neighbour> getFavouriteNeighbourList();

    /**
     * Puts the current neighbour in the favourite list
     */

    void addFavNeighbour(Neighbour neighbour);

    /**
     * Removes the current neighbour from the favourite list
     */

    void removeFavNeighbour(Neighbour neighbour);

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */
    void createNeighbour(Neighbour neighbour);
}
