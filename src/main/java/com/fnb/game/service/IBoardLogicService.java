package com.fnb.game.service;

import com.fnb.game.common.IPit;
import com.fnb.game.model.LargerPit;
import com.fnb.game.model.player.GameResult;

import java.util.List;

public interface IBoardLogicService {
    void play(String pitId);

    List<IPit> getBoard();

    GameResult getWinner();

    List<LargerPit> getLargerPits();

    void resetBoard();
}
