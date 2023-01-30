package com.fnb.game.model.player;

import org.springframework.stereotype.Component;

import static com.fnb.game.model.player.Move.PlayerTurn.PLAYER_1;
import static com.fnb.game.model.player.Move.PlayerTurn.PLAYER_2;

@Component
public class Move {
    private static PlayerTurn turn = PLAYER_1;

    public enum PlayerTurn {
        PLAYER_1,
        PLAYER_2
    }

    public static PlayerTurn getTurn() {
        return turn;
    }

    public static void changeTurn() {
        if (turn == PLAYER_1) {
            turn = PLAYER_2;
        } else {
            turn = PLAYER_1;
        }
    }
}
