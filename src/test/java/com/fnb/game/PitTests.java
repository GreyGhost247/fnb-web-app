package com.fnb.game;
import com.fnb.game.model.Pit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
public class PitTests {
    @Test
    public void testAddStone_addStone_getSum() {
        Pit pit = new Pit("1");

        pit.addStone();

        Assertions.assertEquals(7, pit.getStones());
    }

    @Test
    public void testAdd_add_getSum() {
        Pit pit = new Pit("1");

        pit.add(3);

        Assertions.assertEquals(9, pit.getStones());
    }

    @Test
    public void testAddNegative_invalidInput_returnInitialPitStonesCount() {
        Pit pit = new Pit("1");

        pit.add(-3);

        Assertions.assertEquals(6, pit.getStones());
    }

    @Test
    public void testGetId_getId_returnId() {
        Pit pit = new Pit("1");

        String id = pit.getId();

        Assertions.assertEquals("1", id);
    }

    @Test
    public void testIsPitEmpty_checkIfEmpty_getResult() {
        Pit pit = new Pit("1");
        pit.emptyPit();

        boolean result = pit.isPitEmpty();

        Assertions.assertTrue(result);
    }

    @Test
    public void testEmptyPit_checkIfEmpty_getResult() {
        Pit pit = new Pit("1");

        pit.emptyPit();

        Assertions.assertEquals(0, pit.getStones());
    }
}
