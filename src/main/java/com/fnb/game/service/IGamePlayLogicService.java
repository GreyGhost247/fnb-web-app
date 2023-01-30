package com.fnb.game.service;

import com.fnb.game.model.LargerPit;
import com.fnb.game.model.player.GameResult;

public interface IGamePlayLogicService {
    void sowStone(String pitId);

    int getLastIndex();

    void checkCapture(int lastIndex);

    boolean canMove(int lastIndex);

    boolean doesNotOwnThisPit(String chosenPit);

    boolean hasStones();

    void AddStonesToLargerPit();

    boolean isLargerPit(String pitId);

    LargerPit getLargerPit();

    GameResult getWinner(int player1Stones, int player2Stones);
}
