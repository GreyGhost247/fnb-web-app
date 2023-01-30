package com.fnb.game;
import com.fnb.game.model.player.GameResult;
import com.fnb.game.model.player.Move;
import com.fnb.game.service.BoardLogicService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardLogicServiceTests {
    private BoardLogicService boardLogicService;

    @Before
    public void setup() {
        boardLogicService = new BoardLogicService();
    }

    @Test
    public void testPlay_validInput_successfulPlay() {
        boardLogicService.play("1");
        assertEquals(6, boardLogicService.getBoard().get(0).getStones());
    }

    @Test
    public void testPlay_invalidInput_throwsException() {
        try {
            boardLogicService.play("13");
        } catch (Exception e) {
            assertEquals("Invalid pit to sow", e.getMessage());
        }
    }

    @Test
    public void testGetBoard_initialState_validBoard() {
        assertEquals(14, boardLogicService.getBoard().size());
        assertEquals(6, boardLogicService.getBoard().get(0).getStones());
    }

    @Test
    public void testGetWinner_initialState_unknown() {
        assertEquals(GameResult.UNKNOWN, boardLogicService.getWinner());
    }

    @Test
    public void testGetLargerPits_initialState_validLargerPits() {
        assertEquals(2, boardLogicService.getLargerPits().size());
        assertEquals(0, boardLogicService.getLargerPits().get(0).getStones());
    }

    @Test
    public void testResetBoard_afterPlay_boardReset() {
        boardLogicService.play("1");
        boardLogicService.resetBoard();
        assertEquals(6, boardLogicService.getBoard().get(0).getStones());
        assertEquals(Move.PlayerTurn.PLAYER_1, Move.getTurn());
    }
}
