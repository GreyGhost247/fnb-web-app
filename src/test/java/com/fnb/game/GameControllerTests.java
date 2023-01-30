package com.fnb.game;
import com.fnb.game.common.IPit;
import com.fnb.game.constants.Constant;
import com.fnb.game.controller.GameController;
import com.fnb.game.model.LargerPit;
import com.fnb.game.model.Pit;
import com.fnb.game.model.player.GameResult;
import com.fnb.game.service.IBoardLogicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class GameControllerTests {
    @Mock
    private IBoardLogicService board;
    @InjectMocks
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSow_validInput_successfullySown() {
        List<IPit> boardState = getBoardState();
        when(board.getBoard()).thenReturn(boardState);

        List<IPit> result = gameController.sow("1");

        verify(board).play("1");
        verify(board).getBoard();
        Assertions.assertEquals(boardState, result);
    }
    @Test
    public void testResetGame_reset_boardReset() {
        gameController.resetGame();

        verify(board).resetBoard();
    }

    @Test
    public void testGetWinner_determineWinner_getResult() {
        GameResult winner = GameResult.PLAYER_1;
        when(board.getWinner()).thenReturn(winner);

        GameResult result = gameController.getWinner();

        verify(board).getWinner();
        Assertions.assertEquals(winner, result);
    }

    @Test
    public void testGetLargePits_addLargerPits_returnListLargerPits() {
        List<LargerPit> largerPits = new ArrayList<>();
        largerPits.add(new LargerPit("PLAYER_1_LARGER_PIT"));
        largerPits.add(new LargerPit("PLAYER_2_LARGER_PIT"));
        when(board.getLargerPits()).thenReturn(largerPits);

        List<LargerPit> result = gameController.getLargerPits();

        verify(board).getLargerPits();
        Assertions.assertEquals(largerPits, result);
    }

    private List<IPit> getBoardState(){
        return Arrays.asList(
                new Pit(Constant.PLAYER_1_PIT_1),
                new Pit(Constant.PLAYER_1_PIT_2),
                new Pit(Constant.PLAYER_1_PIT_3),
                new Pit(Constant.PLAYER_1_PIT_4),
                new Pit(Constant.PLAYER_1_PIT_5),
                new Pit(Constant.PLAYER_1_PIT_6),
                new LargerPit(Constant.PLAYER_1_LARGER_PIT),
                new Pit(Constant.PLAYER_2_PIT_1),
                new Pit(Constant.PLAYER_2_PIT_2),
                new Pit(Constant.PLAYER_2_PIT_3),
                new Pit(Constant.PLAYER_2_PIT_4),
                new Pit(Constant.PLAYER_2_PIT_5),
                new Pit(Constant.PLAYER_2_PIT_6),
                new LargerPit(Constant.PLAYER_2_LARGER_PIT)
        );
    }
}
