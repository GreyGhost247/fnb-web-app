package com.fnb.game.service;

import com.fnb.game.common.IPit;
import com.fnb.game.model.LargerPit;
import com.fnb.game.model.Pit;
import com.fnb.game.constants.Constant;
import com.fnb.game.model.player.GameResult;
import com.fnb.game.model.player.Move;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BoardLogicService implements IBoardLogicService{

    private List<IPit> board;
    private IGamePlayLogicService player1;
    private IGamePlayLogicService player2;
    private GameResult winner = GameResult.UNKNOWN;

    public BoardLogicService(){
        initializeGameState();
    }
    @Override
    public void play(String pitId) {

        if (winner != GameResult.UNKNOWN) {
            return;
        }

        IGamePlayLogicService currentPlayer = getCurrentPlayer();

        try {
            if (currentPlayer.isLargerPit(pitId)) {
                throw new RuntimeException("Cannot select a larger pit to be sowed");
            }
            if (currentPlayer.doesNotOwnThisPit(pitId)) {
                throw new RuntimeException("Invalid pit to sow");
            }

            currentPlayer.sowStone(pitId);

            int lastIndexSown = currentPlayer.getLastIndex();
            currentPlayer.checkCapture(lastIndexSown);

            if (gameEnded()) {
                winner = currentPlayer.getWinner(player1.getLargerPit().getStones(),
                        player2.getLargerPit().getStones());
                return;
            }

            if (!currentPlayer.canMove(lastIndexSown)) {
                Move.changeTurn();
            }

            if (gameEnded()) {
                winner = currentPlayer.getWinner(player1.getLargerPit().getStones(),
                        player2.getLargerPit().getStones());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<IPit> getBoard() {
        return board;
    }

    @Override
    public GameResult getWinner() {
        return winner;
    }

    @Override
    public List<LargerPit> getLargerPits() {
        return new ArrayList<>(Arrays.asList(player1.getLargerPit(), player2.getLargerPit()));
    }

    @Override
    public void resetBoard() {
        winner = GameResult.UNKNOWN;
        if (Move.getTurn() == Move.PlayerTurn.PLAYER_2) {
            Move.changeTurn();
        }
        initializeGameState();
    }

    private void initializeGameState() {
        board = Arrays.asList(
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

        player1 = new GamePlayLogicService(
                Arrays.asList(board.get(0), board.get(1), board.get(2),
                        board.get(3), board.get(4), board.get(5), board.get(6),
                        board.get(7), board.get(8), board.get(9), board.get(10),
                        board.get(11), board.get(12)));

        player2 = new GamePlayLogicService(
                Arrays.asList(board.get(7), board.get(8), board.get(9),
                        board.get(10), board.get(11), board.get(12), board.get(13),
                        board.get(0), board.get(1), board.get(2), board.get(3),
                        board.get(4), board.get(5)));
    }

    private boolean gameEnded() {
        if (getCurrentPlayer().hasStones()) {
            return false;
        }

        if (Move.getTurn() == Move.PlayerTurn.PLAYER_1) {
            player2.AddStonesToLargerPit();
        } else {
            player1.AddStonesToLargerPit();
        }
        return true;
    }

    private IGamePlayLogicService getCurrentPlayer() {
        if (Move.getTurn() == Move.PlayerTurn.PLAYER_1) {
            return player1;
        }
        return player2;
    }
}
