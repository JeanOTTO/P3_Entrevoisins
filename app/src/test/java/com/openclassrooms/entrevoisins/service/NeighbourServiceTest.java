package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }
    @Test
    public void addNeighbourWithSuccess() {
        Neighbour neighbourToCreate = service.getNeighbours().get(0);
        service.createNeighbour(neighbourToCreate);
        assertTrue(service.getNeighbours().contains(neighbourToCreate));
    }

    @Test
    public void setNeighbourFavoriteWithSuccess() {
        Neighbour neighbourFavorite = service.getNeighbours().get(0);
        assertFalse(service.getFavoriteNeighbours().contains(neighbourFavorite));
        neighbourFavorite.setFavorite(true);
        assertTrue(service.getFavoriteNeighbours().contains(neighbourFavorite));
    }

    @Test
    public void setFalseNeighbourFavoriteWithSuccess() {
        Neighbour neighbour = service.getNeighbours().get(0);
        neighbour.setFavorite(true);
        assertTrue(service.getFavoriteNeighbours().contains(neighbour));
        neighbour.setFavorite(false);
        assertFalse(service.getFavoriteNeighbours().contains(neighbour));
    }

    @Test
    public void checkNeighbourFavoriteList() {
        // Check that the list of favorite neighbors is initially empty
        assertEquals(0, service.getFavoriteNeighbours().size());

        // Mark the first two neighbors as favorites
        service.getNeighbours().get(0).setFavorite(true);
        service.getNeighbours().get(1).setFavorite(true);

        // Get the list of favorite neighbors
        List<Neighbour> favoriteNeighbours = service.getFavoriteNeighbours();

        // Verify that there are now 2 favorite neighbors
        assertEquals(2, favoriteNeighbours.size());

        // Verify that the references of favorite neighbors in the list match the neighbors marked as favorites
        assertSame(favoriteNeighbours.get(0), service.getNeighbours().get(0));
        assertSame(favoriteNeighbours.get(1), service.getNeighbours().get(1));

        // Verify that the neighbors marked as favorites indeed have the "isFavorite" attribute set to true
        assertTrue(service.getNeighbours().get(0).isFavorite());
        assertTrue(service.getNeighbours().get(1).isFavorite());
    }
}
