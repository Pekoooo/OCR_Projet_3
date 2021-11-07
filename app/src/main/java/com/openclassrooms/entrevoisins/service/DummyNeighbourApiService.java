package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private final List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    private  List<Neighbour> mFavouriteNeighbourList = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }


    @Override
    public List<Neighbour> getFavouriteNeighbourList() {

        if(mFavouriteNeighbourList == null){
            mFavouriteNeighbourList = new ArrayList<>();
        }

        return mFavouriteNeighbourList;
    }

    @Override
    public void addFavNeighbour(Neighbour neighbour) {

        mFavouriteNeighbourList.add(neighbour);

    }

    @Override
    public void removeFavNeighbour(Neighbour neighbour) {

        mFavouriteNeighbourList.remove(neighbour);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }
}
