package com.fnb.game;
import com.fnb.game.common.IPit;
import com.fnb.game.model.Pit;
import com.fnb.game.service.GamePlayLogicService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class GamePlayLogicServiceTests {
    private GamePlayLogicService gamePlayLogicService;

    @Test
    public void testSowStone_ValidInput() {
        List<IPit> board = initializeBoard();
        gamePlayLogicService = new GamePlayLogicService(board);
        gamePlayLogicService.sowStone("PLAYER_1_PIT_1");
        int lastIndex = gamePlayLogicService.getLastIndex();
        assertEquals(6, lastIndex);
    }

    @Test(expected = RuntimeException.class)
    public void testSowStone_InvalidPit() {
        List<IPit> board = initializeBoard();
        gamePlayLogicService = new GamePlayLogicService(board);
        gamePlayLogicService.sowStone("INVALID_PIT");
    }

    private List<IPit> initializeBoard() {
        List<IPit> board = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            board.add(new Pit("PLAYER_1_PIT_"+i));
        }
        return board;
    }
}
